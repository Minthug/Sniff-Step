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

@Service
@Slf4j
@RequiredArgsConstructor
public class BoardService {

        private final BoardRepository boardRepository;
        private final AwsService awsService;
        private final MemberRepository memberRepository;


    @Transactional
    public Board createBoard(BoardCreatedRequestDTO request, String username) {
        Member member = memberRepository.findByEmail(username)
                .orElseThrow(() -> new MemberNotFoundException());

        Board board = new Board(
                request.getTitle(),
                request.getDescription(),
                request.getActivityLocation(),
                member,
                new ArrayList<>(),
                request.getActivityDate().stream()
                        .map(ActivityDate::fromString)
                        .collect(Collectors.toList()),
                request.getActivityTime().stream()
                        .map(ActivityTime::fromString)
                        .collect(Collectors.toList())
        );
        board = boardRepository.save(board);

        if (!request.getImages().isEmpty()) {
            List<AwsS3> uploadFiles = awsService.uploadBoardFiles(member.getId(), board.getId(), request.getImages());

            List<Image> images = uploadFiles.stream()
                    .map(file -> new Image(file.getOriginalFileName(), file.getUploadFileUrl(), file.getUploadFilePath()))
                    .collect(Collectors.toList());

            images.forEach(board::addImages);

            boardRepository.save(board);
        }
        return board;
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
    public ImageUpdateResultDTO updateBoard(Long boardId, String title, String description, String activityLocation, List<MultipartFile> addedImages, List<Long> deletedImages) {
        Board board = findBoardById(boardId);
        List<Image> imagesToDelete = findImagesToDelete(board.getImages(), deletedImages);

        // S3 이미지 업로드
        List<AwsS3> uploadedImages = awsService.uploadBoardFiles(board.getMember().getId(), boardId, addedImages);
        List<Image> newImages = createImageEntities(uploadedImages);

        // 이미지 삭제 처리
        deleteImages(board, imagesToDelete);

        // 새 이미지 및 Board 업데이트
        List<Image> updatedImages = updatedBoardImage(board, imagesToDelete, newImages);

        board.updateBoardWithImages(title, description, activityLocation, updatedImages);

        return new ImageUpdateResultDTO(uploadedImages, newImages, imagesToDelete);
    }

    public Board findBoardById(Long boardId) {
        return boardRepository.findById(boardId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.POST_NOT_FOUND));
    }

    private List<Image> updatedBoardImage(Board board, List<Image> imagesToDelete, List<Image> newImage) {
        List<Image> updatedImage = new ArrayList<>(board.getImages());
        updatedImage.removeAll(imagesToDelete);
        updatedImage.addAll(newImage);
        return updatedImage;
    }

    private List<Image> createImageEntities(List<AwsS3> uploadedImage) {
        return uploadedImage.stream()
                .map(awsS3 -> Image.builder()
                        .originName(awsS3.getOriginalFileName())
                        .s3Url(awsS3.getUploadFileUrl())
                        .s3FilePath(awsS3.getUploadFilePath())
                        .build())
                .collect(Collectors.toList());

    }

    private List<Image> findImagesToDelete(List<Image> currentImage, List<Long> imageIds) {
        return currentImage.stream()
                .filter(image -> imageIds.contains(image.getId()))
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteBoard(Long id) {
        Board board = boardRepository.findById(id).orElseThrow(() -> new BusinessLogicException(ExceptionCode.POST_NOT_FOUND));
        validateBoardWriter(board);
        boardRepository.delete(board);
    }

    private void deleteImages(Board board, List<Image> imageToDelete) {
        for (Image image : imageToDelete) {
            board.removeImage(image);
            String filePath = String.format("member/%d/%d", board.getMember().getId(), board.getId());
            boolean deleted = awsService.deleteFileV2(filePath, image.getUniqueName());
            if (!deleted) {
                log.warn("Failed to delete image file: {}", image.getUniqueName());
            }
        }
    }

    public void validateBoardWriter(Board board) {
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



}

