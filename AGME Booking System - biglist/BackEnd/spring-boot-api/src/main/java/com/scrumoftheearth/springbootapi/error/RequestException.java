package com.scrumoftheearth.springbootapi.error;

import org.springframework.http.HttpStatus;

public class RequestException extends RuntimeException {

    private final HttpStatus errorStatusCode;

    public RequestException(HttpStatus errorStatusCode) {
        super();
        this.errorStatusCode = errorStatusCode;
    }

    public RequestException(String message, HttpStatus errorStatusCode) {
        super(message);
        this.errorStatusCode = errorStatusCode;
    }

    public RequestException(Throwable cause, HttpStatus errorStatusCode) {
        super(cause);
        this.errorStatusCode = errorStatusCode;
    }

    public RequestException(String message, HttpStatus errorStatusCode, Throwable cause) {
        super(message, cause);
        this.errorStatusCode = errorStatusCode;
    }

    public HttpStatus getErrorStatusCode() {
        return errorStatusCode;
    }
}
