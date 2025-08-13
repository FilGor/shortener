package com.shortener.service;


import com.shortener.events.UrlAccessedEvent;
import com.shortener.exceptions.UrlNotFoundException;
import com.shortener.exceptions.UrlValidationException;
import com.shortener.models.ShortUrl;
import com.shortener.models.ShortenerUser;
import com.shortener.models.builders.ShortUrlBuilder;
import com.shortener.repositories.ShortUrlRepository;
import com.shortener.utils.URLShortenerEncoder;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DefaultUrlShortenerService implements UrlShortenerService {

    private final UrlValidator urlValidator;
    private final URLShortenerEncoder urlShortenerEncoder;
    private final ShortUrlRepository shortUrlRepository;
    private final ApplicationEventPublisher publisher;

    public DefaultUrlShortenerService(UrlValidator urlValidator,
                                      URLShortenerEncoder urlShortenerEncoder,
                                      ShortUrlRepository shortUrlRepository,
                                      ApplicationEventPublisher publisher) {
        this.urlValidator = urlValidator;
        this.urlShortenerEncoder=urlShortenerEncoder;
        this.shortUrlRepository = shortUrlRepository;
        this.publisher = publisher;
    }


    @Transactional
    @Override
    public ShortUrl shorten(String originalUrl, ShortenerUser owner) {
        String originalUrlTrimmed = originalUrl.trim();
        if (!urlValidator.isValid(originalUrl)) {
            throw new UrlValidationException("Invalid URL: " + originalUrl);
        }

        String hash = DigestUtils.md5Hex(originalUrlTrimmed);

        return shortUrlRepository.findByOriginalHash(hash)
                .orElseGet(() -> createShortUrl(hash, originalUrlTrimmed, owner));
    }


    @Override
    public Page<ShortUrl> findAllByUrlsOwnerEmail(String ownerEmail, Pageable pageable) {
        return shortUrlRepository.findAllByOwnerEmail(ownerEmail,pageable);

    }

    private ShortUrl createShortUrl(String hash, String originalUrlTrimmed, ShortenerUser owner) { //TODO This could be moved to the factory
        ShortUrl shortUrl = new ShortUrlBuilder()
                .setShortCode(null)
                .setOriginalHash(hash)
                .setOwner(owner)
                .setOriginalUrl(originalUrlTrimmed).createShortUrl();
        ShortUrl entity = shortUrlRepository.save(shortUrl);
        entity.setShortCode(urlShortenerEncoder.encode(entity.getId()));
        return shortUrlRepository.save(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public String resolveOriginalUrl(String shortCode) {
        publisher.publishEvent(new UrlAccessedEvent(this, shortCode));
        return shortUrlRepository.findByShortCode(shortCode)
                .map(ShortUrl::getOriginalUrl)
                .orElseThrow(() -> new UrlNotFoundException(shortCode));
}
}