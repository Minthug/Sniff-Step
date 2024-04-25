package SniffStep.controller;

import SniffStep.common.Response;
import SniffStep.common.config.guard.Login;
import SniffStep.dto.BoardCreatedRequestDTO;
import SniffStep.dto.BoardPatchDTO;
import SniffStep.entity.Member;
import SniffStep.service.BoardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("/v1/boards")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Response createBoard(@Valid @ModelAttribute BoardCreatedRequestDTO request,
                                @RequestParam(value = "images", required = false) MultipartFile[] images,
                                @Login Member member) {
        boardService.createBoard(request, member);
        return Response.success();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public Response findBoard(@PathVariable(value = "id") Long id) {
        return Response.success(boardService.findBoard(id));
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/{id}")
    public Response editBoard(@PathVariable(value = "id") Long id,
                              @Valid @ModelAttribute BoardPatchDTO request,
                              @Login Member member) {
        boardService.editBoard(id, request, member);
        return Response.success();
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{id}")
    public Response deleteBoard(@PathVariable(value = "id") Long id,
                                @Login Member member) {
        boardService.deleteBoard(id, member);
        return Response.success();
    }

}
