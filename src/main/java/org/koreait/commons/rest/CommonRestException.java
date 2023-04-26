package org.koreait.commons.rest;

import org.springframework.http.HttpStatus;

public class CommonRestException extends RuntimeException {

    private HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

    public CommonRestException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

}
