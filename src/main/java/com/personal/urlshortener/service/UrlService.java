package com.personal.urlshortener.service;

import com.personal.urlshortener.dto.UrlDTO;
import com.personal.urlshortener.exception.NoSuchUrlException;
import com.personal.urlshortener.mapper.UrlMapper;
import com.personal.urlshortener.models.Url;
import com.personal.urlshortener.repos.UrlRepository;
import com.personal.urlshortener.utils.UrlUtils;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UrlService {
    private final UrlRepository urlRepository;
    private final ClickService clickService;
    private final UrlUtils urlUtils;
    private final UrlMapper urlMapper;

    public String shorten(String newUrl) {
            String shortCode = urlUtils.generateShortCode();
            String shortenedUrl = urlUtils.buildUrl(shortCode);
            Url url = new Url(newUrl, shortCode, shortenedUrl);
            //DONT FORGET TO ADD ERROR HANDLING FOR URLS THAT ALREADY EXIST
            urlRepository.save(url);
            return url.getShortCode();
    }

    //Gets the original url data mapped to a short url
    public UrlDTO getOriginalUrl(String shortUrl){
        String shortCode = urlUtils.extractShortCode(shortUrl);
        Url url = urlUtils.verifyShortUrl(shortUrl);
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

    //Redirect the shortened url to the original url path
    public void handleRedirect(String shortCode, HttpServletResponse response){
            String shortUrl = urlUtils.buildUrl(shortCode);
            Url url = urlUtils.verifyShortUrl(shortUrl);
            redirectShortUrl(url.getUrl(), response);

            //Save the click values after a redirect
            clickService.logClick(url);
    }

    private void redirectShortUrl(String destinationUrl, HttpServletResponse response){
        try{
            response.sendRedirect(destinationUrl);
        }catch (IOException e){
            handleRedirectError(response, e);
        }
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
