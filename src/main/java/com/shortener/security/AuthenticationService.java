package com.shortener.security;

import com.shortener.models.*;
import com.shortener.models.dto.SignInRequest;
import com.shortener.models.dto.SignUpRequest;
import com.shortener.repositories.UserRepository;
import com.shortener.service.UserCrudService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;
    private final UserCrudService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(UserRepository userRepository,
                                 UserCrudService userService,
                                 PasswordEncoder passwordEncoder,
                                 JwtService jwtService,
                                 AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }


    public JwtAuthenticationResponse signup(SignUpRequest request){
        ShortenerUser shortenerUser = new ShortenerUser(
                request.name(),
                request.email(),
                passwordEncoder.encode(request.password()),
                ShortenerUserRole.ROLE_USER
        );
        userService.saveUser(shortenerUser);
        String token = jwtService.generateToken(shortenerUser);
        return new JwtAuthenticationResponse(token);
    }
    public JwtAuthenticationResponse signin(SignInRequest request){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(),request.password()));
        ShortenerUser shortenerUser = userRepository.findByEmail(request.email()).orElseThrow(IllegalAccessError::new);
        String token = jwtService.generateToken(shortenerUser);
        return new JwtAuthenticationResponse(token);

    }
}