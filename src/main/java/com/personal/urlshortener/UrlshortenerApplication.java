package com.personal.urlshortener;

import com.personal.urlshortener.config.UrlProperties;
import com.personal.urlshortener.utils.UrlUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.security.SecureRandom;

@SpringBootApplication
public class UrlshortenerApplication {

	public static void main(String[] args) {
		SpringApplication.run(UrlshortenerApplication.class, args);

	}

}
