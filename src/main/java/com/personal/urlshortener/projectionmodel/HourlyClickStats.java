package com.personal.urlshortener.projectionmodel;

import java.time.LocalDateTime;

public interface HourlyClickStats {
    Integer getHour();
    Integer getCount();
}
