package SniffStep.service;

import SniffStep.common.exception.*;
import SniffStep.dto.board.*;
import SniffStep.entity.*;
import SniffStep.repository.BoardRepository;
import SniffStep.repository.ImageRepository;
import SniffStep.repository.MemberRepository;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3Client;
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
    private final ImageRepository imageRepository;
    private final AmazonS3Client amazonS3Client;

    @Transactional
    public Board createBoard(BoardCreatedRequest request, String username) {
        Member member = memberRepository.findByEmail(username)
                .orElseThrow(() -> new MemberNotFoundException("Member not found with email: " + username));

        //Null 예외 추가
        List<ActivityDate> activityDates = (request.activityDate() != null) ? request.activityDate().stream().map(ActivityDate::fromString).collect(Collectors.toList()) : new ArrayList<>();

        List<ActivityTime> activityTimes = (request.activityTime() != null) ? request.activityTime().stream().map(ActivityTime::fromString).collect(Collectors.toList()) : new ArrayList<>();

        Board board = new Board(
                request.title(),
                request.description(),
                request.activityLocation(),
                member,
                new ArrayList<>(),
                activityDates,
                activityTimes
            );

        board = boardRepository.save(board);

        if (!request.images().isEmpty()) {
            List<AwsS3> uploadFiles = awsService.uploadBoardFilesV3(member.getId(), board.getId(), request.images());

            List<Image> images = uploadFiles.stream()
                    .map(file -> new Image(file.getOriginalFileName(), file.getUploadFileUrl(), file.getUploadFilePath()))
                    .collect(Collectors.toList());

            images.forEach(board::addImages);

            boardRepository.save(board);
        }
        return board;
    }

    @Transactional(readOnly = true)
    public BoardDetailResponse findBoard(Long id) {
        return boardRepository.findByIdWithMemberAAndImages(id)
                .map(BoardDetailResponse::from)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.POST_NOT_FOUND));
    }

    @Transactional(readOnly = true)
    public BoardPageResponse findAllBoards(Integer page) {
        PageRequest pageRequest = PageRequest.of(page, 10, Sort.by("id").descending());
        Page<Board> boards = boardRepository.findAll(pageRequest);
        List<BoardFindAllResponse> boardsWithDto = boards.stream()
                .map(BoardFindAllResponse::from)
                .collect(Collectors.toList());
        return BoardPageResponse.of(boardsWithDto, PageInfo.from(boards));
    }


    @Transactional(readOnly = true)
    public BoardPageResponse searchBoardsV2(String keyword, Integer page) {
        PageRequest pageRequest = PageRequest.of(page, 10, Sort.by("id").descending());
        Page<Board> boards = boardRepository.findAllByTitleContaining(keyword, pageRequest);

        List<Long> boardIds = boards.getContent().stream()
                .map(Board::getId)
                .collect(Collectors.toList());

        List<Board> boardWithMember = boardRepository.findBoardsWithMemberByIds(boardIds);

        List<BoardFindAllResponse> boardsWithDto = boardWithMember.stream()
                .map(BoardFindAllResponse::from)
                .collect(Collectors.toList());


        return BoardPageResponse.of(boardsWithDto,PageInfo.from(boards), keyword);
    }

    public Board findBoardById(Long boardId) {
        return boardRepository.findById(boardId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.POST_NOT_FOUND));
    }

    private void updateActivityDates(Board board, List<String> activityDates) {
        List<ActivityDate> enumDates = activityDates.stream()
                .map(ActivityDate::fromString)
                .collect(Collectors.toList());
        board.setActivityDateInternal(enumDates);
    }

    private void updateActivityTimes(Board board, List<String> activityTimes) {
        List<ActivityTime> enumTimes = activityTimes.stream()
                .map(ActivityTime::fromString)
                .collect(Collectors.toList());
        board.setActivityTimeInternal(enumTimes);
    }

    @Transactional
    public void deleteBoardV2(Long id) {
        Board board = boardRepository.findById(id).orElseThrow(() -> new BusinessLogicException(ExceptionCode.POST_NOT_FOUND));
        validateBoardWriter(board);


        try {
            boolean folderDeleted = awsService.deleteFolder(board.getMember().getId(), board.getId());
            if (!folderDeleted) {
                log.warn("Failed to delete folder for board: {}", id);
            } else {
                log.info("Folder deleted successfully for board: {}", id);
            }

            boardRepository.delete(board);
            log.info("Board deleted successfully. Board id: {}", id);
        } catch (AmazonServiceException e) {
            log.error("Failed to delete board. Board id: {}", id, e);
            throw new FileUploadFailureException("게시글 삭제에 실패했습니다.", e);
        } catch (Exception e) {
            log.error("Failed to delete board. Board id: {}", id, e);
            throw new FileUploadFailureException("게시글 삭제에 실패했습니다.", e);
        }
    }

    public void validateBoardWriter (Board board) {
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

    @Transactional
    public BoardResponseDTO updateBoardV4(Long boardId, BoardPatchDTO updateDTO) {
        Board board = findBoardById(boardId);
        Long memberId = board.getMember().getId();

        try {
            updateBoardImage(board, updateDTO.getImageFiles());
            updateBoardDetails(board, updateDTO);

            boardRepository.save(board);
            boardRepository.flush();
            log.info("Board updated successfully. Board id: {}", board.getId());

            return BoardResponseDTO.toDto(board);
        } catch (Exception e) {
            log.error("Failed to update board. BoardId: {}", boardId, e);
            throw new FileUploadFailureException("게시글 업데이트에 실패했습니다.", e);
        }
    }

    private void updateBoardImage(Board board, List<MultipartFile> imageFile) {
        if (imageFile == null || imageFile.isEmpty() || imageFile.get(0).isEmpty()) {
            log.info("No new image provided for board id: {}. Keeping existing image.", board.getId());
            return;
        }

        Long memberId = board.getMember().getId();
        try {
                deleteExistingImages(board);
            // 새 이미지 업로드
            List<AwsS3> uploadedImage = awsService.uploadBoardV4(memberId, board.getId(), imageFile);
            for (AwsS3 awsS3 : uploadedImage) {
                Image newImage = new Image(uploadedImage.get(0).getOriginalFileName(), uploadedImage.get(0).getUploadFileUrl(), uploadedImage.get(0).getUploadFilePath());
                board.addImages(newImage);
                log.info("New image uploaded for board id: {}. New URL: {}", board.getId(), newImage.getS3Url());
            }
        } catch (Exception e) {
            log.error("Failed to update board image for board id: {}", board.getId(), e);
            throw new FileUploadFailureException("Failed to update board image", e);
        }
    }

    private void updateBoardDetails(Board board, BoardPatchDTO updateDTO) {
        List<ActivityDate> activityDates = updateDTO.getActivityDate() != null ?
                updateDTO.getActivityDate().stream()
                        .map(ActivityDate::fromString)
                        .collect(Collectors.toList()) :
                null;

        List<ActivityTime> activityTimes = updateDTO.getActivityTime() != null ?
                updateDTO.getActivityTime().stream()
                        .map(ActivityTime::fromString)
                        .collect(Collectors.toList()) :
                null;

        board.update(
                updateDTO.getTitle(),
                updateDTO.getDescription(),
                updateDTO.getActivityLocation(),
                activityDates,
                activityTimes
        );
    }

    private void deleteExistingImages(Board board) {
        if (!board.getImages().isEmpty()) {
            for (Image existingImage : board.getImages()) {
                deleteExistingImage(existingImage.getS3Url());
            }
            board.getImages().clear();
            log.info("Existing images deleted for board id: {}", board.getId());
        }
    }

    private boolean deleteExistingImage(String s3Url) {
        if (s3Url == null || s3Url.isEmpty()) {
            log.debug("No existing image to delete");
            return true;
        }

        try {
            boolean deleted = awsService.deleteFileV3(s3Url);
            if (deleted) {
                log.info("Old profile image deleted successfully: {}", s3Url);
                return true;
            } else {
                log.warn("Failed to delete old profile image: {}", s3Url);
                return false;
            }
        } catch (Exception e) {
            log.error("Failed to delete old profile image: {}", s3Url, e);
            return false;
        }
    }
}

