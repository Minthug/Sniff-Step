package SniffStep.common.jwt.filter;

import SniffStep.common.exception.JwtAuthenticationException;
import SniffStep.common.jwt.service.JwtService;
import SniffStep.entity.JwtTokenType;
import SniffStep.entity.Member;
import SniffStep.repository.MemberRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationProcessingFilter extends OncePerRequestFilter {

    private static final Set<String> NO_CHECK_URL = new HashSet<>(Arrays.asList(
            "/login", "/signup", "/refresh", "/v1/oauth/oauth2/authorization/{provider}",
            "/v1/oauth/oauth2/authorization/google", "/v1/oauth/google",
            "/v1/oauth/oauth2/google/redirect", "/v1/oauth/{type}"));

    private final JwtService jwtService;
    private final MemberRepository memberRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if (isNoCheckUrl(request.getRequestURI())) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            processJwtAuthentication(request, response, filterChain);
        } catch (Exception e) {
            handleFilterException(response, e);
        }
    }

    private boolean isNoCheckUrl(String requestUri) {
        return NO_CHECK_URL.contains(requestUri);
    }

    private void processJwtAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        String refreshToken = jwtService.extractRefreshToken(request)
                .filter(jwtService::isTokenValid)
                .orElse(null);

        if (refreshToken != null) {
            processRefreshToken(response, refreshToken);
        } else {
            processAccessToken(request, response, filterChain);
        }
    }

    private void processRefreshToken(HttpServletResponse response, String refreshToken) {
        memberRepository.findByRefreshToken(refreshToken)
                .ifPresentOrElse(
                        user -> reissueTokens(response, user),
                        () -> {
                            log.warn("Refresh token is valid but member not found");
                            throw new JwtAuthenticationException("Refresh token is valid but member not found");
                        }
                );
    }

    private void processAccessToken(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        jwtService.extractAccessToken(request)
                .filter(jwtService::isTokenValid)
                .ifPresent(this::authenticationUser);

        filterChain.doFilter(request, response);
    }

    private void authenticationUser(String accessToken) {
        jwtService.extractEmail(accessToken)
                .flatMap(memberRepository::findByEmail)
                .ifPresent(this::saveAuthentication);
    }


    private void reissueTokens(HttpServletResponse response, Member user) {
        String newRefreshToken = reIssueRefreshToken(user);
        jwtService.sendAccessAndRefreshToken(response, user.getEmail(), newRefreshToken);
    }

    public void checkRefreshTokenAndReIssueAccessToken(HttpServletResponse response, String refreshToken) {
        memberRepository.findByRefreshToken(refreshToken)
                .ifPresentOrElse(
                        user -> {
                            String reIssuedRefreshToken = reIssueRefreshToken(user);
                            jwtService.sendAccessAndRefreshToken(response, user.getEmail(), reIssuedRefreshToken);
                        },
                        () -> {
                            log.warn("Refresh token is valid but member not found");
                            throw new RuntimeException("Refresh token is valid but member not found");
                        }
                );
    }

    public String reIssueRefreshToken(Member member) {
        String reIssueRefreshToken = jwtService.createToken(member.getEmail(), JwtTokenType.REFRESH_TOKEN);
        member.updateRefreshToken(reIssueRefreshToken);
        memberRepository.saveAndFlush(member);
        return reIssueRefreshToken;
    }

    public void checkAccessTokenAndAuthentication(HttpServletRequest request,
                HttpServletResponse response,
                FilterChain filterChain) throws ServletException, IOException {
        log.info("checkAccessTokenAndAuthentication() call");
        jwtService.extractAccessToken(request)
                .filter(jwtService::isTokenValid)
                .ifPresent(accessToken -> jwtService.extractEmail(accessToken)
                        .ifPresent(email -> memberRepository.findByEmail(email)
                                .ifPresent(this::saveAuthentication)));

        filterChain.doFilter(request, response);
    }

//    public void saveAuthentication(Member member) {
//        UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
//                .username(member.getEmail())
//                .password("") // 소셜 로그인 사용자의 경우 비밀번호를 사용하지 않음
//                .authorities(member.getRole().name())
//                .build();
//
//        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//    }

    private void saveAuthentication(Member member) {
        UserDetails userDetails = createUserDetails(member);
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private UserDetails createUserDetails(Member member) {
        return User.builder()
                .username(member.getEmail())
                .password("")
                .authorities(member.getRole().name())
                .build();
    }

    private void handleFilterException(HttpServletResponse response, Exception e) throws IOException {
        log.error("Jwt Authentication error", e);
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
    }
}
