package com.amalvadkar.lms.common.models.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@Getter
public class CustomResModel {

    private Object data;
    private String message;
    private Boolean success;
    private Integer code;

    public static CustomResModel success(Object data, String message) {
        return builder()
                .data(data)
                .success(true)
                .message(message)
                .code(HttpStatus.OK.value())
                .build();
    }

    public static CustomResModel created(Object data, String message) {
        return builder()
                .data(data)
                .success(true)
                .message(message)
                .code(HttpStatus.CREATED.value())
                .build();
    }

}
