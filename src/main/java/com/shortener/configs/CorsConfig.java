package com.shortener.configs;

import org.springframework.context.annotation.*;
import org.springframework.web.cors.*;

import java.util.Arrays;
import java.util.List;

@Configuration
public class CorsConfig {

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        List<String> allowedOriginPatternsList =
                Arrays.asList("http://localhost:4200",
                        "http://localhost:4200/*",
                        "https://localhost:4200",
                        "https://localhost:4200/*");
        List<String> allowedMethodsList =
                Arrays.asList("GET","POST","PUT","DELETE","PATCH","OPTIONS");
        List<String> allowedheaderList =
                List.of("*");
        List<String> allowedOriginsList =
                Arrays.asList("http://localhost:4200","https://localhost:4200");

        configuration.setAllowedOriginPatterns(allowedOriginPatternsList);
        configuration.setAllowedOrigins(allowedOriginsList);
        configuration.setAllowedMethods(allowedMethodsList);
        configuration.setAllowedHeaders(allowedheaderList);
        configuration.setAllowCredentials(true);
        org.springframework.web.cors.UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
