package com.shortener.security;

import com.shortener.service.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenResolver implements TokenResolver {
    private final TokenService tokenService;
    private final UserSecurityService userSec;

    public JwtTokenResolver(TokenService tokenService, UserSecurityService userSec) {
        this.tokenService = tokenService;
        this.userSec = userSec;
    }

    @Override
    public UserDetails resolve(String header) {
        if (header == null || !header.startsWith("Bearer ")) return null;
        String token = header.substring(7);
        String name  = tokenService.extractUserName(token);
        if (name == null) return null;
        var userDetails = userSec.loadUserByUsername(name);
        return tokenService.isTokenValid(token, userDetails) ? userDetails : null;
    }
}
