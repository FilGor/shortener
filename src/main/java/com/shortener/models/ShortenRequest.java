package com.shortener.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ShortenRequest {


    @NotBlank(message = "Original URL must not be blank")
    @Size(max = 2048)
    private String originalUrl;

    public ShortenRequest() {
    }

    public ShortenRequest(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }
}
