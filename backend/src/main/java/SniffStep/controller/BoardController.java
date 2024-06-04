package SniffStep.controller;

import SniffStep.dto.board.BoardCreatedRequestDTO;
import SniffStep.dto.board.BoardPatchDTO;
import SniffStep.entity.Member;
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
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/v1/boards")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createBoard(@Valid @ModelAttribute BoardCreatedRequestDTO request, Member member) {
        boardService.createBoard(request, member);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    @GetMapping("/find/{id}")
    public ResponseEntity<?> findBoard(@PathVariable(value = "id") Long id) {
        return ResponseEntity.ok(boardService.findBoard(id));
    }

    @GetMapping("/findAll")
    public ResponseEntity<?> findAllBoards(@RequestParam(defaultValue = "0") Integer page) {
        return ResponseEntity.ok(boardService.findAllBoards(page));
    }

    @GetMapping("/search/{keyword}")
    public ResponseEntity<?> searchBoards(@PathVariable(value = "keyword") String keyword,
                                 @RequestParam(defaultValue = "0") Integer page) {
        return ResponseEntity.ok(boardService.searchBoards(keyword, page));
    }

    @PatchMapping(value = "/patch/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> editBoard(@PathVariable(value = "id") Long id,
                                       @Valid @RequestBody BoardPatchDTO request,
                                       @AuthenticationPrincipal UserDetails userDetails) {
        String title = request.getTitle();
        String description = request.getDescription();
        String activityLocation = request.getActivityLocation();
        List<MultipartFile> addedImages = request.getAddedImages();
        List<Long> deletedImages = request.getDeletedImages();

        boardService.editBoard(id, title, description, activityLocation, addedImages, deletedImages);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteBoard(@PathVariable(value = "id") Long id, @AuthenticationPrincipal UserDetails userDetails) {

        boardService.deleteBoard(id);
        return ResponseEntity.ok().build();
    }


}
