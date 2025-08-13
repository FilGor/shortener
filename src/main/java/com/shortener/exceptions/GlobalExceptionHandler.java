package com.shortener.exceptions;

import com.shortener.models.ApiError;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import org.springframework.security.core.AuthenticationException;

//TODO if we don't need custom body then instead of that switch case, or even whole handler we can use e.g @ResponseStatus(HttpStatus.NOT_FOUND)
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidationException(MethodArgumentNotValidException ex) {
        var fieldErrors = ex.getBindingResult().getFieldErrors().stream()
                .map(fe -> fe.getField() + ": " + fe.getDefaultMessage())
                .toList();

        String message = "Validation failed for %d field(s): %s".formatted(
                fieldErrors.size(),
                fieldErrors
        );

        logger.warn(message);
        ApiError error = new ApiError(HttpStatus.BAD_REQUEST, message);
        return ResponseEntity.badRequest().body(error);
    }


    @ExceptionHandler({AuthenticationException.class})
    public ResponseEntity<ApiError> handleAuthException(AuthenticationException ex) {
        logger.error("AuthenticationException: {}", ex.getMessage(), ex);
        ApiError error = new ApiError(HttpStatus.UNAUTHORIZED, ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }
    @ExceptionHandler(UrlShortenerException.class)
    public ResponseEntity<ApiError> handleAll(UrlShortenerException ex) {
        logger.error("UrlShortenerException: {}", ex.getMessage(), ex);
        HttpStatus status = switch (ex) {
            case UrlNotFoundException e     -> HttpStatus.NOT_FOUND;
            case UrlValidationException e      -> HttpStatus.BAD_REQUEST;
            default                        -> HttpStatus.INTERNAL_SERVER_ERROR;
        };
        ApiError error = new ApiError(status, ex.getMessage());
        return new ResponseEntity<>(error, status);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleAll(Exception ex,
                                              HttpServletRequest request) {
        logger.error("Unexpected error at {}: {}", request.getRequestURI(), ex.getMessage(), ex);
        ApiError body = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR,
                "Unexpected error");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }

}
