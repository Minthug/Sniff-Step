package SniffStep.api;

import SniffStep.common.HttpResponseEntity;
import SniffStep.dto.JoinDTO;
import SniffStep.dto.MemberDTO;
import SniffStep.entity.Member;
import SniffStep.mapper.MemberMapper;
import SniffStep.service.MemberService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static SniffStep.common.HttpResponseEntity.ResponseResult;
import static SniffStep.common.HttpResponseEntity.success;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/auth")
public class SignUpController {

    private final MemberService memberService;
//    private final JwtTokenProvider jwtTokenProvider;
    private final MemberMapper memberMapper;

    @PostMapping("/signup")
    public ResponseResult<MemberDTO> join(@RequestParam @Valid JoinDTO joinDTO) {
        return success(memberMapper.toDto(memberService.join(joinDTO)));
    }

    @GetMapping("/signin")
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
