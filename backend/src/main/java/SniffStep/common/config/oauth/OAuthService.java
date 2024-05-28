package SniffStep.common.config.oauth;


import SniffStep.common.jwt.dto.TokenDto;
import SniffStep.common.jwt.service.JwtService;
import SniffStep.entity.JwtTokenType;
import SniffStep.entity.Member;
import SniffStep.repository.MemberRepository;
import SniffStep.service.AuthService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Slf4j
@RequiredArgsConstructor
public class OAuthService {

    private final SocialOAuth socialOAuth;
    private final MemberRepository memberRepository;
    private final JwtService jwtService;
    private final AuthService authService;

    private final GoogleOauth googleOAuth;

    public String request(String type) throws IOException {
        String redirectURL = socialOAuth.getOAuthRedirectURL(type);
        return redirectURL;
    }

    public GetSocialOAuthRes oAuthLogin(String code, String type) throws JsonProcessingException {

        // 액세스 토큰 요청
        GoogleOAuthToken googleOAuthToken = googleOAuth.requestAccessToken(code, type);

        if (googleOAuthToken == null) {
            throw new RuntimeException("액세스 토큰을 받아오지 못했습니다.");
        }

        // 사용자 정보 요청
        ResponseEntity<String> userInfo = socialOAuth.requestUserInfo(googleOAuthToken);
        GoogleUser googleUser = socialOAuth.getUserInfo(userInfo);

        String email = googleUser.getEmail();
        String name = googleUser.getName();
        String providerId = googleUser.getId();
        String provider = "google";

        Member newMember = authService.registerOrUpdateMember(email, name, providerId, provider);


        String accessToken = jwtService.createToken(email, JwtTokenType.ACCESS_TOKEN);

        String refreshToken = jwtService.createToken(email, JwtTokenType.REFRESH_TOKEN);


        jwtService.updateAccessToken(email, accessToken);
        jwtService.updateRefreshToken(email, refreshToken);
        memberRepository.save(newMember);

        GetSocialOAuthRes res = new GetSocialOAuthRes(newMember.getId(), accessToken, refreshToken, "Bearer ", email);
        log.info("GetSocialOAuthRes : {}", res);

        return res;
    }
}

