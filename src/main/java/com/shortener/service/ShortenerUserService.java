package com.shortener.service;


import com.shortener.models.ShortenerUser;
import com.shortener.repositories.UserRepository;
import org.apache.commons.lang3.NotImplementedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class ShortenerUserService implements UserCrudService, UserSecurityService {
    private static final Logger logger = LoggerFactory.getLogger(ShortenerUserService.class);


    private final UserRepository userRepository;

    public ShortenerUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public ShortenerUser saveUser(ShortenerUser user) {
        return userRepository.save(user);
    }

    @Override
    public ShortenerUser findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(email));
    }

    @Override
    public Page<ShortenerUser> getAllUsers(Pageable pageable) {
        throw new NotImplementedException();
    }

    @Override
        public UserDetails loadUserByUsername(String username) {
            return findByEmail(username);
        }
    }
