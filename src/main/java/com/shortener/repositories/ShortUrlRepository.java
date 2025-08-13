package com.shortener.repositories;

import com.shortener.models.ShortUrl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ShortUrlRepository extends JpaRepository<ShortUrl, Long> {
    Optional<ShortUrl> findByShortCode(String shortCode);
    Optional<ShortUrl> findByOriginalHash(String originalHash);
    Page<ShortUrl> findAllByOwnerEmail(String ownerEmail, Pageable pageable);
    @Modifying
    @Query(
            "UPDATE ShortUrl s SET s.visitCount = s.visitCount + 1 WHERE s.shortCode = :code"
    )
    void incrementVisitCount(@Param("code") String code);

}
