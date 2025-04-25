package com.personal.urlshortener.projectionmodel;

import java.sql.Date;

public interface DailyClickStats {
     Date getDate();
    Long getCount();

}
