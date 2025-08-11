package com.shortener.security;

import com.shortener.service.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenResolver {
    private final JwtService jwtService;
    private final UserSecurityService userSec;

    public JwtTokenResolver(JwtService jwtService, UserSecurityService userSec) {
        this.jwtService = jwtService;
        this.userSec = userSec;
    }

    public UserDetails resolve(String header) {
        if (header == null || !header.startsWith("Bearer ")) return null;
        String token = header.substring(7);
        String name  = jwtService.extractUserName(token);
        if (name == null) return null;
        var userDetails = userSec.loadUserByUsername(name);
        return jwtService.isTokenValid(token, userDetails) ? userDetails : null;
    }
}
