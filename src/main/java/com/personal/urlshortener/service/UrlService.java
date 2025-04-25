package com.personal.urlshortener.service;

import com.personal.urlshortener.dto.ShortUrlDTO;
import com.personal.urlshortener.dto.UrlDTO;
import com.personal.urlshortener.exception.InvalidUrlException;
import com.personal.urlshortener.exception.NoSuchUrlException;
import com.personal.urlshortener.exception.ShortCodeAlreadyExistsException;
import com.personal.urlshortener.mapper.ShortUrlMapper;
import com.personal.urlshortener.mapper.UrlMapper;
import com.personal.urlshortener.models.Url;
import com.personal.urlshortener.repos.UrlRepository;
import com.personal.urlshortener.utils.UrlUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class UrlService {
    private final UrlRepository urlRepository;
    private final ClickService clickService;
    private final UrlUtils urlUtils;
    private final UrlMapper urlMapper;
    private final UrlValidator urlValidator;


    //Shorten the given url
    public ShortUrlDTO shorten(String newUrl) {
        //Check if the url is a short url in our db
        if(urlValidator.shortUrlExists(newUrl))
            throw new ShortCodeAlreadyExistsException("This short url already exists!!");

        boolean saved = false;
        int currentTries = 0;
        int maxTries = 5;

        while(maxTries > currentTries){
            try{
                String shortCode = urlUtils.generateShortCode();
                String shortUrl = urlUtils.buildUrl(shortCode);
                Url url = new Url(newUrl, shortCode, shortUrl);

                urlRepository.save(url);
                saved = true;

                return new ShortUrlMapper().toDTO(
                        url,
                        shortUrl
                );
            }catch (ConstraintViolationException e){
                System.out.println("Shortcode already exists. Generating a new shortcode.... ");
                currentTries++;
            }
        }
        throw new ShortCodeAlreadyExistsException("Failed to generate a unique shortcode after: " + maxTries);
    }

    //Gets the original url data mapped to a short url
    public UrlDTO getOriginalUrl(String shortUrl){
        String shortCode = urlUtils.extractShortCode(shortUrl);
        Url url = urlValidator.verifyShortUrl(shortUrl);
        url.setShortenedUrl(urlUtils.buildUrl(url.getShortCode()));

        String lastAccessed = url.getFormattedDate(clickService.getLastClickTime(url.getShortenedUrl()));
        Long clickCount = clickService.getClickCount(url.getShortenedUrl());
        return urlMapper.toDTO(url, clickCount, lastAccessed);
    }


    @Transactional
    public String updateUrlShortCode(String url, String newShortCode){
        Optional<Url> optionalUrl = urlRepository.findShortCodeByUrl(url);
        Url u = optionalUrl.orElseThrow(() ->
                new NoSuchUrlException(String.format("Url: %s not found!", url))
        );

        u.setShortCode(newShortCode);
        u.setUpdatedAt(LocalDateTime.now());
        urlRepository.saveAndFlush(u);
        return urlUtils.buildUrl(newShortCode);
    }

    //Handles the whole redirect process for a short url
    public void handleRedirect(String shortCode, HttpServletResponse response, HttpServletRequest request){
            String shortUrl = urlUtils.buildUrl(shortCode);
            Url url =  urlValidator.verifyShortUrl(shortUrl);

            //Save the click values before the redirect
            clickService.logClick(url, request);

            redirectShortUrl(url.getUrl(), response);
    }


    //Method to redirect the short url
    private void redirectShortUrl(String destinationUrl, HttpServletResponse response){
        try{
            response.sendRedirect(destinationUrl);
        }catch (IOException e){
            handleRedirectError(response, e);
        }
        Logger.getLogger(UrlService.class.getName()).info("Successfully Redirected!");
    }


    //Handle redirect error in case we're unable to redirect to the original url
    private void handleRedirectError(HttpServletResponse response, IOException e) {
        try {
            response.sendRedirect("/error");
        } catch (IOException ex) {
            ex.printStackTrace(); // Optional: consider logging instead
        }
    }

}
