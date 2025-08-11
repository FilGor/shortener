package com.shortener.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface UserSecurityService {
    UserDetails loadUserByUsername(String username);
}