package com.personal.urlshortener.service;

import com.personal.urlshortener.dto.ClickDTO;
import com.personal.urlshortener.dto.RawUrlDTO;
import com.personal.urlshortener.mapper.ClickMapper;
import com.personal.urlshortener.mapper.RawUrlMapper;
import com.personal.urlshortener.models.Click;
import com.personal.urlshortener.models.IpInfo;
import com.personal.urlshortener.models.Url;
import com.personal.urlshortener.projectionmodel.DailyClickStats;
import com.personal.urlshortener.projectionmodel.HourlyClickStats;
import com.personal.urlshortener.projectionmodel.RawUrlStats;
import com.personal.urlshortener.projectionmodel.ReferrerClickStats;
import com.personal.urlshortener.repos.ClickRepository;
import com.personal.urlshortener.repos.IpInfoRepository;
import com.personal.urlshortener.utils.UrlUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClickService {

    private final ClickRepository clickRepository;
    private final IpInfoRepository ipInfoRepository;
    private final IpInfoExtractor ipInfoExtractor;
    private final UrlValidator urlValidator;
    private final RawUrlMapper rawUrlMapper;
    private final UrlUtils urlUtils;
    private final ClickMapper clickMapper;

    /**
     * @param shortUrl The shortUrl you want to get the clicks for
     * Finds the number of times a shortUrl has been clicked/used
     * */
    public Long getClickCount(String shortUrl){
        Url url = urlValidator.verifyShortUrl(shortUrl);
        return clickRepository.countClicksByUrl(url);
    }

    /**
     * @param url the url that was clicked on
     * Saves the values of a click
     * */
    public void logClick(Url url, HttpServletRequest request){
        IpInfo ipInfo = ipInfoExtractor.extractIpInfo(request);
        Click click = new Click(url, ipInfo);
        ipInfoRepository.save(ipInfo);
        clickRepository.save(click);
    }

    /**
     * @param shortUrl The shortUrl to get the number of clicks for a particular day
     * */
    public List<DailyClickStats> countClicksByUrlGroupByDay(String shortUrl){
        Url url = urlValidator.verifyShortUrl(shortUrl);
        return clickRepository.countClicksAndGroupByDate(url);
    }

    /**
     * @param shortUrl The last time the shortUrl was accessed
     * */
    public LocalDateTime getLastClickTime(String shortUrl){
        Url url = urlValidator.verifyShortUrl(shortUrl);
        return clickRepository.findLastClickTime(url);
    }

    //Get all clicks
    public List<ClickDTO> findAllClicks(){
        List<Click> clicks = clickRepository.findAll();
        List<ClickDTO> clickDTOS = new ArrayList<>();

        for(Click click : clicks){
            clickDTOS.add(clickMapper.toDTO(click));
        }

        return clickDTOS;
    }

    /**
     * @param shortUrl The short url you want to get the clicks for
     * Get all clicks for a url
     * */
    public List<ClickDTO> findClicksByUrl(String shortUrl){
        Url url = urlValidator.verifyShortUrl(shortUrl);

        List<Click> clicks = clickRepository.findClicksByUrl(url);
        List<ClickDTO> clickDTOS = new ArrayList<>();

        for(Click click : clicks){
            clickDTOS.add(clickMapper.toDTO(click));
        }
        return clickDTOS;
    }


    /**
     * Find clicks by bots for all urls
     * */
    public List<ClickDTO> findClicksByBots(){
        List<Click> clicks = clickRepository.findAllBotClicks();
        List<ClickDTO> clickDTOS = new ArrayList<>();
        for(Click click : clicks){
            Url url = click.getUrl();
            //Build the shortened url from the shortcode
            url.setShortenedUrl(urlUtils.buildUrl(url.getShortCode()));
            clickDTOS.add(clickMapper.toDTO(click));
        }
        return clickDTOS;
    }

    /**
     * @param shortUrl The shortUrl
     * Find clicks by bots for a specific url
     * */

    public List<ClickDTO> findBotClicksByUrl(String shortUrl) {
        Url url = urlValidator.verifyShortUrl(shortUrl);
        List<Click> clicks = clickRepository.findBotClicksByUrl(url);
        return clicks.stream().map(clickMapper::toDTO).collect(Collectors.toList());
    }

    /**
     * Find clicks by humans
     * */
    public List<ClickDTO> findAllHumanClicks(){
        List<Click> clicks = clickRepository.findAllHumanClicks();
        return clicks.stream().map(clickMapper::toDTO).collect(Collectors.toList());
    }

    /**
     * @param shortUrl The short url
     * Find clicks by humans by url
     * */
    public List<ClickDTO> findHumanClicksByUrl(String shortUrl){
        Url url = urlValidator.verifyShortUrl(shortUrl);
        List<Click> clicks = clickRepository.findHumanClicksByUrl(url);
        return clicks.stream().map(clickMapper::toDTO).collect(Collectors.toList());
    }

    /**
     * @param country The country
     * Find clicks from a specific country
     * */
    public List<ClickDTO> findClicksByCountry(String country) {
        List<Click> clicks = clickRepository.findClicksByCountry(country);
        return clicks.stream().map(clickMapper::toDTO).collect(Collectors.toList());
    }

    /**
     * @param device The device type
     * Find clicks from a specific device type
     * */
    public List<ClickDTO> findClicksByDevice(String device) {
        List<Click> clicks = clickRepository.findClicksByDevice(device);
        return clicks.stream().map(clickMapper::toDTO).collect(Collectors.toList());
    }

    /**
     * @param browser The browser
     * Find clicks by browser
     * */
    public List<ClickDTO> findClicksByBrowser(String browser) {
        List<Click> clicks = clickRepository.findClicksByBrowser(browser);
        return clicks.stream().map(clickMapper::toDTO).collect(Collectors.toList());
    }

    /**
     * Find the top performing urls
     */
    public List<RawUrlDTO> findTopPerformingUrls(Integer limit) {
        Pageable pageable = PageRequest.of(0 ,limit);
        Page<RawUrlStats> rawUrlStats = clickRepository.findTopPerformingUrls(pageable);
        return rawUrlStats.stream().map(rawUrlMapper::toDTO)
                    .collect(Collectors.toCollection(ArrayList::new));

    }

    /**
     * @param targetDate The date you want to get clicks from
     * Get the clicks per hour by date
     * */
    public List<HourlyClickStats> countClicksPerHourByDate(String targetDate){
        LocalDate date = LocalDate.parse(targetDate);
        return clickRepository.countClicksPerHourByDate(date);
    }

    /**
     * Count the number of clicks from referrers
     * */
    public List<ReferrerClickStats> countClicksFromReferrers() {
        return clickRepository.countClicksFromReferrers();
    }

    /**
     * @param targetDate The targetDate
     * Count the number of clicks from referrers by date
     * */
    public List<ReferrerClickStats> countClicksFromReferrersByDate(String targetDate) {
        LocalDate localDate = LocalDate.parse(targetDate);
        return clickRepository.countClicksFromReferrersByDate(localDate);
    }
}
