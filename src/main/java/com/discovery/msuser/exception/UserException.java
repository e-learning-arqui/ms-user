package com.discovery.msuser.exception;

import org.springframework.http.HttpStatus;

public class UserException extends Exception {
    private final int httpStatus;

    public UserException(int httpStatus, String message) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public int getHttpStatus() {
        return httpStatus;
    }

}
