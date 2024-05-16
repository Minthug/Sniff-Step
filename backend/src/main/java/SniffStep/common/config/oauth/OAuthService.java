package SniffStep.common.config.oauth;


import SniffStep.common.jwt.service.JwtService;
import SniffStep.entity.Member;
import SniffStep.entity.MemberRole;
import SniffStep.repository.MemberRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class OAuthService {

    private final SocialOAuth socialOAuth;
    private final MemberRepository memberRepository;
    private final JwtService jwtService;

    public String request(String type) throws IOException {
        String redirectURL = socialOAuth.getOAuthRedirectURL();
        return redirectURL;
    }

    public GetSocialOAuthRes oAuthLogin(String code, String type) throws JsonProcessingException {

        // Access Token 요청
        ResponseEntity<String> accessTokenResponse = socialOAuth.requestAccessToken(code, type);
        GoogleOAuthToken googleOAuthToken = socialOAuth.getAccessToken(accessTokenResponse);

        // 사용자 정보 요청
        ResponseEntity<String> userInfo = socialOAuth.requestUserInfo(googleOAuthToken);
        GoogleUser googleUser = socialOAuth.getUserInfo(userInfo);

        String email = googleUser.getEmail();

        // 화원 정보 조회 또는 생성
        Member member = memberRepository.findByEmail(email)
                .orElseGet(() -> {
                    Member newMember = Member.builder()
                            .email(email)
                            .name(googleUser.getName())
                            .role(MemberRole.USER)
                            .build();
                    return memberRepository.save(newMember);
                });

        String accessToken = jwtService.createAccessToken(email);

        String refreshToken = jwtService.createRefreshToken();

        jwtService.updateRefreshToken(email, refreshToken);
        memberRepository.save(member);

        return new GetSocialOAuthRes(accessToken, member.getId(), refreshToken, googleOAuthToken.getTokenType());

    }
}

