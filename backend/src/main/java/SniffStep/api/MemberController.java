package SniffStep.api;

import SniffStep.entity.Member;
import SniffStep.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@Slf4j
@RestController
@RequestMapping("/v1/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("")
    public List<Member> findAll() {
        return memberService.findMembers();
    }

    @GetMapping("/{id}")
    public Member findOne(Long memberId) {
        return memberService.findOne(memberId);
    }


}
