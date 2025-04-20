package com.personal.urlshortener.projectionmodel;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.time.LocalDateTime;

public interface ClickStats {
     Date getDate();
    Long getCount();
}
