package SniffStep.service;

import SniffStep.common.exception.NotFoundException;
import SniffStep.dto.BoardRequestDTO;
import SniffStep.dto.BoardResponseDTO;
import SniffStep.entity.Board;
import SniffStep.entity.Member;
import SniffStep.mapper.BoardRequestMapper;
import SniffStep.repository.BoardRepository;
import SniffStep.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final ImageService imageService;
    private final MemberRepository memberRepository;


    public BoardResponseDTO saveBoard(BoardRequestDTO boardRequest) {
        Long memberId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Member member = memberRepository.findById(memberId).get();
        Board newBoard = boardRequest.toEntity();
        newBoard.saveMember(member);

        Board savedBoard = boardRepository.save(newBoard);
        List<Long> imageId = boardRequest.getImageId();
        List<String> savedImgUrlList = imageService.saveBoard(savedBoard, imageId);

        return new BoardResponseDTO(savedBoard, savedImgUrlList);
    }
}
