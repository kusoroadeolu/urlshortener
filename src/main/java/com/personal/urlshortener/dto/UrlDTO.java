package com.personal.urlshortener.dto;


public record UrlDTO(String originalUrl, String shortUrl, String createdAt, String updatedAt, String lastAccessed,
                     Long numberOfClicks) {

}


