package com.personal.urlshortener.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "clicks")
public class Click {
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    @Id
    private Long clickId;

    @Column(updatable = false)
    private LocalDateTime clickedAt;
    //private String ipAddress;

    @ManyToOne
    @JoinColumn(name = "url_id", nullable = false)
    private Url url;

    @PrePersist
    public void setClickedAt(){
        this.clickedAt = LocalDateTime.now();
    }

    public Click(Url url) {
        this.url = url;
    }
}
