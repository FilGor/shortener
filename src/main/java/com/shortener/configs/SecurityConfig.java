package com.shortener.configs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shortener.filters.JwtAuthenticationFilter;
import com.shortener.models.ApiError;
import com.shortener.security.JwtTokenResolver;
import com.shortener.security.TokenResolver;
import jakarta.servlet.DispatcherType;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.*;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
public class SecurityConfig {


    private final TokenResolver tokenResolver;
    private final ObjectMapper objectMapper;

    public SecurityConfig(TokenResolver tokenResolver, ObjectMapper objectMapper) {
        this.tokenResolver = tokenResolver;
        this.objectMapper = objectMapper;
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http,
                                           @Qualifier("corsConfigurationSource") CorsConfigurationSource corsSource) throws Exception {
        http.cors(cors -> cors.configurationSource(corsSource))
                .csrf(AbstractHttpConfigurer::disable) //TODO should be enabled if API is used by an frontend
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST, "/api/auth/signup", "/api/auth/signin").permitAll()
                                .requestMatchers(HttpMethod.GET,
                                        "/swagger-ui.html",
                                        "/swagger-ui/**",
                                        "/h2/**", // Would be nice to be available on 'dev' profile only
                                        "/v3/api-docs/**",
                                        "/v3/api-docs.yaml",
                                        "/error",
                                        "/error/**"
                                ).permitAll()
                        .dispatcherTypeMatchers(DispatcherType.ERROR).permitAll()

                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(ex -> ex.authenticationEntryPoint(restAuthEntryPoint())

        );
        return http.build();
    }


    @Bean
    public AuthenticationEntryPoint restAuthEntryPoint() {
        return (request, response, authException) -> {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);

            ApiError error = new ApiError(
                    HttpStatus.UNAUTHORIZED,
                    authException.getMessage()
            );

            objectMapper.writeValue(response.getWriter(), error);
        };
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter(tokenResolver,restAuthEntryPoint());
    }
}
