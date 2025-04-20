package com.personal.urlshortener.controller;

import com.personal.urlshortener.dto.UrlDTO;
import com.personal.urlshortener.models.Url;
import com.personal.urlshortener.service.UrlService;
import com.personal.urlshortener.utils.UrlUtils;
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
    public void redirectToOriginalUrl(@PathVariable String shortCode, HttpServletResponse response)
                                                                                    throws IOException{
      urlService.handleRedirect(shortCode, response);
    }

    //Shorten your url
    @PostMapping("/shorten")
    //Note to format the shortcode into an actual url
    public String shortenUrl(@RequestBody String originalUrl){
        String shortCode = urlService.shorten(originalUrl);
        return urlUtils.buildUrl(shortCode);
    }

    //Gets the original url from the shortened one
    @GetMapping("/original")
    public UrlDTO getOriginalUrl(@RequestParam("shortenedUrl") String shortenedUrl){
        return urlService.getOriginalUrl(shortenedUrl);
    }

    /**
     * @param url The initial url
     * */
    @DeleteMapping("/delete")
    //Deletes a shortcode from an existing url
    public void deleteUrlShortCode(@RequestBody String url){
        urlService.updateUrlShortCode(url, "");
    }

    /**
     * @param url The initial url
     * */
    @PutMapping("/update")
    public String updateUrlShortCode(@RequestBody String url){
        final String shortCode = urlUtils.generateShortCode();
        return urlService.updateUrlShortCode(url, shortCode);

    }


}
