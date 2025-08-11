package com.shortener.models;

import org.springframework.http.HttpStatus;
import java.time.LocalDateTime;

public record ApiError(
        HttpStatus status,
        String message,
        LocalDateTime timestamp
) {
    public ApiError(HttpStatus status, String message) {
        this(status, message, LocalDateTime.now());
    }
}
