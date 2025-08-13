package com.shortener.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class ShortUrlBuilderService implements UrlBuilderService {
    private final String baseUrl;

    public ShortUrlBuilderService(@Value("${application.base-url}") String baseUrl) {
        this.baseUrl = baseUrl;
    }

    @Override
    public String buildFullUrl(String code) {
        return UriComponentsBuilder
                .fromUriString(baseUrl)
                .path("/")
                .path(code)
                .toUriString();
    }
}