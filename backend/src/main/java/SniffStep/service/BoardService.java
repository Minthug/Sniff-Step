package SniffStep.service;

import SniffStep.common.exception.*;
import SniffStep.dto.board.*;
import SniffStep.entity.Board;
import SniffStep.entity.Image;
import SniffStep.entity.Member;
import SniffStep.repository.BoardRepository;
import SniffStep.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class BoardService {

        private final BoardRepository boardRepository;
        private final ImageService imageService;
        private final AwsService awsService;
        private final MemberRepository memberRepository;


//        @Transactional
//        public void createBoardV2(BoardCreatedRequestDTO request, Member member) {
//            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//
//            if (principal instanceof UserDetails) {
//                UserDetails userDetails = (UserDetails) principal;
//                String username = userDetails.getUsername();
//
//                Optional<Member> optionalMember = memberRepository.findByEmail(username);
//                if (optionalMember.isPresent()) {
//                    member = optionalMember.get();
//
//                    List<AwsS3> uploadFiles = awsService.uploadFiles("images/board", request.getImages());
//
//                    List<Image> images  = uploadFiles.stream()
//                            .map(file -> new Image(file.getOriginalFileName(), file.getUploadFileName(), file.getUploadFileUrl()))
//                            .collect(Collectors.toList());
//
//                    Board board = new Board(request.getTitle(), request.getDescription(), request.getActivityLocation(), images, member);
//                    boardRepository.save(board);
//                } else {
//                    throw new MemberNotFoundException();
//                }
//                } else {
//                    throw new AccessDeniedException("인증되지 않은 사용자입니다.");
//                }
//        }

    @Transactional
    public void createBoardV3(BoardCreatedRequestDTO request, Member member) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) principal;
            String username = userDetails.getUsername();

            Optional<Member> optionalMember = memberRepository.findByEmail(username);
            if (optionalMember.isPresent()) {
                member = optionalMember.get();

                String folderPath = String.format("images/board/member_%d", member.getId());
                List<AwsS3> uploadFiles = awsService.uploadFilesV2(member.getId(), folderPath, request.getImages());

                List<Image> images  = uploadFiles.stream()
                        .map(file -> new Image(file.getOriginalFileName(), file.getUploadFileName(), file.getUploadFileUrl()))
                        .collect(Collectors.toList());

                Board board = new Board(request.getTitle(), request.getDescription(), request.getActivityLocation(), images, member);
                boardRepository.save(board);
            } else {
                throw new MemberNotFoundException();
            }
        } else {
            throw new AccessDeniedException("인증되지 않은 사용자입니다.");
        }
    }


        @Transactional(readOnly = true)
        public BoardResponseDTO findBoard(Long id) {
            return boardRepository.findByIdWithMemberAAndImages(id)
                    .map(BoardResponseDTO::toDto)
                    .orElseThrow(() -> new BusinessLogicException(ExceptionCode.POST_NOT_FOUND));
        }

        @Transactional(readOnly = true)
        public BoardFindAllWithPagingResponseDTO findAllBoards(Integer page) {
            PageRequest pageRequest = PageRequest.of(page, 10, Sort.by("id").descending());
            Page<Board> boards = boardRepository.findAll(pageRequest);
            List<BoardFindAllResponseDTO> boardsWithDto = boards.stream()
                    .map(BoardFindAllResponseDTO::toDto)
                    .collect(Collectors.toList());
            return BoardFindAllWithPagingResponseDTO.toDto(boardsWithDto, new PageInfoDTO(boards));
        }

        @Transactional(readOnly = true)
        public BoardFindAllWithPagingResponseDTO searchBoards(String keyword, Integer page) {
            PageRequest pageRequest = PageRequest.of(page, 10, Sort.by("id").descending());
            Page<Board> boards = boardRepository.findAllByTitleContaining(keyword, pageRequest);
            List<BoardFindAllResponseDTO> boardsWithDto = boards.stream()
                    .map(BoardFindAllResponseDTO::toDto)
                    .collect(Collectors.toList());
            return BoardFindAllWithPagingResponseDTO.toDto(boardsWithDto, new PageInfoDTO(boards));
        }

        @Transactional
        public void editBoard(Long id, String title, String description, String activityLocation, List<MultipartFile> addedImageFiles, List<Long> deletedImages) {
            Board board = boardRepository.findById(id).orElseThrow(BoardNotFoundException::new);
            validateBoardWriter(board);

            Board.ImageUpdatedResult result = board.updateBoard(title, description, activityLocation, addedImageFiles, deletedImages);
            uploadImages(result.getAddedImages(), result.getAddedImageFiles());
            deleteImages(result.getDeletedImages());
        }

        @Transactional
        public void deleteBoard(Long id) {
            Board board = boardRepository.findById(id).orElseThrow(() -> new BusinessLogicException(ExceptionCode.POST_NOT_FOUND));
            validateBoardWriter(board);
            deleteImages(board.getImages());
            boardRepository.delete(board);
        }

        private void validateBoardWriter(Board board) {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (principal instanceof UserDetails) {
                UserDetails userDetails = (UserDetails) principal;
                String username = userDetails.getUsername();


                Optional<Member> optionalMember = memberRepository.findByEmail(username);
                if (optionalMember.isPresent()) {
                    Member member = optionalMember.get();
                    if (!board.isOwnBoard(member)) {
                        throw new AccessDeniedException("게시글 작성자만 수정할 수 있습니다.");
                    }
                } else {
                    throw new AccessDeniedException("인증되지 않은 사용자입니다.");
                }
            } else if (principal instanceof String) {
                throw new AccessDeniedException("인증되지 않은 사용자입니다.");
            } else {
                throw new AccessDeniedException("인증되지 않은 사용자입니다.");
            }
        }

        private void deleteImages(List<Image> images) {
            images.forEach(i -> imageService.delete(i.getUniqueName()));
        }


        private void uploadImages(List<Image> images, List<MultipartFile> filesImages) {

            try {
                IntStream.range(0, filesImages.size())
                        .forEach(i ->  imageService.upload(filesImages.get(i), images.get(i).getUniqueName()));
            } catch (BusinessLogicException e) {
                throw new RuntimeException("이미지 업로드 중 오류가 발생했습니다.", e);
            }
        }
    }
