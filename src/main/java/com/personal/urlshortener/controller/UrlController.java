package com.personal.urlshortener.controller;

import com.personal.urlshortener.dto.ShortUrlDTO;
import com.personal.urlshortener.dto.UrlDTO;
import com.personal.urlshortener.models.Url;
import com.personal.urlshortener.service.UrlService;
import com.personal.urlshortener.utils.UrlUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class UrlController {

    private final UrlService urlService;
    private final UrlUtils urlUtils;


    //Redirects the short url to the original url  
    @GetMapping("/{shortCode}")
    public void redirectToOriginalUrl(@PathVariable String shortCode, HttpServletResponse response,
                                                                                HttpServletRequest request)
                                                                                    throws IOException{
      urlService.handleRedirect(shortCode, response, request);
    }

    //Shorten your url
    @PostMapping("/api/shorten")
    //Note to format the shortcode into an actual url
    public ShortUrlDTO shortenUrl(@RequestBody String originalUrl){
        return urlService.shorten(originalUrl);
    }

    //Gets the original url from the shortened one
    @GetMapping("/api/original")
    public UrlDTO getOriginalUrl(@RequestParam("shortUrl") String shortUrl){
        return urlService.getOriginalUrl(shortUrl);
    }

    /**
     * @param url The initial url
     * */
    @DeleteMapping("/api/delete")
    //Deletes a shortcode from an existing url
    public void deleteUrlShortCode(@RequestBody String url){
        urlService.updateUrlShortCode(url, "");
    }

    /**
     * @param url The initial url
     * */
    @PutMapping("/api/update")
    public String updateUrlShortCode(@RequestBody String url){
        final String shortCode = urlUtils.generateShortCode();
        return urlService.updateUrlShortCode(url, shortCode);

    }


}
