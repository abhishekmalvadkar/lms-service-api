package com.amalvadkar.lms.common.exceptions.handler;

import com.amalvadkar.lms.common.exceptions.LmsException;
import com.amalvadkar.lms.common.models.response.CustomResModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(LmsException.class)
    public CustomResModel handleLmsException(LmsException ex) {
        logException(ex);
        return CustomResModel.fail(ex.getMessage(), ex.getHttpStatus());
    }

    @ExceptionHandler(Throwable.class)
    public CustomResModel handleThrowable(Throwable ex) {
        logException(ex);
        return CustomResModel.fail("Something went wrong, please try later..",
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private static void logException(Throwable ex) {
        log.error("Exception occurred -> ", ex);
    }

}
