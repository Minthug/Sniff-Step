package SniffStep.common.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String BEARER_PREFIX = "Bearer ";

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String jwt = resolveToken(request);

        if (StringUtils.hasText(jwt) && jwtTokenProvider.validateToken(jwt)) {
            Authentication authentication = jwtTokenProvider.getAuthentication(jwt);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }

    // Request Header에서 Token을 추출
    private String resolveToken(HttpServletRequest request) {
        String bearerToken =  request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(7);
        }
        return null;
    }


//    private String getToken(HttpServletRequest request) {
//        String headerAuth = request.getHeader("Authorization");
//        if (!StringUtils.isEmpty(headerAuth) && StringUtils.startsWith(headerAuth, "Bearer ")) {
//            return headerAuth.substring(7);
//        }
//        return "";
//    }
//
//    private void checkLogout(String accessToken) {
//        if (logoutTokenRedisRepository.existsById(accessToken)) {
//            throw new IllegalArgumentException("Already logged out member");
//        }
//    }
//
//    private void validateAccessToken(String accessToken, UserDetails userDetails) {
//        if (!jwtTokenProvider.validateToken(accessToken, userDetails)) {
//            throw new IllegalArgumentException("Invalid access token");
//        }
//    }
//
//    private void successfulAuthentication(HttpServletRequest request, UserDetails userDetails) {
//        SecurityContext context = SecurityContextHolder.createEmptyContext();
//
//        UsernamePasswordAuthenticationToken authenticationToken =
//                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//        context.setAuthentication(authenticationToken);
//
//        SecurityContextHolder.setContext(context);
//    }
}
