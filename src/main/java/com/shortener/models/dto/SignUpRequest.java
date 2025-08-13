package com.shortener.models.dto;

import jakarta.validation.constraints.Email;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SignUpRequest(
        @NotBlank @Size(max = 100) @Email(message = "must be a well-formed email address")
        String email,
        @NotBlank @Size(min = 8, max = 50)
        String password,
        @NotBlank @Size(max = 50)
        String name
) {}

