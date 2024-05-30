package SniffStep.common.jwt.service;

import SniffStep.entity.JwtTokenType;
import SniffStep.repository.MemberRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Getter
@Slf4j
public class JwtService {

    @Value("${jwt.secret-key}")
    private String secretKey;

    @Value("${jwt.accessTokenExpireTime}")
    private Long accessTokenExpirationPeriod;

    @Value("${jwt.refreshTokenExpireTime}")
    private Long refreshTokenExpirationPeriod;

    @Value("${jwt.header}")
    private String accessHeader;

    @Value("${jwt.refresh.header}")
    private String refreshHeader;

    private final MemberRepository memberRepository;

    private static final String ACCESS_TOKEN_SUBJECT = "AccessToken";
    private static final String REFRESH_TOKEN_SUBJECT = "RefreshToken";
    private static final String EMAIL_CLAIM = "email";
    private static final String BEARER = "Bearer ";

    public String createToken(String email, JwtTokenType tokenType) {
        Date now = new Date();
        Date expiration;
        String subject;

        if (tokenType == JwtTokenType.ACCESS_TOKEN) {
            expiration = new Date(now.getTime() + accessTokenExpirationPeriod);
            subject = ACCESS_TOKEN_SUBJECT;
        } else {
            expiration = new Date(now.getTime() + refreshTokenExpirationPeriod);
            subject = REFRESH_TOKEN_SUBJECT;
        }

        return JWT.create()
                .withSubject(subject)
                .withExpiresAt(expiration)
                .withClaim(EMAIL_CLAIM, email)
                .sign(Algorithm.HMAC512(secretKey));
    }


    public void sendAccessToken(HttpServletResponse response, String accessToken) {
        response.setStatus(HttpServletResponse.SC_OK);

        response.setHeader(accessHeader, accessToken);
        log.info("재발급된 Access Token : {}", accessToken);
    }

    public void sendAccessAndRefreshToken(HttpServletResponse response, String accessToken, String refreshToken) {
        response.setStatus(HttpServletResponse.SC_OK);

        setAccessTokenHeader(response, accessToken);
        setRefreshTokenHeader(response, refreshToken);
        log.info("Access Token, Refresh Token 헤더 설정 완료");
    }

    public void sendAccessTokenCookie(HttpServletResponse response, String accessToken) {
        CookieUtil.addCookie(response, accessHeader, accessToken, accessTokenExpirationPeriod / 1000, true);
    }

    public void sendRefreshTokenCookie(HttpServletResponse response, String refreshToken) {
        CookieUtil.addCookie(response, refreshHeader, refreshToken, refreshTokenExpirationPeriod / 1000, true);
    }

    public void sendAccessAndRefreshTokenCookie(HttpServletResponse response, String accessToken, String refreshToken) {
        sendAccessTokenCookie(response, accessToken);
        sendRefreshTokenCookie(response, refreshToken);
    }



    public Optional<String> extractRefreshToken(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader(refreshHeader))
                .filter(refreshToken -> refreshToken.startsWith(BEARER))
                .map(refreshToken -> refreshToken.replace(BEARER, ""));
    }

    public Optional<String> extractAccessToken(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader(accessHeader))
                .filter(accessToken -> accessToken.startsWith(BEARER))
                .map(accessToken -> accessToken.replace(BEARER, ""));
    }

    public Optional<String> extractEmail(String accessToken) {
        try {
            return Optional.ofNullable(JWT.require(Algorithm.HMAC512(secretKey))
                    .build()
                    .verify(accessToken)
                    .getClaim(EMAIL_CLAIM)
                    .asString());
        } catch (Exception e) {
            log.error("액세스 토큰이 유효하지 않습니다.");
            return Optional.empty();
        }
    }

    // refreshToken Header 설정
    public void setRefreshTokenHeader(HttpServletResponse response, String refreshToken) {
        response.setHeader(refreshHeader, refreshToken);
    }

    // accessToken Header 설정
    public void setAccessTokenHeader(HttpServletResponse response, String accessToken) {
        response.setHeader(accessHeader, accessToken);
    }

// refreshToken DB 저장
    public void updateRefreshToken(String email, String refreshToken) {
        memberRepository.findByEmail(email)
                .ifPresentOrElse(
                        member -> member.updateRefreshToken(refreshToken),
                        () -> log.error("해당하는 회원이 없습니다.")
                );
    }

    public void updateAccessToken(String email, String accessToken) {
        memberRepository.findByEmail(email)
                .ifPresentOrElse(
                        member -> member.updateAccessToken(accessToken),
                        () -> log.error("해당하는 회원이 없습니다.")
                );
    }

    public boolean isTokenValid(String token) {
        try {
            JWT.require(Algorithm.HMAC512(secretKey))
                    .build()
                    .verify(token);
            return true;
        } catch (Exception e) {
            log.info("토큰이 유효하지 않습니다. {}", e.getMessage());
            return false;
        }
    }

    private static class CookieUtil {
        public static void addCookie(HttpServletResponse response, String name, String value, long maxAge, boolean httpOnly) {
            Cookie cookie = new Cookie(name, value);
            cookie.setPath("/");
            cookie.setSecure(true);
            cookie.setMaxAge((int) maxAge);
            cookie.setHttpOnly(httpOnly);
            response.addCookie(cookie);
        }

        public static Optional<Cookie> getCookie(HttpServletRequest request, String name) {
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals(name)) {
                        return Optional.of(cookie);
                    }
                }
            }
                return Optional.empty();
        }
    }
}
