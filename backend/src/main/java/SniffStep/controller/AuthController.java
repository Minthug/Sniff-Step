package SniffStep.controller;

import SniffStep.common.exception.DuplicateEmailException;
import SniffStep.common.exception.DuplicateNicknameException;
import SniffStep.common.exception.LoginFailureException;
import SniffStep.common.exception.NotFoundException;
import SniffStep.common.jwt.dto.TokenDto;
import SniffStep.common.jwt.service.JwtService;
import SniffStep.dto.auth.LoginRequest;
import SniffStep.dto.auth.ProfileRequest;
import SniffStep.dto.auth.SignUpRequestDTO;
import SniffStep.repository.MemberRepository;
import SniffStep.service.AuthService;
import SniffStep.service.MemberService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.security.InvalidParameterException;
import java.util.Map;


@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1/auth")
public class AuthController {

    private final AuthService authService;
    private final MemberService memberService;
    private final JwtService jwtService;
    private final MemberRepository memberRepository;


    // 자체 회원가입
    @PostMapping("/signup")
    public ResponseEntity signup(@RequestBody SignUpRequestDTO signUpRequestDTO) throws Exception {
        if (signUpRequestDTO.getEmail() == null || signUpRequestDTO.getEmail().trim().isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "이메일 주소를 입력해주세요."));
        }
        if (signUpRequestDTO.getPassword() == null || signUpRequestDTO.getPassword().trim().isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "비밀번호를 입력해주세요."));
        }
        if (signUpRequestDTO.getNickname() == null || signUpRequestDTO.getNickname().trim().isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "닉네임을 입력해주세요."));
        }


        try {
            authService.signup(signUpRequestDTO);
            return ResponseEntity.ok().body(Map.of("Message", "회원가입 성공하였습니다"));
        } catch (DuplicateEmailException e) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(Map.of("error", "이미 존재하는 이메일입니다.", "message", e.getMessage()));
        } catch (DuplicateNicknameException e) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(Map.of("error", "이미 존재하는 닉네임입니다.", "message", e.getMessage()));
       } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "서버 오류가 발생하였습니다.", "message", "회원 가입에 실패하였습니다."));
        }
    }

    // 로그인
    @PostMapping("/signin")
    public ResponseEntity login(@RequestBody LoginRequest request) {

        try {
            return ResponseEntity.ok(authService.login(request));
        } catch (LoginFailureException e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "로그인 실패", "message", "비밀번호가 일치하지 않습니다."));
        } catch (NotFoundException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "로그인 실패", "message", "존재하지 않는 이메일입니다."));
        }
    }

    @GetMapping("/profile")
    public ResponseEntity<ProfileRequest> getProfile(@AuthenticationPrincipal UserDetails userDetails) {
        String email = userDetails.getUsername();
        ProfileRequest profileDTO = memberService.getProfile(email);
        return ResponseEntity.ok(profileDTO);
    }

    @PostMapping("/refresh")
    public ResponseEntity<TokenDto> reissue(@RequestBody TokenDto tokenDto, HttpServletResponse response) {
        try {
            TokenDto newTokenDto = jwtService.reissue(tokenDto.getRefreshToken(), response);
            return ResponseEntity.ok(newTokenDto);
        } catch (InvalidParameterException e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new TokenDto(null, null));
        }
    }
}
