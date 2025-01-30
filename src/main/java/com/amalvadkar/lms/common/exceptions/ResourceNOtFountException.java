package com.amalvadkar.lms.common.exceptions;

import org.springframework.http.HttpStatus;

public class ResourceNOtFountException extends LmsException{

    public ResourceNOtFountException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
