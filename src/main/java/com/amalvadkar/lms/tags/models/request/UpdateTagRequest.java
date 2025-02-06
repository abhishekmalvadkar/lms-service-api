package com.amalvadkar.lms.tags.models.request;

public record UpdateTagRequest(
        String headerConfigId,
        String value,
        String tagId
) {
}
