package SniffStep.controller;

import SniffStep.common.HttpResponseEntity.ResponseResult;
import SniffStep.dto.BoardRequestDTO;
import SniffStep.dto.BoardResponseDTO;
import SniffStep.mapper.BoardRequestMapper;
import SniffStep.mapper.BoardResponseMapper;
import SniffStep.service.BoardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static SniffStep.common.HttpResponseEntity.success;

@Slf4j
@RestController
@RequestMapping("/v1/boards")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final BoardRequestMapper boardRequestMapper;
    private final BoardResponseMapper boardResponseMapper;

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/newBoard")
    public ResponseEntity<BoardResponseDTO> saveBoard(@RequestBody BoardRequestDTO boardRequestDTO) {
        return ResponseEntity.ok(boardService.saveBoard(boardRequestDTO));
    }


}
