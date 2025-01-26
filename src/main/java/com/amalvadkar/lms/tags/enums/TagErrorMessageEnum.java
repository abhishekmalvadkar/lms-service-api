package com.amalvadkar.lms.tags.enums;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum TagErrorMessageEnum {

    TAG_ALREADY_EXISTS_ERR_MSG("Tag already exists");

    private final String value;

    public String value() {
        return value;
    }
}