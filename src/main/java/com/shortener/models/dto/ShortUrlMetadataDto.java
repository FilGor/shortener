package com.shortener.models.dto;


import java.time.Instant;

public class ShortUrlMetadataDto {

    private String originalUrl;
    private String shortCode;
    private long visitCount;
    private String shortenedUrl;
    private Instant createdDate;
    private Instant lastModifiedDate;
    private String createdBy;
    private String lastModifiedBy;

    public ShortUrlMetadataDto() {}

    public ShortUrlMetadataDto(String originalUrl, String shortCode, long visitCount, String shortenedUrl, Instant createdDate, Instant lastModifiedDate, String createdBy, String lastModifiedBy) {
        this.originalUrl = originalUrl;
        this.shortCode = shortCode;
        this.visitCount = visitCount;
        this.shortenedUrl = shortenedUrl;
        this.createdDate = createdDate;
        this.lastModifiedDate = lastModifiedDate;
        this.createdBy = createdBy;
        this.lastModifiedBy = lastModifiedBy;
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

    public long getVisitCount() {
        return visitCount;
    }

    public void setVisitCount(long visitCount) {
        this.visitCount = visitCount;
    }

    public String getShortenedUrl() {
        return shortenedUrl;
    }

    public void setShortenedUrl(String shortenedUrl) {
        this.shortenedUrl = shortenedUrl;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Instant getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }
}
