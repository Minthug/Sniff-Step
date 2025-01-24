package SniffStep.service;

import SniffStep.common.exception.DuplicateEmailException;
import SniffStep.common.exception.DuplicateNicknameException;
import SniffStep.common.exception.LoginFailureException;
import SniffStep.common.exception.NotFoundException;
import SniffStep.common.jwt.dto.TokenDto;
import SniffStep.common.jwt.service.JwtService;
import SniffStep.dto.auth.LoginRequest;
import SniffStep.dto.auth.SignUpRequest;
import SniffStep.entity.JwtTokenType;
import SniffStep.entity.Member;
import SniffStep.entity.MemberRole;
import SniffStep.entity.MemberType;
import SniffStep.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.InvalidParameterException;


@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder encoder;
    private final JwtService jwtService;
    private final MemberService memberService;



    @Transactional
    public void signup(SignUpRequest request) throws Exception {
        validateUniqueInfo(request);
        validateRequiredFields(request);

        Member member = Member.builder()
                .email(request.email())
                .password(request.password())
                .nickname(request.nickname())
                .introduce(request.introduce())
                .phoneNumber(request.phoneNumber())
                .imageUrl(request.imageUrl())
                .role(MemberRole.USER)
                .memberType(MemberType.GENERAL)
                .build();

        member.hashPassword(encoder);
        memberRepository.save(member);
    }

    @Transactional
    public TokenDto login(LoginRequest loginRequest) {
        // 이메일로 회원 정보 조회
        Member member = memberRepository.findByEmail(loginRequest.email())
                .orElseThrow(() -> new NotFoundException("회원 정보를 찾을 수 없습니다."));

        if (member.getMemberType() == null) {
            member.updateMemberType(MemberType.GENERAL);
            memberRepository.save(member);
        }

        // 비밀번호 일치 여부 확인
        if (member.getMemberType() == MemberType.GENERAL) {
            if (!encoder.matches(loginRequest.password(), member.getPassword())) {
                throw new LoginFailureException("비밀번호가 일치하지 않습니다.");
            }
        }

        // Access Token 생성
        String accessToken = jwtService.createToken(member.getEmail(), JwtTokenType.ACCESS_TOKEN);

        // Refresh Token 생성
        String refreshToken = jwtService.createToken(member.getEmail(), JwtTokenType.REFRESH_TOKEN);

        // Refresh Token DB에 저장
        jwtService.updateAccessToken(member.getEmail(), refreshToken);
        jwtService.updateRefreshToken(accessToken, refreshToken);

        return new TokenDto(accessToken, refreshToken);
    }


    // 새로 추가한 메서드
    @Transactional
    public Member registerOrUpdateMember(String email, String name, String providerId, String provider, String profileImageUrl) {
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
                    member.updateProfileImageUrl(profileImageUrl);
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
                            .imageUrl(profileImageUrl)
                            .build();
                    return memberRepository.save(newMember);
                });
    }

    private void validateUniqueInfo(SignUpRequest dto) {
        if (memberRepository.findByEmail(dto.email()).isPresent()) {
            throw new DuplicateEmailException("이미 존재하는 이메일입니다.");
        }

        if (memberRepository.findByNickname(dto.nickname()).isPresent()) {
            throw new DuplicateNicknameException("이미 존재하는 닉네임입니다.");
        }
    }

    private void validateRequiredFields(SignUpRequest dto) {
        if (StringUtils.isEmpty(dto.email())) {
            throw new InvalidParameterException("이메일은 필수 입력값입니다.");
        }

        if (StringUtils.isBlank(dto.password())) {
            throw new InvalidParameterException("비밀번호는 필수 입력값입니다.");
        }

        if (StringUtils.isBlank(dto.nickname())) {
            throw new InvalidParameterException("닉네임은 필수 입력값입니다.");
        }

    }
}