package com.shortener.models;

public class ShortUrlDto {

    private String shortenedUrl;

    public ShortUrlDto() {
        // needed by ModelMapper
    }

    public ShortUrlDto(String shortenedUrl) {
        this.shortenedUrl = shortenedUrl;
    }

    public String getShortenedUrl() {
        return shortenedUrl;
    }

    public void setShortenedUrl(String shortenedUrl) {
        this.shortenedUrl = shortenedUrl;
    }
}