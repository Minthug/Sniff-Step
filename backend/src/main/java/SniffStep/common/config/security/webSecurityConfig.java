package SniffStep.common.config.security;

import SniffStep.common.config.oauth.CustomOAuthUserService;
import SniffStep.common.config.oauth.handler.OAuthLoginFailureHandler;
import SniffStep.common.config.oauth.handler.OAuthLoginSuccessHandler;
import SniffStep.common.jwt.filter.CustomJsonUsernamePasswordAuthenticationFilter;
import SniffStep.common.jwt.filter.JwtAuthenticationProcessingFilter;
import SniffStep.common.jwt.handler.LoginFailureHandler;
import SniffStep.common.jwt.handler.LoginSuccessHandler;
import SniffStep.common.jwt.service.JwtService;
import SniffStep.common.jwt.service.LoginService;
import SniffStep.repository.MemberRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.annotation.web.configurers.oauth2.client.OAuth2LoginConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class webSecurityConfig {

    private final JwtService jwtService;
    private final MemberRepository memberRepository;
    private final ObjectMapper objectMapper;
    private final LoginService loginService;
    private final OAuthLoginSuccessHandler oAuthLoginSuccessHandler;
    private final OAuthLoginFailureHandler oAuthLoginFailureHandler;
    private final CustomOAuthUserService customOAuthUserService;

    private static final String[] PUBLIC_URLS = {
            "/v1/auth/**", "/v1/upload/**", "/v1/boards","/oauth2/authorization/**", "/",
            "/css/**", "/images/**", "/js/**", "/h2-console/**", "/favicon.ico", "/error", "/v1/oauth/**"
    };

    private static final String[] USER_ADMIN_AUTHORITIES = {"USER", "ADMIN"};

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(configurationSource()))
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .formLogin(form -> form.disable())
                .httpBasic(basic -> basic.disable())
                .headers(headers -> headers.frameOptions().disable())
                .authorizeHttpRequests(auth -> configureAuthorizeHttpRequests(auth))
                .oauth2Login(oauth2 -> configureOauth2Login(oauth2))
                .addFilterAfter(customJsonUsernamePasswordAuthenticationFilter(), LogoutFilter.class)
                .addFilterBefore(jwtAuthenticationProcessingFilter(), CustomJsonUsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    private void configureAuthorizeHttpRequests(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry auth) {
        auth
                .requestMatchers(PUBLIC_URLS).permitAll()
                .requestMatchers(HttpMethod.GET, "/v1/members", "/v1/members/{id}", "/v1/boards/{id}", "/v1/boards/search/**").permitAll()
                .requestMatchers(HttpMethod.PATCH, "/v1/members/{id}").hasAnyAuthority(USER_ADMIN_AUTHORITIES);

        configureUserAdminRequests(auth);

        auth.anyRequest().authenticated();
    }

    private void configureUserAdminRequests(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry auth) {
        auth
                .requestMatchers(HttpMethod.GET, "/v1/members/profile").hasAnyAuthority(USER_ADMIN_AUTHORITIES)
                .requestMatchers(HttpMethod.DELETE, "/v1/members/**").hasAnyAuthority(USER_ADMIN_AUTHORITIES)
                .requestMatchers(HttpMethod.POST, "/v1/boards/**").hasAnyAuthority(USER_ADMIN_AUTHORITIES)
                .requestMatchers(HttpMethod.PATCH, "/v1/boards/**").hasAnyAuthority(USER_ADMIN_AUTHORITIES)
                .requestMatchers(HttpMethod.DELETE, "/v1/boards/**").hasAnyAuthority(USER_ADMIN_AUTHORITIES)
                .requestMatchers(HttpMethod.POST, "/v1/s3/**").hasAnyAuthority(USER_ADMIN_AUTHORITIES)
                .requestMatchers(HttpMethod.DELETE, "/v1/s3/resource").hasAnyAuthority(USER_ADMIN_AUTHORITIES);
    }

    // 선택적
    private void authorizeUserAdminRequests(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry auth,
                                  HttpMethod method, String patterns) {
        auth.requestMatchers(method, patterns).hasAnyAuthority(USER_ADMIN_AUTHORITIES);
    }

    private void configureOauth2Login(OAuth2LoginConfigurer<HttpSecurity> oauth2) {
        oauth2
                .loginPage("/v1/oauth/oauth2/authorization/{provider}")
                .successHandler(oAuthLoginSuccessHandler)
                .failureHandler(oAuthLoginFailureHandler)
                .userInfoEndpoint(userInfo -> userInfo.userService(customOAuthUserService));
    }

    @Bean
    public CorsConfigurationSource configurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("https://sniffstep.com"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(Collections.singletonList("*"));
        configuration.setExposedHeaders(Arrays.asList("Authorization", "RefreshToken"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(loginService);
        return new ProviderManager(provider);
    }

    @Bean
    public LoginFailureHandler loginFailureHandler() {
        return new LoginFailureHandler();
    }

    @Bean
    public LoginSuccessHandler loginSuccessHandler() {
        return new LoginSuccessHandler(jwtService, memberRepository);
    }

    @Bean
    public CustomJsonUsernamePasswordAuthenticationFilter customJsonUsernamePasswordAuthenticationFilter() {
        CustomJsonUsernamePasswordAuthenticationFilter customJsonUsernamePasswordLoginFilter =
                new CustomJsonUsernamePasswordAuthenticationFilter(objectMapper);
        customJsonUsernamePasswordLoginFilter.setAuthenticationManager(authenticationManager());
        customJsonUsernamePasswordLoginFilter.setAuthenticationSuccessHandler(loginSuccessHandler());
        customJsonUsernamePasswordLoginFilter.setAuthenticationFailureHandler(loginFailureHandler());
        return customJsonUsernamePasswordLoginFilter;
    }

    @Bean
    public JwtAuthenticationProcessingFilter jwtAuthenticationProcessingFilter() {
        JwtAuthenticationProcessingFilter jwtAuthenticationProcessingFilter = new JwtAuthenticationProcessingFilter(jwtService, memberRepository);
        return jwtAuthenticationProcessingFilter;
    }
}
