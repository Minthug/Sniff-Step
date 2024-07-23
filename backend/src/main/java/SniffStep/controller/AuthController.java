package SniffStep.controller;

import SniffStep.common.exception.DuplicateEmailException;
import SniffStep.common.exception.DuplicateNicknameException;
import SniffStep.common.jwt.dto.TokenDto;
import SniffStep.common.jwt.service.JwtService;
import SniffStep.dto.auth.LoginDTO;
import SniffStep.dto.auth.ProfileDTO;
import SniffStep.dto.auth.SignUpRequestDTO;
import SniffStep.repository.MemberRepository;
import SniffStep.service.AuthService;
import SniffStep.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
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
    public ResponseEntity login(@RequestBody LoginDTO loginDTO) {
        return ResponseEntity.ok(authService.login(loginDTO));
    }

    @GetMapping("/profile")
    public ResponseEntity<ProfileDTO> getProfile(@AuthenticationPrincipal UserDetails userDetails) {
        String email = userDetails.getUsername();
        ProfileDTO profileDTO = memberService.getProfile(email);
        return ResponseEntity.ok(profileDTO);
    }

    @PostMapping("/refresh")
    public ResponseEntity<TokenDto> reissue(HttpServletRequest request, HttpServletResponse response) {
        try {
            TokenDto tokenDto = jwtService.reissue(request, response);
            return ResponseEntity.ok(tokenDto);
        } catch (InvalidParameterException e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new TokenDto(null, null));
        }
    }

    @PostMapping("/refreshV2")
    public ResponseEntity<TokenDto> reissueV2(@RequestBody TokenDto tokenDto, HttpServletResponse response) {
        try {
            TokenDto newTokenDto = jwtService.reissueV2(tokenDto.getRefreshToken(), response);
            return ResponseEntity.ok(newTokenDto);
        } catch (InvalidParameterException e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new TokenDto(null, null));
        }
    }
}
