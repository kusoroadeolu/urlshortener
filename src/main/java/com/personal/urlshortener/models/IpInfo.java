package com.personal.urlshortener.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "ip_info")
@Setter
@Getter
@RequiredArgsConstructor
public class IpInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ipAddress;
    private String referrer;
    private String country;
    private String deviceType;
    private String browser;
    private String os;
    private Boolean isBot = false;

    @OneToOne
    private Click click;

    public IpInfo(String ipAddress,
                  String referrer,
                  String country,
                  String deviceType,
                  String browser, String os, Boolean isBot) {
        this.ipAddress = ipAddress;
        this.referrer = referrer;
        this.country = country;
        this.deviceType = deviceType;
        this.browser = browser;
        this.os = os;
        this.isBot = isBot;
    }
}
