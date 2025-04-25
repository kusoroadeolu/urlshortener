package com.personal.urlshortener.exception;

public class UserAgentNotFoundException extends RuntimeException {
    public UserAgentNotFoundException(String message) {
        super(message);
    }
}
