package com.country.routing.exception;

public class NoSuchCountryException extends RuntimeException {
    public NoSuchCountryException(String message) {
        super(message);
    }
}
