package SniffStep.controller;

import SniffStep.common.HttpResponseEntity.ResponseResult;
import SniffStep.common.jwt.JwtTokenProvider;
import SniffStep.dto.MemberDTO;
import SniffStep.dto.MemberUpdateDTO;
import SniffStep.mapper.MemberMapper;
import SniffStep.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static SniffStep.common.HttpResponseEntity.success;

@Slf4j
@RestController
@RequestMapping("/v1/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final MemberMapper memberMapper;
    private final JwtTokenProvider jwtTokenProvider;

    @GetMapping("/list")
    public ResponseResult<List<MemberDTO>> getList() {
        return success(memberMapper.toDtoList(memberService.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseResult<MemberDTO> findOne(@PathVariable Long id) {
        return success(memberMapper.toDto(memberService.findById(id)));
    }

    @PatchMapping("/{id}")
    public ResponseResult<MemberDTO> update(@RequestBody @Valid MemberUpdateDTO memberUpdateDTO,
                                            @PathVariable(value = "id") Long id) {
        return success(memberMapper.toDto(memberService.update(memberUpdateDTO, id)));
    }

    @DeleteMapping("/{id}")
    public ResponseResult<MemberDTO> delete(@PathVariable Long id) {
        return success(memberMapper.toDto(memberService.delete(id)));
    }
}
