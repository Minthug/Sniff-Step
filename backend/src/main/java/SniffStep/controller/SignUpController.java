package SniffStep.controller;

import SniffStep.common.jwt.JwtTokenProvider;
import SniffStep.common.jwt.dto.TokenResponseDTO;
import SniffStep.dto.JoinDTO;
import SniffStep.dto.LoginDTO;
import SniffStep.dto.MemberDTO;
import SniffStep.entity.Member;
import SniffStep.mapper.MemberMapper;
import SniffStep.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import static SniffStep.common.HttpResponseEntity.ResponseResult;
import static SniffStep.common.HttpResponseEntity.success;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1/auth")
public class SignUpController {

    private final MemberService memberService;
    private final MemberMapper memberMapper;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/signup")
    public ResponseResult<MemberDTO> join(@RequestBody @Valid JoinDTO joinDTO) {
        Member newMember = processJoinRequest(joinDTO);
        return success(memberMapper.toDto(newMember));
    }

    private Member processJoinRequest(JoinDTO joinDTO) {
        return memberService.join(joinDTO);
    }

    @PostMapping("/signin")
    public ResponseResult<TokenResponseDTO> login(@RequestBody LoginDTO loginDTO) {
        return success(memberService.login(loginDTO));
    }

    @PostMapping("/reissue")
    public ResponseResult<TokenResponseDTO> reissue(@RequestHeader("RefreshToken") String refreshToken) {
        return success(memberService.reissue(refreshToken));
    }
}
