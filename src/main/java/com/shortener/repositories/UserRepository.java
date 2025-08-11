package com.shortener.repositories;

import com.shortener.models.ShortenerUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository  extends JpaRepository<ShortenerUser, Long> {
    Optional<ShortenerUser> findByEmail(String email);
}