package com.shortener.exceptions;

public class UserNotFoundException extends UrlShortenerException {
    public UserNotFoundException(String email) {
        super("User not found: " + email);
    }
}