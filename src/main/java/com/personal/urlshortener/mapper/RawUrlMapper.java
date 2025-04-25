package com.personal.urlshortener.mapper;

import com.personal.urlshortener.dto.RawUrlDTO;
import com.personal.urlshortener.projectionmodel.RawUrlStats;
import com.personal.urlshortener.utils.UrlUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
@RequiredArgsConstructor
public class RawUrlMapper {

    private final UrlUtils urlUtils;

    public RawUrlDTO toDTO(RawUrlStats rawUrlStats){
        String shortUrl = urlUtils.buildUrl(rawUrlStats.getShortCode());
        String createdAt = getFormattedDate(rawUrlStats.getCreatedAt());

        return new RawUrlDTO(
                shortUrl,
                rawUrlStats.getClickCount(),
                createdAt
        );
    }

    public String getFormattedDate(LocalDateTime localDateTime){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-ss");
        return localDateTime.format(dateTimeFormatter);
    }
}
