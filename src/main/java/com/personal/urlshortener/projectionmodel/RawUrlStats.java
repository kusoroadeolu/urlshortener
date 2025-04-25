package com.personal.urlshortener.projectionmodel;

import java.time.LocalDateTime;

public interface RawUrlStats {
    String getShortCode();
    Integer getClickCount();
    LocalDateTime getCreatedAt();

}
