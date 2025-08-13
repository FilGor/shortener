package com.shortener.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserSecurityService extends UserDetailsService {
    UserDetails loadUserByUsername(String username);
}