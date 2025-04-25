package com.personal.urlshortener.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


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

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "ip_info_id")
    private IpInfo ipInfo;

    @PrePersist
    public void setClickedAt(){
        this.clickedAt = LocalDateTime.now();
    }

    public Click(Url url) {
        this.url = url;
    }

    public Click(Url url, IpInfo ipInfo) {
        this.url = url;
        this.ipInfo = ipInfo;
    }

    public String getFormattedDate(LocalDateTime localDateTime){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-ss");
        return localDateTime.format(dateTimeFormatter);
    }
}
