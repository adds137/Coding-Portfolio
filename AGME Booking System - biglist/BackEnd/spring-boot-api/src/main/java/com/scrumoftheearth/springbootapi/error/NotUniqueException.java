package com.scrumoftheearth.springbootapi.error;

import org.springframework.http.HttpStatus;

public class NotUniqueException extends RequestException {

    private final String invalidField;

    public NotUniqueException(String message, HttpStatus code, String invalidField) {
        super(message, code);
        this.invalidField = invalidField;
    }

    public String getInvalidField() {
        return invalidField;
    }
}
