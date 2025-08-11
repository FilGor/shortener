package com.shortener.exceptions;

public class UrlShortenerException extends RuntimeException {
    public UrlShortenerException(String message) {
        super(message);
    }
}
