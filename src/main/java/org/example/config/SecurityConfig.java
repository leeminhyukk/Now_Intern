package org.example.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestFilter;

@ComponentScan // jwtSecurityFilter 빈 등록 실패로 인해 추가.
@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true) // 권한 확인용
public class SecurityConfig {

    private final JwtSecurityFilter jwtSecurityFilter;

    // security 에 들어있는 passwordEncoder 사용
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable()) // CSRF 비활성화
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 세션 사용 안함
                )
                .addFilterBefore(jwtSecurityFilter, SecurityContextHolderAwareRequestFilter.class) // Jwt 필터 추가
                .formLogin(AbstractHttpConfigurer::disable) // 폼 로그인 비활성화
                .anonymous(AbstractHttpConfigurer::disable) // 익명 사용자 비활성화
                .httpBasic(AbstractHttpConfigurer::disable) // BasicAuthenticationFilter 비활성화
                .logout(AbstractHttpConfigurer::disable) // LogoutFilter 비활성화
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login", "/signup").permitAll() // 회원가입, 로그인 허용
                        .requestMatchers(
                                "/swagger-ui/**", // Swagger UI 경로
                                "/swagger-ui/", // Swagger UI 경로
                                "/v3/api-docs/**", // OpenAPI 문서 경로
                                "/api-docs/**" // OpenAPI 문서 경로
                        ).permitAll() // Swagger와 API 문서 접근 허용
                        .anyRequest().authenticated() // 그 외 모든 요청은 로그인 필요
                )
                .build();
    }
}
