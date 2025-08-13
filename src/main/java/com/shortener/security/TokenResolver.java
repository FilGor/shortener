package com.shortener.security;

import org.springframework.security.core.userdetails.UserDetails;

public interface TokenResolver {
    UserDetails resolve(String header);
}
