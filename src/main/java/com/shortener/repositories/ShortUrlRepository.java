package com.shortener.repositories;

import com.shortener.models.ShortUrl;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShortUrlRepository extends JpaRepository<ShortUrl, Long> {
    Optional<ShortUrl> findByShortCode(String shortCode);
    Optional<ShortUrl> findByOriginalHash(String originalHash);
}
