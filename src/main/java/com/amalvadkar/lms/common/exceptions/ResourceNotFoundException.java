package com.amalvadkar.lms.common.exceptions;

import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends LmsException{

    public ResourceNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
