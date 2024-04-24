package SniffStep.service;

import SniffStep.common.exception.BusinessLogicException;
import SniffStep.common.exception.ExceptionCode;
import SniffStep.common.exception.MemberNotFoundException;
import SniffStep.dto.BoardCreatedRequestDTO;
import SniffStep.dto.BoardPatchDTO;
import SniffStep.dto.BoardResponseDTO;
import SniffStep.entity.Board;
import SniffStep.entity.Image;
import SniffStep.entity.Member;
import SniffStep.repository.BoardRepository;
import SniffStep.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final ImageService imageService;
    private final FileService fileService;
    private final MemberRepository memberRepository;

    @Transactional
    public void createBoard(BoardCreatedRequestDTO request, Member member) {
        List<Image> images = request.getImages().stream()
                .map(i -> new Image(i.getOriginalFilename()))
                .collect(Collectors.toList());
        Board board = new Board(request.getTitle(), request.getDescription(), request.getActivityLocation(), request.getActivityDate(), request.getActivityTime(), images, member);
        boardRepository.save(board);

        uploadImages(board.getImages(), request.getImages());
    }


    @Transactional(readOnly = true)
    public BoardResponseDTO findBoard(Long id) {
        Board board = boardRepository.findById(id).orElseThrow(() -> new BusinessLogicException(ExceptionCode.POST_NOT_FOUND));

        Member member = board.getMember();
        return BoardResponseDTO.toDto(member.getName(), board);
    }

    @Transactional
    public void editBoard(Long id, BoardPatchDTO request, Member member) {
        Board board = boardRepository.findById(id).orElseThrow(() -> new BusinessLogicException(ExceptionCode.POST_NOT_FOUND));
        validateBoardWriter(board, member);
        Board.ImageUpdatedResult result = board.updateBoard(request);
        uploadImages(result.getAddedImages(), result.getAddedImageFiles());
        deleteImages(result.getDeletedImages());
    }

    @Transactional
    public void deleteBoard(Long id, Member member) {
        Board board = boardRepository.findById(id).orElseThrow(() -> new BusinessLogicException(ExceptionCode.POST_NOT_FOUND));
        validateBoardWriter(board, member);
        deleteImages(board.getImages());
        boardRepository.delete(board);
    }

    private void validateBoardWriter(Board board, Member member) {
        if (!board.isOwnBoard(member)) {
            throw new MemberNotFoundException();
        }
    }

    private void deleteImages(List<Image> images) {
        images.forEach(i -> fileService.delete(i.getUniqueName()));
    }


    private void uploadImages(List<Image> images, List<MultipartFile> filesImages) {
        IntStream.range(0, images.size())
                .forEach(i -> imageService.upload(filesImages.get(i), images.get(i).getUniqueName()));
    }
}

