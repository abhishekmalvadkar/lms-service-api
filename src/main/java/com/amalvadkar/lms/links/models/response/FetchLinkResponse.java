package com.amalvadkar.lms.links.models.response;

import com.amalvadkar.lms.links.entities.LinkEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public final class FetchLinkResponse {
    private String linkId;
    private String url;
    private String title;
    private List<TagResponse> tags;

    public FetchLinkResponse(LinkEntity linkEntity) {
        this.linkId = linkEntity.getId();
        this.url = linkEntity.getUrl();
        this.title = linkEntity.getTitle();
        this.tags = linkEntity.getTags().stream()
                .map(TagResponse::new)
                .toList();
    }
}
