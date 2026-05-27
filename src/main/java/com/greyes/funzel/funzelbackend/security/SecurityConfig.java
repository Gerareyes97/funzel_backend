package com.greyes.funzel.funzelbackend.security;

import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.header.writers.ReferrerPolicyHeaderWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                // API stateless
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)

                // Forzar HTTPS
                .requiresChannel(channel ->
                        channel.anyRequest().requiresSecure()
                )

                // CORS (endurecer luego en prod)
                .cors(Customizer.withDefaults())

                // Headers de seguridad fuertes
                .headers(headers -> headers
                        .contentSecurityPolicy(csp -> csp
                                .policyDirectives(
                                        "default-src 'self'; " +
                                                "script-src 'self' https://*.redserfinsa.com https://www.google.com https://www.gstatic.com; " +
                                                "connect-src 'self' https://*.redserfinsa.com https://www.google.com https://www.gstatic.com; " +
                                                "frame-src 'self' https://*.redserfinsa.com https://www.google.com; " +
                                                "img-src 'self' data: https:; " +
                                                "style-src 'self' 'unsafe-inline'; " +
                                                "object-src 'none'; " +
                                                "base-uri 'self'; " +
                                                "form-action 'self' https://*.redserfinsa.com;"
                                )
                        )

                        // Anti clickjacking
                        .frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin)

                        // Referrer
                        .referrerPolicy(referrer ->
                                referrer.policy(
                                        ReferrerPolicyHeaderWriter.ReferrerPolicy.ORIGIN
                                )
                        )

                        // HSTS (solo HTTPS)
                        .httpStrictTransportSecurity(hsts -> hsts
                                .includeSubDomains(true)
                                .preload(true)
                                .maxAgeInSeconds(31536000)
                        )

                        .addHeaderWriter((request, response) -> {
                            response.setHeader("X-Content-Type-Options", "nosniff");
                            response.setHeader("Permissions-Policy", "geolocation=()");
                            response.setHeader("Feature-Policy", "geolocation 'none'");
                        })
                )

                // Endpoints públicos controlados
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/api/donaciones/**",
                                "/api/contactos/**",
                                "/api/voluntarios/**",
                                "/api/impacto/**",
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html"
                        ).permitAll()
                        .anyRequest().denyAll()
                );

        return http.build();
    }
}