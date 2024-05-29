package SniffStep.common.jwt.filter;

import SniffStep.common.jwt.service.JwtService;
import SniffStep.common.utils.PasswordUtil;
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
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationProcessingFilter extends OncePerRequestFilter {

    private static final String NO_CHECK_URL = "/login";

    private final JwtService jwtService;
    private final MemberRepository memberRepository;

//    private GrantedAuthoritiesMapper authoritiesMapper = new NullAuthoritiesMapper();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if (request.getRequestURI().equals(NO_CHECK_URL)) {
            filterChain.doFilter(request, response);
            return;
        }

        try {

            String refreshToken = jwtService.extractRefreshToken(request)
                    .filter(jwtService::isTokenValid)
                    .orElse(null);

            if (refreshToken != null) {
                checkRefreshTokenAndReIssueAccessToken(response, refreshToken);
            } else {
                checkAccessTokenAndAuthentication(request, response, filterChain);
            }
        } catch (Exception e) {
            log.error("JwtAuthenticationProcessingFilter error", e);
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
        }
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
//            log.info("checkAccessTokenAndAuthentication() call");
//            jwtService.extractAccessToken(request)
//                    .filter(jwtService::isTokenValid)
//                    .ifPresentOrElse(
//                            accessToken -> jwtService.extractEmail(accessToken)
//                                    .flatMap(memberRepository::findByEmail)
//                                    .ifPresentOrElse(
//                                            member -> {
//                                                saveAuthentication(member);
//                                                try {
//                                                    filterChain.doFilter(request, response);
//                                                } catch (IOException e) {
//                                                    throw new RuntimeException(e);
//                                                } catch (ServletException e) {
//                                                    throw new RuntimeException(e);
//                                                }
//                                            },
//                                            () -> {
//                                                log.warn("Access token is valid but member not found");
//                                                throw new RuntimeException("Access token is valid but member not found");
//                                            }
//                                    ),
//                            () -> {
//                                log.warn("Access token is invalid or not present");
//                                throw new RuntimeException("Access token is invalid or not present");
//                            }
//                    );
    }

    public void saveAuthentication(Member member) {
        UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
                .username(member.getEmail())
                .password("") // 소셜 로그인 사용자의 경우 비밀번호를 사용하지 않음
                .authorities(member.getRole().name())
                .build();

        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
