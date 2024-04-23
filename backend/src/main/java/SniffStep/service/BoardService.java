package SniffStep.service;

import SniffStep.common.exception.BusinessLogicException;
import SniffStep.common.exception.ExceptionCode;
import SniffStep.dto.BoardPatchDTO;
import SniffStep.dto.BoardRequestDTO;
import SniffStep.dto.BoardResponseDTO;
import SniffStep.dto.BoardTotalResponseDTO;
import SniffStep.entity.Board;
import SniffStep.entity.Member;
import SniffStep.repository.BoardRepository;
import SniffStep.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final ImageService imageService;
    private final MemberRepository memberRepository;


    public BoardResponseDTO saveBoard(BoardRequestDTO boardRequest) {
        // Principal에서 가져온 username을 이용해 member를 찾습니다.
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        Member member = memberRepository.findByName(name);

        Board newBoard = boardRequest.toEntity();
        newBoard.saveMember(member);

        Board savedBoard = boardRepository.save(newBoard);
        List<Long> imageId = boardRequest.getImageId();
        List<String> savedImgUrlList = imageService.saveBoard(savedBoard, imageId);

        return new BoardResponseDTO(savedBoard, savedImgUrlList);
    }

    public BoardResponseDTO patchBoard(BoardPatchDTO patch, Long id) {
        Board board = boardRepository.findById(id).orElseThrow(() -> new BusinessLogicException(ExceptionCode.POST_NOT_FOUND));
        board.updateBoard(id, patch);
        boardRepository.save(board);
        List<String> imgUrlList = imageService.findUrlByBoardId(id);
        return new BoardResponseDTO(board, imgUrlList);
    }

    public BoardTotalResponseDTO findById(Long id) {
        Board board = boardRepository.findById(id).get();

        List<String> imgUrlList = imageService.findUrlByBoardId(board.getId());

        BoardTotalResponseDTO response = BoardTotalResponseDTO.builder()
                .id(board.getId())
                .memberId(board.getAuthor().getId())
                .nickname(board.getAuthor().getNickname())
                .title(board.getTitle())
                .activityTime(board.getActivityTime())
                .description(board.getDescription())
                .activityLocation(board.getActivityLocation())
                .activityDate(board.getActivityDate())
                .imgUrlList(imgUrlList)
                .build();

        return response;
    }

    public void delete(Long id) {
        Board board = boardRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id = " + id));
        boardRepository.delete(board);
    }

    public Board verifyBoard(Long id) {
        return boardRepository.findById(id)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.POST_NOT_FOUND));
    }
}

