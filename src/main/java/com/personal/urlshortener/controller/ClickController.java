package com.personal.urlshortener.controller;

import com.personal.urlshortener.projectionmodel.ClickStats;
import com.personal.urlshortener.service.ClickService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ClickController {

    private final ClickService clickService;

    @GetMapping("/click/count")
    public Long getClickCount(@RequestParam("shortUrl") String shortUrl){
          return clickService.getClickCount(shortUrl);
    }

    @GetMapping("/click/date-count")
    public List<ClickStats> countClicksByUrlGroupedByDay(@RequestParam("shortUrl") String shortUrl){
        return clickService.countClicksByUrlGroupByDay(shortUrl);
    }
 }
