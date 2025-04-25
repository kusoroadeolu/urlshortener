package com.personal.urlshortener.repos;

import com.personal.urlshortener.models.Url;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Repository to directly communicate with the db
 * */
@Repository
public interface UrlRepository extends JpaRepository<Url, Long>{
    //Find an url by its shortcode and vice versa
    Optional<Url> findUrlByShortCode(String shortcode);
    Optional<Url> findShortCodeByUrl(String url);



}
