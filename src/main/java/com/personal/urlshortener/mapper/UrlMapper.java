package com.personal.urlshortener.mapper;

import com.personal.urlshortener.dto.UrlDTO;
import com.personal.urlshortener.models.Url;
import org.springframework.stereotype.Component;

@Component
public class UrlMapper {
    public UrlDTO toDTO(Url url, Long clickCount, String lastAccessed){
        return new UrlDTO(
                url.getUrl(),
                url.getShortenedUrl(),
                url.getFormattedDate(url.getCreatedAt()),
                url.getFormattedDate(url.getUpdatedAt()),
                lastAccessed,
                clickCount
                );
    }
}
