package SniffStep.controller;

import SniffStep.dto.auth.LoginDTO;
import SniffStep.dto.auth.ProfileDTO;
import SniffStep.dto.auth.SignUpRequestDTO;
import SniffStep.dto.member.MemberDTO;
import SniffStep.entity.Member;
import SniffStep.service.AuthService;
import SniffStep.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1/auth")
public class AuthController {

    private final AuthService authService;
    private final MemberService memberService;


    // 자체 회원가입
    @PostMapping("/signup")
    public ResponseEntity signup(@RequestBody SignUpRequestDTO signUpRequestDTO) throws Exception {
        authService.signup(signUpRequestDTO);
        return ResponseEntity.ok().build();
    }

    // 로그인
    @PostMapping("/signin")
    public ResponseEntity login(@RequestBody LoginDTO loginDTO) {
        return ResponseEntity.ok(authService.login(loginDTO));
    }

    @GetMapping("/profile")
    public ResponseEntity<ProfileDTO> getProfile(@AuthenticationPrincipal UserDetails userDetails) {
        String email = userDetails.getUsername();
        ProfileDTO profileDTO = memberService.getProfile(email);
        return ResponseEntity.ok(profileDTO);
    }
}
