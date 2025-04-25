package com.personal.urlshortener.mapper;

import com.personal.urlshortener.dto.ClickDTO;
import com.personal.urlshortener.models.Click;
import com.personal.urlshortener.utils.UrlUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ClickMapper {

    private final UrlUtils urlUtils;

    public ClickDTO toDTO(Click click){
        String shortUrl = urlUtils.buildUrl(click.getUrl().getShortCode());
        click.getUrl().setShortenedUrl(shortUrl);

        return new ClickDTO(
                click.getClickId(),
                click.getUrl().getShortenedUrl(),
                click.getIpInfo().getIpAddress(),
                click.getIpInfo().getDeviceType(),
                click.getIpInfo().getBrowser(),
                click.getFormattedDate(click.getClickedAt())
        );
    }
}
