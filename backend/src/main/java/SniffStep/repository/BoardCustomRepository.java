package SniffStep.repository;


import SniffStep.dto.BoardResponseDTO;
import SniffStep.entity.BoardType;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BoardCustomRepository {

    PageImpl<BoardResponseDTO> getBoardList(String query, BoardType boardType, Pageable pageable);

    List<BoardResponseDTO> getBestList(BoardType boardType);

}