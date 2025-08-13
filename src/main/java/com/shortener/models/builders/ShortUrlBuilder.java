package com.shortener.models.builders;

import com.shortener.models.ShortUrl;
import com.shortener.models.ShortenerUser;

public class ShortUrlBuilder {
    private String shortCode;
    private String originalHash;
    private String originalUrl;
    private ShortenerUser shortenerUser;

    public ShortUrlBuilder setShortCode(String shortCode) {
        this.shortCode = shortCode;
        return this;
    }

    public ShortUrlBuilder setOriginalHash(String originalHash) {
        this.originalHash = originalHash;
        return this;
    }

    public ShortUrlBuilder setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
        return this;
    }
    public ShortUrlBuilder setOwner(ShortenerUser shortenerUser){
        this.shortenerUser=shortenerUser;
        return this;
    }

    public ShortUrl createShortUrl() {
        return new ShortUrl(shortCode, originalHash, originalUrl,shortenerUser);
    }
}