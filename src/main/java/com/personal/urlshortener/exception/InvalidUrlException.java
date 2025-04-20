package com.personal.urlshortener.exception;

/**
 * Exception class to deal with invalid short urls
 * */
public class InvalidUrlException extends RuntimeException {
    /**
     * @param message The exception message
     * */
    public InvalidUrlException(String message) {
        super(message);
    }
}
