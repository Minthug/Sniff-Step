package SniffStep.service;

import SniffStep.common.exception.*;
import SniffStep.dto.board.*;
import SniffStep.entity.*;
import SniffStep.repository.BoardRepository;
import SniffStep.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@Slf4j
@RequiredArgsConstructor
public class BoardService {

        private final BoardRepository boardRepository;
        private final ImageService imageService;
        private final AwsService awsService;
        private final MemberRepository memberRepository;


    @Transactional
    public void createBoard(BoardCreatedRequestDTO request, Member member) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) principal;
            String username = userDetails.getUsername();

            Optional<Member> optionalMember = memberRepository.findByEmail(username);
            if (optionalMember.isPresent()) {
                member = optionalMember.get();

                String folderPath = String.format("images/board/member_%d", member.getId());
                List<AwsS3> uploadFiles = awsService.uploadFiles(member.getId(), folderPath, request.getImages());

                List<Image> images  = uploadFiles.stream()
                        .map(file -> new Image(file.getOriginalFileName(), file.getUploadFileName(), file.getUploadFileUrl()))
                        .collect(Collectors.toList());

                List<ActivityDate> activityDates = request.getActivityDate().stream()
                        .map(ActivityDate::fromString)
                        .collect(Collectors.toList());

                List<ActivityTime> activityTimes = request.getActivityTime().stream()
                        .map(ActivityTime::fromString)
                        .collect(Collectors.toList());

                Board board = new Board(
                        request.getTitle(),
                        request.getDescription(),
                        request.getActivityLocation(),
                        images,
                        member,
                        activityDates,
                        activityTimes);

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

//        @Transactional
//        public void editBoard(Long id, String title, String description, String activityLocation, List<MultipartFile> addedImageFiles, List<Long> deletedImages) {
//            Board board = boardRepository.findById(id).orElseThrow(BoardNotFoundException::new);
//            validateBoardWriter(board);
//
//            ImageUpdatedResult result = board.updateBoard(title, description, activityLocation, addedImageFiles, deletedImages);
//            uploadImages(result.getAddedImages(), result.getAddedImageFiles());
//            deleteImages(result.getDeletedImages());
//        }

    @Transactional
    public ImageUpdateResultDTO updateBoard(Long boardId, String title, String description, String activityLocation, List<MultipartFile> addedImages, List<Long> deletedImages) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new BusinessLogicException(ExceptionCode.POST_NOT_FOUND));

        board.update(title, description, activityLocation);

        ImageUpdateResultDTO result = processImages(board, addedImages, deletedImages);

        return null;
    }

//    private ImageUpdateResultDTO findImageUpdatedResult(List<MultipartFile> addedImageFiles, List<Long> deletedImageIds) {
//        List<Image> addedImage = convertImageFilesToImages(addedImageFiles);
//        List<Image> deletedImages = convertImageIdsToImages(deletedImageIds);
//        return new ImageUpdatedResult(addedImageFiles, addedImage, deletedImages);
//    }

    private ImageUpdateResultDTO processImages(Board board, List<MultipartFile> addedImages, List<Long> deletedImageIds) {
        List<Image> addedImageEntities = createImageEntities(addedImages);
        List<Image> deletedImageEntities = findImagesToDelete(board, deletedImageIds);

        board.addImages(addedImageEntities);
        deleteImages(deletedImageEntities);

        return new ImageUpdateResultDTO(addedImages, addedImageEntities, deletedImageEntities);
    }

    private List<Image> createImageEntities(List<MultipartFile> imageFiles) {
        return imageFiles.stream()
                .map(file -> new Image(file.getOriginalFilename()))
                .collect(Collectors.toList());
    }

    private List<Image> findImagesToDelete(Board board, List<Long> imageIds) {
        return board.getImages().stream()
                .filter(image -> imageIds.contains(image.getId()))
                .collect(Collectors.toList());
    }
    @Transactional
    public void deleteBoard(Long id) {
        Board board = boardRepository.findById(id).orElseThrow(() -> new BusinessLogicException(ExceptionCode.POST_NOT_FOUND));
        validateBoardWriter(board);
        boardRepository.delete(board);
    }

    private void deleteImages(List<Image> images) {
        for (Image image : images) {
            boolean deleted = awsService.deleteFileV2(image.getS3FilePath(), image.getUniqueName());
            if (!deleted) {
                log.warn("이미지 삭제에 실패했습니다. 이미지 경로: {}", image.getUniqueName());
            }
        }
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

    private void uploadImages(List<Image> images, List<MultipartFile> filesImages) {

        try {
            IntStream.range(0, filesImages.size())
                    .forEach(i ->  imageService.upload(filesImages.get(i), images.get(i).getUniqueName()));
        } catch (BusinessLogicException e) {
            throw new RuntimeException("이미지 업로드 중 오류가 발생했습니다.", e);
        }
    }
}

