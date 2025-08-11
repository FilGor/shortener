package com.shortener.configs;

import com.shortener.filters.JwtAuthenticationFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<JwtAuthenticationFilter> jwtFilter(JwtAuthenticationFilter filter) {
        var bean = new FilterRegistrationBean<>(filter);
        bean.setOrder(1);
        bean.addUrlPatterns("/api/*");
        return bean;
    }
}