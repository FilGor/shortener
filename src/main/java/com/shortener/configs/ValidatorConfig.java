package com.shortener.configs;

import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class ValidatorConfig {

    private final String[] schemesArray;

    public ValidatorConfig(@Value("${url.schemes.allowed.for.shortening}") String schemesProperty) {
        this.schemesArray = Arrays.stream(schemesProperty.split(","))
                .map(String::trim)
                .toArray(String[]::new);
    }

    @Bean
    public UrlValidator urlValidator() {
        return new UrlValidator(schemesArray);
    }
}
