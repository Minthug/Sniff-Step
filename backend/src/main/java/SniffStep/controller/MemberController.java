package SniffStep.controller;

import SniffStep.common.Response;
import SniffStep.common.config.guard.Login;
import SniffStep.dto.MemberUpdateDTO;
import SniffStep.entity.Member;
import SniffStep.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/list")
    public Response findAllMember() {
        return Response.success(memberService.findAllMember());
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public Response findMember(@PathVariable(value = "id") Long id) {
        return Response.success(memberService.findMember(id));
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping()
    public Response editMember(@RequestBody MemberUpdateDTO memberUpdateDTO,
                               @Login Member member) {
        memberService.editMember(member, memberUpdateDTO);

        Authentication authentication = new UsernamePasswordAuthenticationToken(member.getNickname(),
                memberUpdateDTO.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return Response.success();
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping()
    public Response deleteMember(@Login Member member) {
        memberService.deleteMember(member);
        return Response.success();
    }
}
