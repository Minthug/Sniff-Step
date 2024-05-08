package SniffStep.controller;

import SniffStep.common.Response;
import SniffStep.common.config.guard.Login;
import SniffStep.dto.board.BoardCreatedRequestDTO;
import SniffStep.dto.board.BoardPatchDTO;
import SniffStep.entity.Member;
import SniffStep.service.BoardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/v1/boards")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Response createBoard(@Valid @ModelAttribute BoardCreatedRequestDTO request,
                                @Login Member member) {
        boardService.createBoard(request, member);
        return Response.success();
    }


    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/find/{id}")
    public Response findBoard(@PathVariable(value = "id") Long id) {
        return Response.success(boardService.findBoard(id));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/findAll")
    public Response findAllBoards(@RequestParam(defaultValue = "0") Integer page) {
        return Response.success(boardService.findAllBoards(page));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/search/{keyword}")
    public Response searchBoards(@PathVariable(value = "keyword") String keyword,
                                 @RequestParam(defaultValue = "0") Integer page) {
        return Response.success(boardService.searchBoards(keyword, page));
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/patch/{id}")
    public Response editBoard(@PathVariable(value = "id") Long id,
                              @Valid @ModelAttribute BoardPatchDTO request,
                              @Login Member member) {
        boardService.editBoard(id, request, member);
        return Response.success();
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/delete/{id}")
    public Response deleteBoard(@PathVariable(value = "id") Long id,
                                @Login Member member) {
        boardService.deleteBoard(id, member);
        return Response.success();
    }


}
