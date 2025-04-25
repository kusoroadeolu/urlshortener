package com.personal.urlshortener.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ua_parser.Parser;

import java.security.SecureRandom;

@Configuration
public class AppConfig {
    @Bean
    public SecureRandom secureRandom(){
        return new SecureRandom();
    }

    @Bean
    public Parser uaParser(){ return new Parser(); }

}
