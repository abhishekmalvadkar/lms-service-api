package com.amalvadkar.lms.tags.models.request;

import com.amalvadkar.lms.common.models.request.LmsPageRequest;
import lombok.Getter;
import org.springframework.data.domain.Sort;

@Getter
public class FetchTagsRequest extends LmsPageRequest {

    public static final String DEFAULT_SORT_BY = "updatedOn";
    private final String searchText;

    FetchTagsRequest(int pageNo, String searchText) {
        super(pageNo , Sort.by(DEFAULT_SORT_BY).descending());
        this.searchText = searchText;
    }
}
