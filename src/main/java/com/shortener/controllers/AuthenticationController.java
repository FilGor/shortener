package com.shortener.controllers;


import com.shortener.models.JwtAuthenticationResponse;
import com.shortener.models.SignInRequest;
import com.shortener.models.SignUpRequest;
import com.shortener.security.AuthenticationService;
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
    public ResponseEntity<JwtAuthenticationResponse> signup(@RequestBody SignUpRequest signUpRequest){
        return new ResponseEntity<>(authenticationService.signup(signUpRequest), HttpStatus.OK);

    }
    @PostMapping(value = "/signin")
    public ResponseEntity<JwtAuthenticationResponse> signin(@RequestBody SignInRequest signInRequest){
        return new ResponseEntity<>(authenticationService.signin(signInRequest), HttpStatus.OK);
    }
}