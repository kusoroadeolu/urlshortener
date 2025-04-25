package com.personal.urlshortener.dto;

import java.util.Date;

public record RawUrlDTO(String shortUrl, Integer count, String createdAt) {

}
