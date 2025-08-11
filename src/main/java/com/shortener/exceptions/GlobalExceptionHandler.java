package com.shortener.exceptions;

import com.shortener.models.ApiError;
import org.springframework.web.bind.annotation.ControllerAdvice;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@ControllerAdvice
public class GlobalExceptionHandler {

    //TODO if we don't need custom body then instead of that switch case, or even whole handler we can use e.g @ResponseStatus(HttpStatus.NOT_FOUND)

    @ExceptionHandler(UrlShortenerException.class)
    public ResponseEntity<ApiError> handleAll(UrlShortenerException ex) {
        HttpStatus status = switch (ex) {
            case UrlNotFoundException e     -> HttpStatus.NOT_FOUND;
            case ValidationException e      -> HttpStatus.BAD_REQUEST;
            default                        -> HttpStatus.INTERNAL_SERVER_ERROR;
        };
        ApiError error = new ApiError(status, ex.getMessage());
        return new ResponseEntity<>(error, status);
    }
}
