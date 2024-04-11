package SniffStep.service;

import SniffStep.common.exception.NotFoundException;
import SniffStep.dto.BoardRequestDTO;
import SniffStep.entity.Board;
import SniffStep.entity.Member;
import SniffStep.mapper.BoardRequestMapper;
import SniffStep.repository.BoardRepository;
import SniffStep.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;
    private final BoardRequestMapper boardRequestMapper;

    @Transactional(readOnly = true)
    public List<Board> findAll() {
        return boardRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Board findById(Long id) {
        return boardRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Board not found. : " + id));
    }


    @Transactional
    public Board insert(BoardRequestDTO boardRequestDTO) {

        Member member = memberRepository.findById(boardRequestDTO.getMemberId())
                .orElseThrow(() -> new NotFoundException("Member not found. : " + boardRequestDTO.getMemberId()));

        Board board = boardRequestMapper.toEntity(boardRequestDTO);
        board.updateMember(member);

        return boardRepository.save(board);
    }

    @Transactional
    public Board update(BoardRequestDTO boardRequestDTO, Long id) {

        Board board = findById(id);
        board.updateBoard(boardRequestDTO);
        return board;
    }

    @Transactional
    public Board delete(Long id) {
        Board board = findById(id);
        boardRepository.delete(board);
        return board;
    }
}
