package com.amalvadkar.lms.common.models.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@Getter
public class CustomResponse {

    private Object data;
    private String message;
    private Boolean success;
    private Integer code;

    public static CustomResponse success(Object data, String message) {
        return builder()
                .data(data)
                .success(true)
                .message(message)
                .code(HttpStatus.OK.value())
                .build();
    }

    public static CustomResponse fail(String message , HttpStatus httpStatus) {
        return builder()
                .success(false)
                .message(message)
                .code(httpStatus.value())
                .build();
    }

    public static CustomResponse created(Object data, String message) {
        return builder()
                .data(data)
                .success(true)
                .message(message)
                .code(HttpStatus.CREATED.value())
                .build();
    }

    public static CustomResponse deleted(String message) {
        return builder()
                .success(true)
                .message(message)
                .code(HttpStatus.NO_CONTENT.value())
                .build();
    }

}
