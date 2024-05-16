package com.spring.security.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.spring.security.services.JWTUtilityService;

import jakarta.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {

    @Autowired
    private JWTUtilityService service;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                // Configuration
                .csrf(csrf -> {
                    csrf.disable(); // Protection disable
                })
                .authorizeHttpRequests(authRequest -> {
                    // Public and private routes
                    authRequest
                            // Public routes. Allowed routes with "/auth/"
                            .requestMatchers("/auth/**").permitAll()
                            // Private routes. Need authenticated
                            .anyRequest().authenticated();
                })
                .sessionManagement(sessionManager -> {
                    sessionManager
                            .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                })
                .addFilterBefore(
                        new JWTAuthorizationFilter(service),
                        UsernamePasswordAuthenticationFilter.class)
                // Customization requests
                // Transform to request: 401 (Unauthorized)
                .exceptionHandling(exceptionHandling -> {
                    exceptionHandling
                            .authenticationEntryPoint((request, response, authException) -> {
                                response
                                        .sendError(HttpServletResponse.SC_UNAUTHORIZED,
                                                "Unauthorized / No autorizado");
                            });
                })
                .build();
    }

    /**
     * Necessary to run the app
     * 
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
