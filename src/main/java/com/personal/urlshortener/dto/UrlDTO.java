package com.personal.urlshortener.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UrlDTO {
    private String originalUrl;
    private String shortUrl;
    private String createdAt;
    private String updatedAt;
    private String lastAccessed;
    private Long numberOfClicks;

    public UrlDTO(String originalUrl, String shortUrl,
                     String createdAt, String updatedAt,
                        String lastAccessed, Long numberOfClicks) {
        this.originalUrl = originalUrl;
        this.shortUrl = shortUrl;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.lastAccessed = lastAccessed;
        this.numberOfClicks = numberOfClicks;
    }
}
