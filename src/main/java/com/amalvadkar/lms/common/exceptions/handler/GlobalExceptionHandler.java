package com.amalvadkar.lms.common.exceptions.handler;

import com.amalvadkar.lms.common.exceptions.LmsException;
import com.amalvadkar.lms.common.models.response.CustomResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(LmsException.class)
    public CustomResponse handleLmsException(LmsException ex) {
        logException(ex);
        return CustomResponse.fail(ex.getMessage(), ex.getHttpStatus());
    }

    @ExceptionHandler(Throwable.class)
    public CustomResponse handleThrowable(Throwable ex) {
        logException(ex);
        return CustomResponse.fail("Something went wrong, please try later..",
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private static void logException(Throwable ex) {
        log.error("Exception occurred -> ", ex);
    }

}
