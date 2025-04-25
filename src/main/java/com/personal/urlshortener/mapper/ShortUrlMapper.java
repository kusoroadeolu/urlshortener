package com.personal.urlshortener.mapper;

import com.personal.urlshortener.dto.ShortUrlDTO;
import com.personal.urlshortener.models.Url;
import com.personal.urlshortener.utils.UrlUtils;
import org.springframework.stereotype.Component;

@Component
public class ShortUrlMapper {

    public ShortUrlDTO toDTO(Url url, String shortUrl){

        return new ShortUrlDTO(
                shortUrl,
                url.getShortCode()
        );
    }
}
