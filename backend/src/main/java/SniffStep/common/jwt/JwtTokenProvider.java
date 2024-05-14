package SniffStep.common.jwt;

import SniffStep.common.exception.AuthenticationException;
import SniffStep.common.jwt.dto.TokenDTO;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JwtTokenProvider {


    private static final String AUTHORITIES_KEY = "auth";
    private static final String BEARER_TYPE = "bearer";
    private static final String EXPIRED_ACCESS_TOKEN_MESSAGE = "만료된 Access Token입니다.";
    private static final String EXPIRED_REFRESH_TOKEN_MESSAGE = "만료된 Refresh Token입니다.";
    private final String EMAIL_KEY = "email";

    private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 30; // 30Min
    private static final long REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24 * 7; // 7Day

    private final Key secretKey;

    @Value("${jwt.secret-key}")
    private String jwtAccessTokenSecret;
    @Value("${jwt.accessTokenExpireTime}")
    private long jwtAccessTokenExpireTime;
    @Value("${jwt.refreshTokenExpireTime}")
    private long jwtRefreshTokenExpireTime;

    public JwtTokenProvider(@Value("${jwt.secret-key}") String secretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.secretKey = Keys.hmacShaKeyFor(keyBytes);
    }
    public TokenDTO generateTokenDto(Authentication authentication) {
        // bring authorities
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        long now = (new Date()).getTime();


        // created accessToken and refreshToken
        Date accessTokenExpiresIn = new Date(now + ACCESS_TOKEN_EXPIRE_TIME);
        String accessToken = Jwts.builder()
                .setSubject(authentication.getName())
                .claim(AUTHORITIES_KEY, authorities)
                .signWith(secretKey, SignatureAlgorithm.HS512)
                .compact();

        String refreshToken = Jwts.builder()
                .setExpiration(new Date(now + REFRESH_TOKEN_EXPIRE_TIME))
                .signWith(secretKey, SignatureAlgorithm.HS512)
                .compact();

        return TokenDTO.builder()
                .grantType(BEARER_TYPE)
                .accessToken(accessToken)
                .accessTokenExpiresIn(accessTokenExpiresIn.getTime())
                .refreshToken(refreshToken)
                .build();
    }

    public Authentication getAuthentication (String accessToken){
        // token parsing
        Claims claims = parseClaims(accessToken);

        if (claims.get(AUTHORITIES_KEY) == null) {
            throw new RuntimeException("권한 정보가 없는 토큰입니다.");
        }

        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        UserDetails principal = new User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }

    public boolean validateToken (String token){
        try {
            Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.info("Wrong JWT signature");
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT token");
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT token");
        } catch (IllegalArgumentException e) {
            log.info("JWT token compact of handler are invalid");
        }
        return false;
    }

    private Claims parseClaims (String accessToken){
        try {
            return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(accessToken).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    public String getUid (String accessToken){
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(accessToken).getBody().getSubject();
    }

    public String getRole (String accessToken){
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(accessToken).getBody().get("role", String.class);
    }


//    --------------------------------------------------------------------------------------------


    public String generateAccessToken(final String email) {
        final Date now = new Date();
        final Date expiryDate = new Date(now.getTime() + jwtAccessTokenExpireTime);
        final SecretKey secretKey = new SecretKeySpec(jwtAccessTokenSecret.getBytes(StandardCharsets.UTF_8), SignatureAlgorithm.HS512.getJcaName());

        return Jwts.builder()
                .claim(EMAIL_KEY, email)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(secretKey)
                .compact();
    }

    public String extractEmailFromAccessToken(final String token) {
        validateAccessToken(token);
        final Jws<Claims> claimsJwt = getAccessTokenParser().parseClaimsJws(token);
        String extractedEmail = claimsJwt.getBody().get(EMAIL_KEY, String.class);
        if (extractedEmail == null) {
            final String logMessage = "인증 실패(JWT accessToken Payload 이메일 누락 : " + token;
            throw new AuthenticationException.FailAuthenticationException(logMessage);
        }
        return extractedEmail;
    }

    private JwtParser getAccessTokenParser() {
        return Jwts.parserBuilder()
                .setSigningKey(jwtAccessTokenSecret.getBytes(StandardCharsets.UTF_8))
                .build();
    }

    private void validateAccessToken(final String token) {
        try {
            final Claims claims = getAccessTokenParser().parseClaimsJws(token).getBody();
        } catch (MalformedJwtException | UnsupportedJwtException e) {
            final String logMessage = "인증 실패(잘못된 액세스 토큰) - 토큰 : " + token;

            throw new AuthenticationException.FailAuthenticationException(logMessage);
        } catch (ExpiredJwtException e) {
            throw new ExpiredJwtException(null, null, EXPIRED_ACCESS_TOKEN_MESSAGE);
        }
    }

    public String generatedRefreshToken(final String email) {
        final Date now = new Date();
        final Date expiryDate = new Date(now.getTime() + jwtRefreshTokenExpireTime);
        final SecretKey secretKey = new SecretKeySpec(jwtAccessTokenSecret.getBytes(StandardCharsets.UTF_8), SignatureAlgorithm.HS512.getJcaName());

        return Jwts.builder()
                .claim(EMAIL_KEY, email)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(secretKey)
                .compact();
    }

    public String extractEmailFromRefreshToken(final String token) {
        validateRefreshToken(token);
        final Jws<Claims> claimsJwt = getRefreshTokenParser().parseClaimsJws(token);
        String extractedEmail = claimsJwt.getBody().get(EMAIL_KEY, String.class);
        if (extractedEmail == null) {
            final String logMessage = "인증 실패(JWT refreshToken Payload 이메일 누락 : " + token;
            throw new AuthenticationException.FailAuthenticationException(logMessage);
        }
        return extractedEmail;
    }

    private JwtParser getRefreshTokenParser() {
        return Jwts.parserBuilder()
                .setSigningKey(jwtAccessTokenSecret.getBytes(StandardCharsets.UTF_8))
                .build();
    }

    private void validateRefreshToken(final String token) {
        try {
            final Claims claims = getRefreshTokenParser().parseClaimsJws(token).getBody();
        } catch (MalformedJwtException | UnsupportedJwtException e) {
            final String logMessage = "인증 실패(잘못된 리프레시 토큰) - 토큰 : " + token;

            throw new AuthenticationException.FailAuthenticationException(logMessage);
        } catch (ExpiredJwtException e) {
            throw new ExpiredJwtException(null, null, EXPIRED_REFRESH_TOKEN_MESSAGE);
        }
    }
}