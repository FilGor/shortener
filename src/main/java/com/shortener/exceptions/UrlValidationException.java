package com.shortener.exceptions;

public class UrlValidationException extends UrlShortenerException {
    public UrlValidationException(String msg) {
        super(msg);
    }
}
