package SniffStep.controller;

import SniffStep.common.jwt.service.JwtService;
import SniffStep.dto.auth.LoginDTO;
import SniffStep.dto.auth.SignUpRequestDTO;
import SniffStep.service.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;


@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1/auth")
public class AuthController {

    private final AuthService authService;


    // 자체 회원가입
    @PostMapping("/signup")
    public ResponseEntity signup(@RequestBody SignUpRequestDTO signUpRequestDTO) throws Exception {
        authService.signup(signUpRequestDTO);
        return ResponseEntity.ok().build();
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginDTO loginDTO) {
        return ResponseEntity.ok(authService.login(loginDTO));
    }
}
