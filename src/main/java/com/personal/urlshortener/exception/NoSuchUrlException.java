package com.personal.urlshortener.exception;

/**
 * Exception class to deal with absent original urls
 * */
public class NoSuchUrlException extends RuntimeException {

    /**
     * @param message The exception message
     * */
    public NoSuchUrlException(String message) {
        super(message);
    }
}
