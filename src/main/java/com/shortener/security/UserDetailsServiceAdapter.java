package com.shortener.security;

import com.shortener.service.UserCrudService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsServiceAdapter {
    private final UserCrudService userCrudService;

    public UserDetailsServiceAdapter(UserCrudService userCrudService) {
        this.userCrudService = userCrudService;
    }

    public UserDetails loadByUsername(String username) {
        return userCrudService.findByEmail(username);
    }
}