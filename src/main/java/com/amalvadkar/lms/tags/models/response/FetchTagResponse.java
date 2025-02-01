package com.amalvadkar.lms.tags.models.response;

import com.amalvadkar.lms.tags.entities.TagEntity;

public record FetchTagResponse(String tagId, String tagName) {

    public FetchTagResponse(TagEntity tagEntity) {
        this(tagEntity.getId(), tagEntity.getName());
    }
}
