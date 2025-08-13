package com.shortener.controllers;

import com.shortener.service.UrlShortenerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class RedirectController {

    private final UrlShortenerService urlShortener;

    public RedirectController(UrlShortenerService urlShortener) {
        this.urlShortener = urlShortener;
    }


    @GetMapping("/{shortCode}")
    public ResponseEntity<Void> redirect(@PathVariable String shortCode) {
        String originalUrl = urlShortener.resolveOriginalUrl(shortCode);
        return ResponseEntity.status(HttpStatus.FOUND)
                .header("Location", originalUrl)
                .build();
    }
}
