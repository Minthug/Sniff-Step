package SniffStep.common.jwt;

import SniffStep.common.exception.AuthenticationException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;


@Component
public class JwtTokenExtractor {

    private static final String PREFIX_BEARER = "Bearer ";
    private static final String ACCESS_TOKEN_HEADER = HttpHeaders.AUTHORIZATION;
    private static final String REFRESH_TOKEN_HEADER = "Authorization-Refresh";

    public String extractAccessToken(final HttpServletRequest request) {
        final String accessToken = request.getHeader(ACCESS_TOKEN_HEADER);
        if (StringUtils.hasText(accessToken) && accessToken.startsWith(PREFIX_BEARER)) {
            return accessToken.substring(PREFIX_BEARER.length());
        }
        final String logMessage = "인증 실패(accessToken 추출 실패) - 토큰 : " + accessToken;
        throw new AuthenticationException.FailAuthenticationException(logMessage);
    }

    public String extractRefreshToken(final HttpServletRequest request) {
        final String refreshToken = request.getHeader(REFRESH_TOKEN_HEADER);
        if (StringUtils.hasText(refreshToken) && refreshToken.startsWith(PREFIX_BEARER)) {
            return refreshToken.substring(PREFIX_BEARER.length());
        }
        final String logMessage = "인증 실패(refreshToken 추출 실패) - 토큰 : " + refreshToken;
        throw new AuthenticationException.FailAuthenticationException(logMessage);
    }
}
