package SniffStep.common.jwt;

import SniffStep.common.jwt.dto.TokenResponseDTO;
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
    private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 30; // 30Min
    private static final long REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24 * 7; // 7Day

    private final Key secretKey;

    public JwtTokenProvider(@Value("${jwt.secret-key}") String secretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.secretKey = Keys.hmacShaKeyFor(keyBytes);
    }

    public TokenResponseDTO generateTokenDto(Authentication authentication) {
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

        return TokenResponseDTO.builder()
                .grantType(BEARER_TYPE)
                .accessToken(accessToken)
                .accessTokenExpiresIn(accessTokenExpiresIn.getTime())
                .refreshToken(refreshToken)
                .build();
    }

    public Authentication getAuthentication(String accessToken) {
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

    public boolean validateToken(String token) {
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

    private Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(accessToken).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }


    //    public JwtTokenProvider(Environment env, CustomMemberDetailsService customMemberDetailsService) {
//        this.secretKey = env.getProperty("jwt.secret-key");
//        this.customMemberDetailsService = customMemberDetailsService;
//    }


//    public Claims extractAllClaims(String token) {
//        return Jwts.parserBuilder()
//                .setSigningKey(getSigningKey(secretKey))
//                .build()
//                .parseClaimsJws(token)
//                .getBody();
//    }
//
//    public String getUsername(String token) {
//        return extractAllClaims(token).get("username", String.class);
//    }
//
//    public Key getSigningKey(String secretKey) {
//        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
//        return Keys.hmacShaKeyFor(keyBytes);
//    }
//
//    public Boolean isTokenExpired(String token) {
//        Date expiration = extractAllClaims(token).getExpiration();
//        return expiration.before(new Date());
//    }
//
//    public String generateAccessToken(String username) {
//        String token = doGenerateToken(username, ACCESS_TOKEN_EXPIRE_TIME.getValue());
//        log.debug("generateAccessToken: {}", token);
//        return token;
//    }
//
//    public String generateRefreshToken(String username) {
//        return doGenerateToken(username, REFRESH_TOKEN_EXPIRE_TIME.getValue());
//    }
//
//    public String doGenerateToken(String username, Long expireTime) {
//        Claims claims = Jwts.claims();
//        claims.put("username", username);
//
//        return Jwts.builder()
//                .setClaims(claims)
//                .setIssuedAt(new Date(System.currentTimeMillis()))
//                .setExpiration(new Date(System.currentTimeMillis() + expireTime))
//                .signWith(getSigningKey(secretKey), SignatureAlgorithm.HS512)
//                .compact();
//    }
//
//    public boolean validateToken(String accessToken, UserDetails userDetails) {
//        String username = getUsername(accessToken);
//        return (username.equals(userDetails.getUsername()) && !isTokenExpired(accessToken));
//    }
//
//    public long getRemainMilliseconds(String token) {
//        Date expiration = extractAllClaims(token).getExpiration();
//        Date now = new Date();
//        return expiration.getTime() - now.getTime();
//    }
}
