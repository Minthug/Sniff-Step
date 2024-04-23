package SniffStep.controller;

import SniffStep.common.jwt.JwtTokenProvider;
import SniffStep.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/v1/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final JwtTokenProvider jwtTokenProvider;

//    @GetMapping("/list")
//    public ResponseResult<List<MemberDTO>> getList() {
//        return success(memberMapper.toDtoList(memberService.findAll()));
//    }
//
//    @GetMapping("/{id}")
//    public ResponseResult<MemberDTO> findOne(@PathVariable Long id) {
//        return success(memberMapper.toDto(memberService.findById(id)));
//    }
//
//    @PatchMapping("/{id}")
//    public ResponseResult<MemberDTO> update(@RequestBody @Valid MemberUpdateDTO memberUpdateDTO,
//                                            @PathVariable(value = "id") Long id) {
//        return success(memberMapper.toDto(memberService.update(memberUpdateDTO, id)));
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseResult<MemberDTO> delete(@PathVariable Long id) {
//        return success(memberMapper.toDto(memberService.delete(id)));
//    }
}
