package com.shortener.exceptions;

public class UrlNotFoundException extends UrlShortenerException {
    public UrlNotFoundException(String id) {
        super("Short URL not found: " + id);
    }
}
