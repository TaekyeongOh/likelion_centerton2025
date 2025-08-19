package com.example.likelion_ch.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // CSRF 보호 비활성화
                .authorizeHttpRequests(auth -> auth
                        // 회원가입, 로그인 경로에 대한 모든 요청 허용
                        .requestMatchers("/api/user/register/**", "/api/user/login", "/api/store/**", "/api/cart/**","/api/order-ratings/**","/orders/**").permitAll()
                        // 그 외의 모든 요청은 인증된 사용자만 허용
                        .anyRequest().authenticated()
                )
                // REST API이므로 폼 로그인과 기본 인증 비활성화
                .formLogin(form -> form.disable())
                .httpBasic(httpBasic -> httpBasic.disable());

        return http.build();
    }

    // BCryptPasswordEncoder Bean 등록
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}