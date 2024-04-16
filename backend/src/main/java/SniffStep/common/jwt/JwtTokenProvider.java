package SniffStep.common.jwt;

import SniffStep.common.config.security.CustomMemberDetailsService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

import static SniffStep.common.jwt.JwtTokenExpireEnum.ACCESS_TOKEN_EXPIRE_TIME;
import static SniffStep.common.jwt.JwtTokenExpireEnum.REFRESH_TOKEN_EXPIRE_TIME;

@Slf4j
@Component
public class JwtTokenProvider {

    private static final String AUTHORITIES_KEY = "auth";
    private static final String BEARER_TYPE = "bearer";

    private final String secretKey;
    private final CustomMemberDetailsService customMemberDetailsService;

    public JwtTokenProvider(Environment env, CustomMemberDetailsService customMemberDetailsService) {
        this.secretKey = env.getProperty("jwt.secret-key");
        this.customMemberDetailsService = customMemberDetailsService;
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey(secretKey))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String getUsername(String token) {
        return extractAllClaims(token).get("username", String.class);
    }

    public Key getSigningKey(String secretKey) {
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public Boolean isTokenExpired(String token) {
        Date expiration = extractAllClaims(token).getExpiration();
        return expiration.before(new Date());
    }

    public String generateAccessToken(String username) {
        String token = doGenerateToken(username, ACCESS_TOKEN_EXPIRE_TIME.getValue());
        log.debug("generateAccessToken: {}", token);
        return token;
    }

    public String generateRefreshToken(String username) {
        return doGenerateToken(username, REFRESH_TOKEN_EXPIRE_TIME.getValue());
    }

    public String doGenerateToken(String username, Long expireTime) {
        Claims claims = Jwts.claims();
        claims.put("username", username);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expireTime))
                .signWith(getSigningKey(secretKey), SignatureAlgorithm.HS512)
                .compact();
    }

    public boolean validateToken(String accessToken, UserDetails userDetails) {
        String username = getUsername(accessToken);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(accessToken));
    }

    public long getRemainMilliseconds(String token) {
        Date expiration = extractAllClaims(token).getExpiration();
        Date now = new Date();
        return expiration.getTime() - now.getTime();
    }

    public UserDetails getUserDetailsFromToken(String token) {
        String username = getUsername(token);
        if (username != null) {
            return customMemberDetailsService.loadUserByUsername(username);
        }
        return null;
    }


}
