package SniffStep.controller;

import SniffStep.dto.BoardRequestDTO;
import SniffStep.dto.BoardResponseDTO;
import SniffStep.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/v1/boards")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;


    @PreAuthorize("hasRole('USER')")
    @PostMapping("/newBoard")
    public ResponseEntity<BoardResponseDTO> saveBoard(@RequestBody BoardRequestDTO boardRequestDTO) {
        return ResponseEntity.ok(boardService.saveBoard(boardRequestDTO));
    }
}
