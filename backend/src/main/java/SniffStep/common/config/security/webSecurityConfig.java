package SniffStep.common.config.security;

import SniffStep.common.config.oauth.CustomOAuth2UserService;
import SniffStep.common.config.oauth.handler.OAuth2LoginFailureHandler;
import SniffStep.common.config.oauth.handler.OAuth2LoginSuccessHandler;
import SniffStep.common.jwt.JwtAccessDeniedHandler;
import SniffStep.common.jwt.JwtAuthenticationEntryPoint;
import SniffStep.common.jwt.JwtSecurityConfig;
import SniffStep.common.jwt.JwtTokenProvider;
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
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class webSecurityConfig {


    private final JwtService jwtService;
    private final MemberRepository memberRepository;
    private final ObjectMapper objectMapper;
    private final LoginService loginService;
    private final OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;
    private final OAuth2LoginFailureHandler oAuth2LoginFailureHandler;
    private final CustomOAuth2UserService customOAuth2UserService;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, HandlerMappingIntrospector introspector) throws Exception {
        http.csrf().disable()

                .authorizeHttpRequests()
                .requestMatchers("/v1/auth/**", "/v1/upload/**", "/v1/boards/find/", "/v1/boards/findAll","/v1/oauth2/code/google", "/", "/css/**", "/images/**", "/js/**", "/h2-console/**", "/favicon.ico", "/error").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/v1/members").hasAnyAuthority("USER", "ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/v1/members/{id}").hasAnyAuthority("USER", "ADMIN")
                .requestMatchers(HttpMethod.PATCH, "/api/v1/members/**").hasAnyAuthority("USER", "ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/v1/members/**").hasAnyAuthority("USER", "ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/v1/boards/create").hasAnyAuthority("USER", "ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/v1/boards/{id}").hasAnyAuthority("USER", "ADMIN")
                .requestMatchers(HttpMethod.PATCH, "/api/v1/boards/**").hasAnyAuthority("USER", "ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/v1/boards/**").hasAnyAuthority("USER", "ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/v1/s3/").hasAnyAuthority("USER", "ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/v1/s3/resource").hasAnyAuthority("USER", "ADMIN")
                .anyRequest().authenticated()


                .and()
                .formLogin().disable()
                .httpBasic().disable()
                .headers().frameOptions().disable()
                .and()

                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()

                .oauth2Login()
                .successHandler(oAuth2LoginSuccessHandler)
                .failureHandler(oAuth2LoginFailureHandler)
                .userInfoEndpoint().userService(customOAuth2UserService);

        http.addFilterAfter(customJsonUsernamePasswordAuthenticationFilter(), LogoutFilter.class);
        http.addFilterBefore(jwtAuthenticationProcessingFilter(), CustomJsonUsernamePasswordAuthenticationFilter.class);

        return http.build();
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
        CustomJsonUsernamePasswordAuthenticationFilter customJsonUsernamePasswordAuthenticationFilter = new CustomJsonUsernamePasswordAuthenticationFilter(objectMapper);
        customJsonUsernamePasswordAuthenticationFilter().setAuthenticationManager(authenticationManager());
        customJsonUsernamePasswordAuthenticationFilter().setAuthenticationSuccessHandler(loginSuccessHandler());
        customJsonUsernamePasswordAuthenticationFilter().setAuthenticationFailureHandler(loginFailureHandler());
        return customJsonUsernamePasswordAuthenticationFilter;
    }

    @Bean
    public JwtAuthenticationProcessingFilter jwtAuthenticationProcessingFilter() {
        JwtAuthenticationProcessingFilter jwtAuthenticationProcessingFilter = new JwtAuthenticationProcessingFilter(jwtService, memberRepository);
        return jwtAuthenticationProcessingFilter;
    }
}
