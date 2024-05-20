package SniffStep.controller;

import SniffStep.common.Response;
import SniffStep.common.jwt.service.JwtService;
import SniffStep.dto.auth.LoginDTO;
import SniffStep.dto.auth.SignUpRequestDTO;
import SniffStep.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static SniffStep.common.Response.success;


@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1/auth")
public class AuthController {

    private final AuthService authService;
    private final JwtService jwtService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/signup")
    public Response signup(@RequestBody SignUpRequestDTO signUpRequestDTO) throws Exception {
        authService.signup(signUpRequestDTO);
        return success();
    }

    @PostMapping("/login")
    public ResponseEntity<Response> login(@RequestBody LoginDTO loginDTO) {
        return ResponseEntity.ok(success(authService.login(loginDTO)));
    }


}
