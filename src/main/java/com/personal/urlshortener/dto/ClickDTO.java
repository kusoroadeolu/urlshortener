package com.personal.urlshortener.dto;


public record ClickDTO(Long clickId, String shortUrl, String ipAddress, String osType, String browser,
                       String clickedAt) {
}
