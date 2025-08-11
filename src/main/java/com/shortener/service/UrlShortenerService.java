package com.shortener.service;


import com.shortener.models.ShortUrl;

public interface UrlShortenerService {
    ShortUrl shorten(String originalUrl);
    String resolveOriginalUrl(String shortCode);
}
