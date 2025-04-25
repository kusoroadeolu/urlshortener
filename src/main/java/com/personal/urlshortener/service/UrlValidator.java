package com.personal.urlshortener.service;

import com.personal.urlshortener.exception.InvalidUrlException;
import com.personal.urlshortener.models.Url;
import com.personal.urlshortener.repos.UrlRepository;
import com.personal.urlshortener.utils.UrlUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UrlValidator {
    private final UrlRepository urlRepository;
    private final UrlUtils urlUtils;

    //Verifies is a short url exists in the db
    public Url verifyShortUrl(String shortUrl){
        String shortCode = urlUtils.extractShortCode(shortUrl);
        Optional<Url> optionalUrl = urlRepository.findUrlByShortCode(shortCode);
        return optionalUrl.orElseThrow(() ->
                new InvalidUrlException(String.format("Short url: %s not found!", shortUrl))
        );
    }

    //Returns true or false if a short url exists in the db
    public boolean shortUrlExists(String shortUrl){
        String shortCode = urlUtils.extractShortCode(shortUrl);
        Optional<Url> optionalUrl = urlRepository.findUrlByShortCode(shortCode);
        return optionalUrl.isPresent();
    }
}
