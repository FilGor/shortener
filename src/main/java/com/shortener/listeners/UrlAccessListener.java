package com.shortener.listeners;

import com.shortener.events.UrlAccessedEvent;
import com.shortener.repositories.ShortUrlRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class UrlAccessListener implements ApplicationListener<UrlAccessedEvent> {
    private final ShortUrlRepository shortUrlRepository;

    public UrlAccessListener(ShortUrlRepository shortUrlRepository) {
        this.shortUrlRepository = shortUrlRepository;
    }

    @Override
    public void onApplicationEvent(UrlAccessedEvent event) {
        shortUrlRepository.incrementVisitCount(event.getShortCode());
    }
}