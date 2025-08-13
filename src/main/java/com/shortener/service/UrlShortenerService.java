package com.shortener.service;


import com.shortener.models.ShortUrl;
import com.shortener.models.ShortenerUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface UrlShortenerService {
    ShortUrl shorten(String originalUrl, ShortenerUser owner);
    Page<ShortUrl> findAllByUrlsOwnerEmail(String email, Pageable pageable);
    String resolveOriginalUrl(String shortCode);
}
