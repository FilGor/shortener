package com.shortener.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "short_urls", indexes = {
        @Index(name = "idx_short_code", columnList = "shortCode", unique = true),
        @Index(name = "idx_original_hash", columnList = "originalHash")
})
public class ShortUrl {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 10)
    @Column(unique = true, length = 10)
    private String shortCode;

    @NotBlank
    @Size(max = 32)
    @Column(nullable = false, length = 32)
    private String originalHash;

    @NotBlank
    @Size(max = 2048)
    @Column(nullable = false, length = 2048)
    private String originalUrl;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private ShortenerUser owner;

    public ShortUrl() {
    }

    public ShortUrl(String shortCode, String originalHash, String originalUrl, ShortenerUser owner) {
        this.shortCode = shortCode;
        this.originalHash = originalHash;
        this.originalUrl = originalUrl;
        this.owner = owner;
    }

    public Long getId() {
        return id;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    public String getShortCode() {
        return shortCode;
    }

    public void setShortCode(String shortCode) {
        this.shortCode = shortCode;
    }

    public String getOriginalHash() {
        return originalHash;
    }

    public void setOriginalHash(String originalHash) {
        this.originalHash = originalHash;
    }

    public ShortenerUser getOwner() {
        return owner;
    }

    public void setOwner(ShortenerUser owner) {
        this.owner = owner;
    }
}
