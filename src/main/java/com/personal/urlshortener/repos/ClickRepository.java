package com.personal.urlshortener.repos;

import com.personal.urlshortener.models.Click;
import com.personal.urlshortener.models.Url;
import com.personal.urlshortener.projectionmodel.ClickStats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ClickRepository extends JpaRepository<Click, Long> {
        List<Click> findClicksByUrl(Url url);

        //Find the number of times an url has been clicked
        @Query("SELECT Count(c) " +
                "FROM Click c " +
                "WHERE c.url = :url")
        Long countClicksByUrl(@Param("url") Url url);

        //Find the last time an url was clicked
        @Query("SELECT MAX(c.clickedAt) " +
                "FROM Click c " +
                "WHERE c.url = :url")
        LocalDateTime findLastClickTime(@Param("url") Url url);

        @Query("SELECT DATE(c.clickedAt), Count(c) " +
                "FROM Click c " +
                "WHERE c.url = :url " +
                "GROUP BY DATE(c.clickedAt)")
        List<ClickStats> countClicksAndGroupByDate(@Param("url") Url url);
}
