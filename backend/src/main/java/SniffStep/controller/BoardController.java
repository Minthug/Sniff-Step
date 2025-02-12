package SniffStep.controller;

import SniffStep.dto.board.*;
import SniffStep.entity.Board;
import SniffStep.service.BoardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/v1/boards")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;


    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<BoardDetailResponse> createBoard(@Valid @ModelAttribute BoardCreatedRequest request,
                                         @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Board createBoard = boardService.createBoard(request, userDetails.getUsername());
        BoardDetailResponse response = BoardDetailResponse.from(createBoard);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findBoard(@PathVariable(value = "id") Long id) {
        return ResponseEntity.ok(boardService.findBoard(id));
    }

    @GetMapping("")
    public ResponseEntity<?> findAllBoards(@RequestParam(defaultValue = "0") Integer page) {
        return ResponseEntity.ok(boardService.findAllBoards(page));
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchBoards(@RequestParam(value = "keyword") String keyword,
                                 @RequestParam(defaultValue = "0") Integer page) {
        return ResponseEntity.ok(boardService.searchBoardsV2(keyword, page));
    }

    @PatchMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> editBoard(@PathVariable(value = "id") Long id,
                                       @Valid @ModelAttribute BoardPatchRequest request) {
        BoardDetailResponse response = boardService.updateBoardV4(id, request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBoard(@PathVariable(value = "id") Long id, @AuthenticationPrincipal UserDetails userDetails) {

        boardService.deleteBoardV2(id);
        return ResponseEntity.ok().build();
    }


}
