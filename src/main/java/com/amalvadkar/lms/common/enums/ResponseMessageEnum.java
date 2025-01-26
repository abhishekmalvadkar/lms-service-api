package com.amalvadkar.lms.common.enums;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ResponseMessageEnum {

    CREATED_SUCCESSFULLY_MSG("Created Successfully"),
    DELETED_SUCCESSFULLY_MSG("Deleted successfully");

    private final String value;

    public String value() {
        return value;
    }
}