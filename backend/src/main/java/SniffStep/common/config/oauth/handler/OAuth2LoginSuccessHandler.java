package SniffStep.common.config.oauth.handler;

import SniffStep.common.config.oauth.CustomOAuth2User;
import SniffStep.common.jwt.service.JwtService;
import SniffStep.entity.JwtTokenType;
import SniffStep.entity.MemberRole;
import SniffStep.repository.MemberRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtService jwtService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("OAuth2 Login 성공");


            CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();

            if (oAuth2User.getRole() == MemberRole.GUEST) {
                String accessToken = jwtService.createToken(oAuth2User.getEmail(), JwtTokenType.ACCESS_TOKEN);
                jwtService.sendAccessToken(response, accessToken);
                response.addHeader(jwtService.getAccessHeader(), "Bearer " + accessToken);
                response.sendRedirect("http://localhost:8080/api/test/main");

                jwtService.sendAccessAndRefreshToken(response, accessToken, null);
                jwtService.sendAccessAndRefreshTokenCookie(response, accessToken, null);
            } else {
                loginSuccess(response, oAuth2User);
            }
    }

    private void loginSuccess(HttpServletResponse response, CustomOAuth2User oAuth2User) throws IOException {
        String accessToken = jwtService.createToken(oAuth2User.getEmail(), JwtTokenType.ACCESS_TOKEN);
        String refreshToken = jwtService.createToken(oAuth2User.getEmail(), JwtTokenType.REFRESH_TOKEN);
        response.addHeader(jwtService.getAccessHeader(), "Bearer " + accessToken);
        response.addHeader(jwtService.getRefreshHeader(), "Bearer " + refreshToken);

        jwtService.sendAccessAndRefreshTokenCookie(response, accessToken, refreshToken);
        jwtService.updateAccessToken(oAuth2User.getEmail(), accessToken);
        jwtService.updateRefreshToken(oAuth2User.getEmail(), refreshToken);
    }
}
