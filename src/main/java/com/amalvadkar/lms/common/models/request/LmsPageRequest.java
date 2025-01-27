package com.amalvadkar.lms.common.models.request;

import lombok.Getter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Getter
public abstract class LmsPageRequest {
    private final int PageNo;
    private final Sort sort;
    private final int pageSize = 2;

    protected LmsPageRequest(int pageNo, Sort sort) {
        PageNo = pageNo;
        this.sort = sort;
    }

    public Pageable preparePageRequest() {
        int pageNo = getPageNo() <= 1 ? 0 : getPageNo() - 1;
        return PageRequest.of(pageNo, pageSize, getSort());
    }
}
