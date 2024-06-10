package SniffStep.service;

import SniffStep.common.jwt.dto.TokenDto;
import SniffStep.common.jwt.service.JwtService;
import SniffStep.dto.auth.LoginDTO;
import SniffStep.dto.auth.SignUpRequestDTO;
import SniffStep.entity.JwtTokenType;
import SniffStep.entity.Member;
import SniffStep.entity.MemberRole;
import SniffStep.entity.MemberType;
import SniffStep.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder encoder;
    private final JwtService jwtService;



    @Transactional
    public void signup(SignUpRequestDTO signUpRequestDTO) throws Exception {
        if (memberRepository.findByEmail(signUpRequestDTO.getEmail()).isPresent()) {
            throw new Exception("이미 존재하는 이메일입니다.");
        }

        if (memberRepository.findByNickname(signUpRequestDTO.getNickname()).isPresent()) {
            throw new Exception("이미 존재하는 닉네임입니다.");
        }

        Member member = Member.builder()
                .email(signUpRequestDTO.getEmail())
                .password(signUpRequestDTO.getPassword())
                .nickname(signUpRequestDTO.getNickname())
                .name(signUpRequestDTO.getName())
                .introduce(signUpRequestDTO.getIntroduce())
                .phoneNumber(signUpRequestDTO.getPhoneNumber())
                .role(MemberRole.USER)
                .build();

        member.hashPassword(encoder);
        memberRepository.save(member);
    }

    @Transactional
    public TokenDto login(LoginDTO loginDTO) {
        // 이메일로 회원 정보 조회
        Member member = memberRepository.findByEmail(loginDTO.getEmail())
                .orElseThrow(() -> new RuntimeException("회원 정보를 찾을 수 없습니다."));

        // 비밀번호 일치 여부 확인
        if (member.getMemberType() == MemberType.GENERAL) {
            if (!encoder.matches(loginDTO.getPassword(), member.getPassword())) {
                throw new RuntimeException("비밀번호가 일치하지 않습니다.");
            }
        }

        // Access Token 생성
        String accessToken = jwtService.createToken(member.getEmail(), JwtTokenType.ACCESS_TOKEN);

        // Refresh Token 생성
        String refreshToken = jwtService.createToken(member.getEmail(), JwtTokenType.REFRESH_TOKEN);

        // Refresh Token DB에 저장
        jwtService.updateRefreshToken(accessToken, refreshToken);
//        memberRepository.save(registerOrUpdateMember(member.getEmail(), member.getName(), member.getSocialId(), member.getProvider()));

        return new TokenDto(accessToken, refreshToken);
    }


    // 새로 추가한 메서드
    @Transactional
    public Member registerOrUpdateMember(String email, String name, String providerId, String provider) {
        MemberType memberType;
        if (provider.equals("google")) {
            memberType = MemberType.GOOGLE;
        } else {
            memberType = MemberType.GENERAL;
        }

        return memberRepository.findByEmail(email)
                .map(member -> {
                    member.updateName(name);
                    member.updateOAuthInfo(providerId, provider);
                    member.updateMemberType(memberType);
                    return member;
                })
                .orElseGet(() ->{
                    Member newMember = Member.builder()
                            .email(email)
                            .name(name)
                            .socialId(providerId)
                            .provider(provider)
                            .role(MemberRole.USER)
                            .memberType(memberType)
                            .build();
                    return memberRepository.save(newMember);
                });
    }



}