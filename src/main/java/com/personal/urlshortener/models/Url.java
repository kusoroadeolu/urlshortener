package com.personal.urlshortener.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "urls")

/**
 * Model class for the url table
 * */
public class Url {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    @Column(name = "url_id")
    private Long urlId;

    @Column(name= "url", unique = true)
    private String url;

    @Column(name = "short_code", unique = true)
    private String shortCode;

    @Transient
    private String shortenedUrl;

    @Transient
    private LocalDateTime lastAccessed;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "url", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Click> clicks;

    public Url(String url, String shortCode, String shortenedUrl) {
        this.url = url;
        this.shortCode = shortCode;
        this.shortenedUrl = shortenedUrl;
        this.updatedAt = LocalDateTime.now();
    }

    //Automatically sets the created at to the current date/time before being saved to the db
    @PrePersist
    public void setCreatedAt(){
        this.createdAt = LocalDateTime.now();
    }

    public String getFormattedDate(LocalDateTime localDateTime){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-ss");
        return localDateTime.format(dateTimeFormatter);
    }



}
