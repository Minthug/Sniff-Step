package SniffStep.common.config.security;

import SniffStep.common.jwt.JwtAccessDeniedHandler;
import SniffStep.common.jwt.JwtAuthenticationEntryPoint;
import SniffStep.common.jwt.JwtSecurityConfig;
import SniffStep.common.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class webSecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;
    private final CorsFilter corsFilter;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, HandlerMappingIntrospector introspector) throws Exception {
        http.csrf().disable()

                .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)

                // exception handling 할 때 우리가 만든 클래스를 추가
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)

                .and()
                .headers()
                .frameOptions()
                .sameOrigin()

                // 시큐리티는 기본적으로 세션을 사용
                // 여기서는 세션을 사용하지 않기 때문에 세션 설정을 Stateless 로 설정
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                .authorizeHttpRequests()
                .requestMatchers("/v1/auth/**", "/v1/upload/**", "/v1/boards/find/", "/v1/boards/findAll").permitAll()
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
                .formLogin()
                .loginPage("/oauth-login/login")
                .loginProcessingUrl("/oauth-login/loginProc")
                .usernameParameter("loginId")
                .passwordParameter("password")
                .defaultSuccessUrl("/oauth-login")
                .failureUrl("/oauth-login")
                .permitAll()

                .and()
                .oauth2Login()
                .loginPage("/oauth-login/login")
                .defaultSuccessUrl("/oauth-login")
                .failureUrl("/oauth-login/login")
                .permitAll()

                .and()
                .logout()
                .logoutUrl("/oauth-login/logout")


                .and()
                .apply(new JwtSecurityConfig(jwtTokenProvider));

        return http.build();
    }
}
