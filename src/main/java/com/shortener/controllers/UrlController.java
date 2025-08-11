package com.shortener.controllers;


import com.shortener.models.ShortUrl;
import com.shortener.models.ShortenRequest;
import com.shortener.service.UrlShortenerService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/urls")
public class UrlController {

    private final UrlShortenerService urlShortener;

    public UrlController(UrlShortenerService urlShortener) {
        this.urlShortener = urlShortener;
    }

    @PostMapping("/shorten")
    public ResponseEntity<ShortUrl> shortenUrl(@Valid @RequestBody ShortenRequest request) {
        ShortUrl shortUrl = urlShortener.shorten(request.getOriginalUrl());
        return new ResponseEntity<>(shortUrl, HttpStatus.CREATED);
    }

    @GetMapping("/{shortCode}")
    public ResponseEntity<Void> redirect(@PathVariable String shortCode) {
        String originalUrl = urlShortener.resolveOriginalUrl(shortCode);
        return ResponseEntity.status(HttpStatus.FOUND)
                .header("Location", originalUrl)
                .build();
    }

}
