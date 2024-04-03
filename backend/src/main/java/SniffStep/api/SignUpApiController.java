package SniffStep.api;

import SniffStep.member.entity.Member;
import SniffStep.member.service.MemberService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SignUpApiController {

    private final MemberService memberService;

    @PostMapping("/v1/auth/signup")
    public String create(@Valid MemberForm memberForm, BindingResult result) {

        if (result.hasErrors()) {
            return "members/createMemberForm";
        }

        Member member = new Member();
        member.setName(memberForm.getName());
        member.setEmail(memberForm.getEmail());
        member.setPassword(memberForm.getPassword());
        member.setPhoneNumber(memberForm.getPhoneNumber());

        memberService.join(member);
        return "success";
    }

    @GetMapping("/v1/auth/signin")
    public String list(Model model) {
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "members/memberList";
    }


    @Data
    static class CreateMemberRequest {
        @NotEmpty
        private String name;
    }
    @Data
    static class CreateMemberResponse {
        private Long id;

        public CreateMemberResponse(Long id) {
            this.id = id;
        }
    }
}
