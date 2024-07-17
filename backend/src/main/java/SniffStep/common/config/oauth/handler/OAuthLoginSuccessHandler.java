package SniffStep.common.config.oauth.handler;

import SniffStep.common.jwt.service.JwtService;
import SniffStep.entity.JwtTokenType;
import SniffStep.entity.MemberRole;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuthLoginSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtService jwtService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("OAuth2 Login 성공");


            CustomOAuthUser oAuth2User = (CustomOAuthUser) authentication.getPrincipal();

            if (oAuth2User.getRole() == MemberRole.GUEST) {
                String accessToken = jwtService.createToken(oAuth2User.getEmail(), JwtTokenType.ACCESS_TOKEN);
                String refreshToken = jwtService.createToken(oAuth2User.getEmail(), JwtTokenType.REFRESH_TOKEN);
                response.addHeader(jwtService.getAccessHeader(), "Bearer " + accessToken);
                jwtService.sendAccessAndRefreshTokenCookie(response, accessToken, null);
                response.sendRedirect("http://localhost:3000");

            } else {
                loginSuccess(response, oAuth2User);
            }
    }

    private void loginSuccess(HttpServletResponse response, CustomOAuthUser oAuth2User) throws IOException {
        String accessToken = jwtService.createToken(oAuth2User.getEmail(), JwtTokenType.ACCESS_TOKEN);
        String refreshToken = jwtService.createToken(oAuth2User.getEmail(), JwtTokenType.REFRESH_TOKEN);
        response.addHeader(jwtService.getAccessHeader(), "Bearer " + accessToken);
        response.addHeader(jwtService.getRefreshHeader(), "Bearer " + refreshToken);

        jwtService.sendAccessAndRefreshTokenCookie(response, accessToken, refreshToken);
        jwtService.updateAccessToken(oAuth2User.getEmail(), accessToken);
        jwtService.updateRefreshToken(oAuth2User.getEmail(), refreshToken);
        response.sendRedirect("http://localhost:3000");
    }
}
