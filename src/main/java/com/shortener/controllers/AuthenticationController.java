package com.shortener.controllers;


import com.shortener.models.JwtAuthenticationResponse;
import com.shortener.models.dto.SignInRequest;
import com.shortener.models.dto.SignUpRequest;
import com.shortener.security.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signup")
    public ResponseEntity<JwtAuthenticationResponse> signup(@RequestBody @Valid SignUpRequest signUpRequest){
        return new ResponseEntity<>(authenticationService.signup(signUpRequest), HttpStatus.OK);

    }
    @PostMapping(value = "/signin")
    public ResponseEntity<JwtAuthenticationResponse> signin(@RequestBody @Valid SignInRequest signInRequest){
        return new ResponseEntity<>(authenticationService.signin(signInRequest), HttpStatus.OK);
    }
}