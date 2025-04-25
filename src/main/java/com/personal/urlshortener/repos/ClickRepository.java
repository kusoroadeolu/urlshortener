package com.personal.urlshortener.repos;

import com.personal.urlshortener.models.Click;
import com.personal.urlshortener.models.Url;
import com.personal.urlshortener.projectionmodel.DailyClickStats;
import com.personal.urlshortener.projectionmodel.HourlyClickStats;
import com.personal.urlshortener.projectionmodel.RawUrlStats;
import com.personal.urlshortener.projectionmodel.ReferrerClickStats;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
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
        List<DailyClickStats> countClicksAndGroupByDate(@Param("url") Url url);

        //Find all bot clicks for all urls
        @Query("SELECT c " +
                "FROM Click c " +
                "WHERE c.ipInfo.isBot = true")
        List<Click> findAllBotClicks();

        //Find all bot clicks for a specific url
        @Query("SELECT c " +
                "FROM Click c " +
                "WHERE c.ipInfo.isBot = true " +
                "AND c.url = :url")
        List<Click> findBotClicksByUrl(@Param("url") Url url);

        //Find all human clicks for all urls
        @Query("SELECT c " +
                "FROM Click c " +
                "WHERE c.ipInfo.isBot = false")
        List<Click> findAllHumanClicks();

        //Find all human clicks for a specific url
        @Query("SELECT c " +
                "FROM Click c " +
                "WHERE c.ipInfo.isBot = false " +
                "AND c.url = :url")
        List<Click> findHumanClicksByUrl(@Param("url") Url url);

        //Find all clicks from a country
        @Query("SELECT c " +
                "FROM Click c " +
                "WHERE c.ipInfo.country = :country")
        List<Click> findClicksByCountry(@Param("country") String country);

        //Find all clicks by a specific device type
        @Query("SELECT c " +
                "FROM Click c " +
                "WHERE c.ipInfo.deviceType = :device")
        List<Click> findClicksByDevice(@Param("device") String device);

        //Find all clicks by a specific browser
        @Query("SELECT c " +
                "FROM Click c " +
                "WHERE c.ipInfo.browser = :browser")
        List<Click> findClicksByBrowser(@Param("browser") String browser);

        @Query("SELECT c.url.shortCode AS shortCode, " +
                "COUNT(c.clickedAt) AS clickCount, " +
                "DATE(c.url.createdAt) AS createdAt " +
                "FROM Click c " +
                "GROUP BY c.url " +
                "ORDER BY COUNT(c.clickedAt) DESC")
        Page<RawUrlStats> findTopPerformingUrls(Pageable pageable);

        @Query("SELECT HOUR(c.clickedAt) as hour, " +
                "COUNT(c) as count " +
                "FROM Click c " +
                "WHERE DATE(c.clickedAt) = :targetDate " +
                "GROUP BY HOUR(c.clickedAt)")
        List<HourlyClickStats> countClicksPerHourByDate(@Param("targetDate") LocalDate targetDate);

        @Query("SELECT c.ipInfo.referrer as referrer, " +
                "COUNT(c.clickedAt) as count " +
                "FROM Click c " +
                "GROUP BY c.ipInfo.referrer")
        List<ReferrerClickStats> countClicksFromReferrers();

        @Query("SELECT c.ipInfo.referrer as referrer, " +
                "COUNT(c.clickedAt) as count " +
                "FROM Click c " +
                "WHERE DATE(c.clickedAt) = :targetDate " +
                "GROUP BY c.ipInfo.referrer")
        List<ReferrerClickStats> countClicksFromReferrersByDate(@Param("targetDate") LocalDate targetDate);
}
