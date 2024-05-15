package SniffStep.controller;

import SniffStep.common.Response;
import SniffStep.common.jwt.dto.TokenRequestDTO;
import SniffStep.dto.auth.SignUpRequestDTO;
import SniffStep.dto.member.MemberRequestDTO;
import SniffStep.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static SniffStep.common.Response.success;


@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1/auth")
public class AuthController {

    private final AuthService authService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/signup")
    public Response signup(@RequestBody SignUpRequestDTO signUpRequestDTO) throws Exception {
        authService.signup(signUpRequestDTO);
        return success();
    }

    @PostMapping("/sign-up")
    public String signupV2(@RequestBody SignUpRequestDTO signUpRequestDTO) throws Exception {
        authService.signup(signUpRequestDTO);
        return "success";
    }

    @PostMapping("/login")
    public String jwtTest() {
        return "jwtTest";
    }
}
