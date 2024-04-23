package SniffStep.controller;

import SniffStep.common.HttpResponseEntity;
import SniffStep.common.HttpResponseEntity.ResponseResult;
import SniffStep.common.jwt.JwtTokenProvider;
import SniffStep.common.jwt.dto.TokenDTO;
import SniffStep.dto.MemberRequestDTO;
import SniffStep.dto.MemberResponseDTO;
import SniffStep.dto.SignUpRequestDTO;
import SniffStep.service.AuthService;
import SniffStep.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static SniffStep.common.HttpResponseEntity.success;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseResult signup(@RequestBody SignUpRequestDTO signUpRequestDTO) {
        return success();
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@RequestBody MemberRequestDTO memberRequestDTO) {
        return ResponseEntity.ok(authService.login(memberRequestDTO));
    }
}
