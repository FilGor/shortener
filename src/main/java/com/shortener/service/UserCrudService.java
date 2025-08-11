package com.shortener.service;

import com.shortener.models.ShortenerUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserCrudService {
    ShortenerUser saveUser(ShortenerUser user);
    ShortenerUser findByEmail(String email);
    Page<ShortenerUser> getAllUsers(Pageable pageable);
}