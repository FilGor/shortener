package com.shortener.exceptions;

public class ValidationException extends UrlShortenerException {
    public ValidationException(String msg) {
        super(msg);
    }
}
