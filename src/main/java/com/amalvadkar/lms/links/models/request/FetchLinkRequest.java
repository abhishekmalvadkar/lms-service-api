package com.amalvadkar.lms.links.models.request;

import com.amalvadkar.lms.common.models.request.LmsPageRequest;
import lombok.Getter;
import org.springframework.data.domain.Sort;

@Getter
public class FetchLinkRequest extends LmsPageRequest {

    public static final String DEFAULT_SORT_BY = "updatedOn";

    private final String searchText;

    private final String tagId;


    public FetchLinkRequest(String searchText, int pageNo, String tagId){
        super(pageNo, Sort.by(DEFAULT_SORT_BY).descending());
        this.searchText = searchText;
        this.tagId = tagId;
    }

}
