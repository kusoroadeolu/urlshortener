package com.personal.urlshortener.utils;

import com.personal.urlshortener.config.UrlProperties;
import com.personal.urlshortener.exception.InvalidUrlException;
import com.personal.urlshortener.models.Url;
import com.personal.urlshortener.repos.UrlRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.security.SecureRandom;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UrlUtils {
    private static final String POOL =  "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                                      + "abcdefghijklmnopqrstuvwxyz"
                                      + "0123456789";

    private final SecureRandom secureRandom;
    private final UrlProperties urlProperties;
    private final UrlRepository urlRepository;

    public String generateShortCode(){
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0; i < urlProperties.getDefaultLength(); i++){
            int index = secureRandom.nextInt(POOL.length());
            stringBuilder.append(POOL.charAt(index));
        }
        return stringBuilder.toString();
    }

    public String buildUrl(String shortCode){
        return urlProperties.getBaseDomain() + shortCode;
    }

    //Get the shortcode from the short url
    public String extractShortCode(String shortUrl){
        URI uri = URI.create(shortUrl);
        String path =  uri.getPath();
        return path.replaceFirst("/", "");
    }


    //Verifies is a short url exists in the db
    public Url verifyShortUrl(String shortUrl){
        String shortCode = extractShortCode(shortUrl);
        Optional<Url> optionalUrl = urlRepository.findUrlByShortCode(shortCode);
        optionalUrl.orElseThrow(() ->
                new InvalidUrlException(String.format("Short url: %s not found!", shortUrl))
        );
        return optionalUrl.get();
    }
}
