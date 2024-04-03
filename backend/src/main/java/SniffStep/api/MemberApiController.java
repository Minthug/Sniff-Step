package SniffStep.api;

import SniffStep.member.entity.Member;
import SniffStep.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

    @GetMapping("/v1/members")
    public List<Member> membersV1 () {
        return memberService.findMembers();
    }
}
