package com.sh.financial.auth.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//@ResponseStatus(value= HttpStatus.BAD_REQUEST)
public class AuthAPIException extends RuntimeException{
    private HttpStatus status;
    private String message;

    public AuthAPIException(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public AuthAPIException(String message, HttpStatus status, String message1) {
        super(message);
        this.status = status;
        this.message = message1;
    }

    public HttpStatus getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
