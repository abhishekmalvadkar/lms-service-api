package com.amalvadkar.lms.links.models.response;

import com.amalvadkar.lms.tags.entities.TagEntity;

public record TagResponse(String tagId, String tagName) {

    public TagResponse(TagEntity tagEntity) {
        this(tagEntity.getId(), tagEntity.getName());
    }

}
