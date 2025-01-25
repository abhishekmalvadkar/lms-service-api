package com.amalvadkar.lms.common.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class LmsException extends RuntimeException {

  private final HttpStatus httpStatus;

    public LmsException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

}
