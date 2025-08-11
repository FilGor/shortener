package com.shortener.service;


import com.shortener.exceptions.UrlNotFoundException;
import com.shortener.models.ShortUrl;
import com.shortener.models.builders.ShortUrlBuilder;
import com.shortener.repositories.ShortUrlRepository;
import com.shortener.utils.URLShortenerEncoder;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DefaultUrlShortenerService implements UrlShortenerService {

    private final UrlValidator urlValidator;
    private final URLShortenerEncoder urlShortenerEncoder;
    private final ShortUrlRepository shortUrlRepository;

    public DefaultUrlShortenerService(UrlValidator urlValidator,
                                      URLShortenerEncoder urlShortenerEncoder,
                                      ShortUrlRepository shortUrlRepository) {
        this.urlValidator = urlValidator;
        this.urlShortenerEncoder=urlShortenerEncoder;
        this.shortUrlRepository = shortUrlRepository;
    }


    @Transactional
    @Override
    public ShortUrl shorten(String originalUrl) {
        String originalUrlTrimmed = originalUrl.trim();
        if (!urlValidator.isValid(originalUrl)) {
            throw new IllegalArgumentException("Invalid URL: " + originalUrl); //TODO custom exceptions
        }

        String hash = DigestUtils.md5Hex(originalUrlTrimmed);

        return shortUrlRepository.findByOriginalHash(hash)
                .orElseGet(() -> {
                    ShortUrl shortUrl = new ShortUrlBuilder()
                            .setShortCode(null)
                            .setOriginalHash(hash)
                            .setOriginalUrl(originalUrlTrimmed).createShortUrl();
                    ShortUrl entity = shortUrlRepository.save(shortUrl);
                    entity.setShortCode(urlShortenerEncoder.encode(entity.getId()));
                    return shortUrlRepository.save(entity);
                });
    }

    @Override
    @Transactional(readOnly = true)
    public String resolveOriginalUrl(String shortCode) {
        return shortUrlRepository.findByShortCode(shortCode)
                .map(ShortUrl::getOriginalUrl)
                .orElseThrow(() -> new UrlNotFoundException(shortCode));
}
}