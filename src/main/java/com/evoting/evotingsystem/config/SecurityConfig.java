package com.evoting.evotingsystem.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/api/register/**",
                                "/api/otp/**",
                                "/api/auth/**",
                                "/api/vote-token/**",
                                "/api/vote/**",
                                "/api/face/**"
                        ).permitAll()
                        .anyRequest().authenticated()
                );

        return http.build();
    }
}