package com.personal.urlshortener.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties("app.base")
public class UrlProperties{
    private String baseDomain;
    private Integer defaultLength;
}

