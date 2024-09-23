package SniffStep.common.jwt.service;

import SniffStep.common.jwt.dto.TokenDto;
import SniffStep.entity.JwtTokenType;
import SniffStep.repository.MemberRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.InvalidParameterException;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Getter
@Slf4j
public class JwtService {

    @Value("${spring.config.jwt.secret-key}")
    private String secretKey;

    @Value("${spring.config.jwt.accessTokenExpiration}")
    private Long accessTokenExpirationPeriod;

    @Value("${spring.config.jwt.refreshTokenExpiration}")
    private Long refreshTokenExpirationPeriod;

    @Value("${spring.config.jwt.header}")
    private String accessHeader;

    @Value("${spring.config.jwt.refresh.header}")
    private String refreshHeader;

    private final MemberRepository memberRepository;


    public String createToken(String email, JwtTokenType tokenType) {
        Date now = new Date();
        Date expiration = calculateExpirationDate(now, tokenType);
        String subject = tokenType == JwtTokenType.ACCESS_TOKEN ? JwtConstans.ACCESS_TOKEN_SUBJECT : JwtConstans.REFRESH_TOKEN_SUBJECT;

        return JWT.create()
                .withSubject(subject)
                .withExpiresAt(expiration)
                .withClaim(JwtConstans.EMAIL_CLAIMS, email)
                .sign(Algorithm.HMAC512(secretKey));
    }

    private Date calculateExpirationDate(Date now, JwtTokenType tokenType) {
        long expirationPeriod = tokenType == JwtTokenType.ACCESS_TOKEN ? accessTokenExpirationPeriod : refreshTokenExpirationPeriod;
        return new Date(now.getTime() + expirationPeriod);
    }

    private String extractEmailFromToken(String token) {
        DecodedJWT jwt = JWT.require(Algorithm.HMAC512(secretKey))
                .build()
                .verify(token);
        return jwt.getClaim(JwtConstans.EMAIL_CLAIMS).asString();
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
        CookieUtil.addCookie(response, "accessToken", accessToken, accessTokenExpirationPeriod / 1000, true);
    }

    public void sendRefreshTokenCookie(HttpServletResponse response, String refreshToken) {
        CookieUtil.addCookie(response, "refreshToken", refreshToken, refreshTokenExpirationPeriod / 1000, true);
    }

    public void sendAccessAndRefreshTokenCookie(HttpServletResponse response, String accessToken, String refreshToken) {
        sendAccessTokenCookie(response, accessToken);
        sendRefreshTokenCookie(response, refreshToken);
    }



    public Optional<String> extractRefreshToken(HttpServletRequest request) {
        log.info("Attempting to extract Refresh Token");

        // 1. 헤더에서 추출 시도 (Bearer 접두사 있는 경우)
        Optional<String> fromHeader = Optional.ofNullable(request.getHeader(refreshHeader))
                .filter(token -> token.startsWith(JwtConstans.BEARER))
                .map(token -> token.replace(JwtConstans.BEARER, ""));
        if (fromHeader.isPresent()) {
            log.info("Refresh Token extracted from header with Bearer prefix");
            return fromHeader;
        }

        // 2. 헤더에서 추출 시도 (Bearer 접두사 없는 경우)
        fromHeader = Optional.ofNullable(request.getHeader(refreshHeader));
        if (fromHeader.isPresent()) {
            log.info("Refresh Token extracted from header without Bearer prefix");
            return fromHeader;
        }

        // 3. 쿠키에서 추출 시도
        Optional<String> fromCookie = extractTokenFromCookie(request, "refreshToken");
        if (fromCookie.isPresent()) {
            log.info("Refresh Token extracted from cookie");
            return fromCookie;
        }

        // 4. 요청 파라미터에서 추출 시도
        String fromParameter = request.getParameter("refreshToken");
        if (fromParameter != null && !fromParameter.isEmpty()) {
            log.info("Refresh Token extracted from request parameter");
            return Optional.of(fromParameter);
        }

        log.warn("No Refresh Token found in the request");
        return Optional.empty();
    }

    private Optional<String> extractTokenFromCookie(HttpServletRequest request, String cookieName) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookieName.equals(cookie.getName())) {
                    return Optional.of(cookie.getValue());
                }
            }
        }
        return Optional.empty();
    }


    public Optional<String> extractAccessToken(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader(accessHeader))
                .filter(accessToken -> accessToken.startsWith(JwtConstans.BEARER))
                .map(accessToken -> accessToken.replace(JwtConstans.BEARER, ""));
    }

    public Optional<String> extractEmail(String accessToken) {
        try {
            return Optional.ofNullable(JWT.require(Algorithm.HMAC512(secretKey))
                    .build()
                    .verify(accessToken)
                    .getClaim(JwtConstans.EMAIL_CLAIMS)
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


    @Transactional
    public TokenDto reissue(String refreshToken, HttpServletResponse response) {
        if (refreshToken == null || refreshToken.isEmpty()) {
            throw new InvalidParameterException("Refresh Token이 없습니다.");
        }
        // Refresh Token 유효성 검사
        if (!isTokenValid(refreshToken)) {
            throw new InvalidParameterException("유효하지 않은 Refresh Token입니다.");
        }

        try {
            // Refresh Token에서 이메일 추출
            String email = extractEmailFromToken(refreshToken);

            // DB에서 저장된 RefreshToken 확인 (필요한 경우)

            // 새로운 Access Token 생성
            String newAccessToken = createToken(email, JwtTokenType.ACCESS_TOKEN);

            // 새로운 Refresh Token 생성 (선택적)
            String newRefreshToken = createToken(email, JwtTokenType.REFRESH_TOKEN);

            // Access Token을 응답 헤더에 추가
            sendAccessToken(response, newAccessToken);

            return new TokenDto(newAccessToken, newRefreshToken);
        } catch (Exception e) {
            throw new InvalidParameterException("토큰 재발급 중 오류가 발생했습니다.");
        }
    }

    class JwtConstans {
        public static final String ACCESS_TOKEN_SUBJECT = "AccessToken";
        public static final String REFRESH_TOKEN_SUBJECT = "RefreshToken";
        public static final String EMAIL_CLAIMS = "email";
        public static final String BEARER = "Bearer ";
    }


    private static class CookieUtil {
        public static void addCookie(HttpServletResponse response, String name, String value, long maxAge, boolean httpOnly) {
            Cookie cookie = new Cookie(name, value);
            cookie.setPath("/");
            cookie.setSecure(true);
            cookie.setMaxAge(7 * 24 * 60 * 60); // 7 Days
            cookie.setHttpOnly(true);
            response.addCookie(cookie);
        }

        // 아마 리프레쉬 토큰이나 토큰 만료시에 써야할듯
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

        // 쿠키 삭제
        public static void deleteCookie(HttpServletRequest request, HttpServletResponse response, String name) {
            Cookie[] cookies = request.getCookies();
            if (cookies != null && cookies.length > 0) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals(name)) {
                        cookie.setValue("");
                        cookie.setPath("/");
                        cookie.setMaxAge(0);
                        response.addCookie(cookie);
                    }
                }
            }
        }
    }
}
