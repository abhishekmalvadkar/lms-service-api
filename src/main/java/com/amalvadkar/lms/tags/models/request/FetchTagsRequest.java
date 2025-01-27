package com.amalvadkar.lms.tags.models.request;

public record FetchTagsRequest(
        String searchText,
        int pageNO
) {
}
