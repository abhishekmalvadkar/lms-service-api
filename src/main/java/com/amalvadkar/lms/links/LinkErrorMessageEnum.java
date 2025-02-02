package com.amalvadkar.lms.links;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum LinkErrorMessageEnum {

    LINK_ALREADY_EXISTS_ERR_MSG("Link already exists");

    private final String value;

    public String value(){
        return value;
    }

}
