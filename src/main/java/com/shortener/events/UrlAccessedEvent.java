package com.shortener.events;

import org.springframework.context.ApplicationEvent;

public class UrlAccessedEvent extends ApplicationEvent {

    private final String shortCode;
    public UrlAccessedEvent(Object source, String shortCode) {
        super(source);
        this.shortCode = shortCode;
    }
    public String getShortCode() { return shortCode; }
}