package com.personal.urlshortener.service;

import com.personal.urlshortener.models.Click;
import com.personal.urlshortener.models.Url;
import com.personal.urlshortener.projectionmodel.ClickStats;
import com.personal.urlshortener.repos.ClickRepository;
import com.personal.urlshortener.repos.UrlRepository;
import com.personal.urlshortener.utils.UrlUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClickService {

    private final UrlRepository urlRepository;
    private final UrlUtils urlUtils;
    private final ClickRepository clickRepository;

    /**
     * @param shortUrl The shortUrl you want to get the clicks for
     * Finds the number of times a shortUrl has been clicked/used
     * */
    public Long getClickCount(String shortUrl){
        Url url = urlUtils.verifyShortUrl(shortUrl);
        return clickRepository.countClicksByUrl(url);
    }

    /**
     * @param url the url that was clicked on
     * Saves the values of a click
     * */
    public void logClick(Url url){
        Click click = new Click(url);
        clickRepository.save(click);
    }

    public List<ClickStats> countClicksByUrlGroupByDay(String shortUrl){
        Url url = urlUtils.verifyShortUrl(shortUrl);
        return clickRepository.countClicksAndGroupByDate(url);
    }

    public LocalDateTime getLastClickTime(String shortUrl){
        Url url = urlUtils.verifyShortUrl(shortUrl);
        return clickRepository.findLastClickTime(url);
    }
}
