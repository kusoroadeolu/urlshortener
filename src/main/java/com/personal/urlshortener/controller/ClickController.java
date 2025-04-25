package com.personal.urlshortener.controller;

import com.personal.urlshortener.dto.ClickDTO;
import com.personal.urlshortener.dto.RawUrlDTO;
import com.personal.urlshortener.projectionmodel.DailyClickStats;
import com.personal.urlshortener.projectionmodel.HourlyClickStats;
import com.personal.urlshortener.projectionmodel.ReferrerClickStats;
import com.personal.urlshortener.service.ClickService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ClickController {

    private final ClickService clickService;

    //Get all clicks
    @GetMapping("api/click/all")
    public List<ClickDTO> findAllClicks(){
        return clickService.findAllClicks();

    }

    //Get all clicks for a specific url
    @GetMapping("api/click/by-url")
    public List<ClickDTO> findClicksByUrl(@RequestParam(value = "shortUrl", required = false)
                                          String shortUrl){
        return clickService.findClicksByUrl(shortUrl);
    }

    //Get all the clicks for a specific short url
    @GetMapping("api/click/count")
    public Long getClickCount(@RequestParam("shortUrl") String shortUrl){
          return clickService.getClickCount(shortUrl);
    }

    //Group all clicks by the day they were clicked
    @GetMapping("api/click/date-count")
    public List<DailyClickStats> countClicksByUrlGroupedByDay(@RequestParam("shortUrl") String shortUrl){
        return clickService.countClicksByUrlGroupByDay(shortUrl);
    }

    //Get all clicks by bots
    @GetMapping("api/click/bots")
    public List<ClickDTO> findAllBotClicks(){
        return clickService.findClicksByBots();
    }

    //Get bot clicks by url
    @GetMapping("api/click/bots/by-url")
    public List<ClickDTO> findBotClicksByUrl(@RequestParam("shortUrl") String shortUrl){
        return clickService.findBotClicksByUrl(shortUrl);
    }

    //Get all clicks by humans
    @GetMapping("api/click/humans")
    public List<ClickDTO> findAllHumansClicks(){
        return clickService.findAllHumanClicks();
    }

    //Get humans clicks by url
    @GetMapping("api/click/humans/by-url")
    public List<ClickDTO> findHumanClicksByUrl(@RequestParam("shortUrl") String shortUrl){
        return clickService.findHumanClicksByUrl(shortUrl);
    }

    //Get clicks by country
    @GetMapping("api/click/country")
    public List<ClickDTO> findClicksByCountry(@RequestParam("country") String country){
        return clickService.findClicksByCountry(country);
    }

    //Get clicks by device type
    //Get clicks by country
    @GetMapping("api/click/device")
    public List<ClickDTO> findClicksByDevice(@RequestParam("device") String device){
        return clickService.findClicksByDevice(device);
    }

    //Get clicks by browser
    //Get clicks by country
    @GetMapping("api/click/browser")
    public List<ClickDTO> findClicksByBrowser(@RequestParam("browser") String browser){
        return clickService.findClicksByBrowser(browser);
    }

    //Get top performing urls
    @GetMapping("api/click/top-performing")
    public List<RawUrlDTO> findTopPerformingUrls(@RequestParam("limit") Integer limit){
        return clickService.findTopPerformingUrls(limit);
    }

    //Get clicks per hour. Date format is yyyy-mm-dd
    @GetMapping("api/click/hour")
    public List<HourlyClickStats> getClickPerHourByDate(@RequestParam("targetDate") String targetDate){
        return clickService.countClicksPerHourByDate(targetDate);
    }

    //Get the count of clicks from referrers
    @GetMapping("api/click/referrer")
    public List<ReferrerClickStats> countClicksFromReferrers(){
        return clickService.countClicksFromReferrers();
    }

    //Get the count of clicks from referrers by date
    @GetMapping("api/click/referrer/by-date")
    public List<ReferrerClickStats> countClicksFromReferrersByDate(@Param("targetDate") String targetDate){
        return clickService.countClicksFromReferrersByDate(targetDate);
    }

 }
