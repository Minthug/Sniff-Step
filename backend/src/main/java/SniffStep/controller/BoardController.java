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

    @GetMapping("/list")
    public ResponseResult<List<BoardResponseDTO>> listAll() {
        return success(boardResponseMapper.toDtoList(boardService.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseResult<BoardResponseDTO> findOne(@PathVariable(value = "id") Long id) {
        return success(boardResponseMapper.toDto(boardService.findById(id)));
    }

    @PostMapping
    public ResponseResult<BoardResponseDTO> insert(@RequestBody @Valid BoardRequestDTO boardRequestDTO) {
        return success(boardResponseMapper.toDto(boardService.insert(boardRequestDTO)));
    }
}
