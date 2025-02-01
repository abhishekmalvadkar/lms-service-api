package com.amalvadkar.lms.common.models.response;

import lombok.Builder;
import org.springframework.data.domain.Page;

import java.util.List;

@Builder
public record PagedResult<T>(
        List<T> content,
        long totalElements,
        int pageNumber,
        int totalPages,
        boolean isFirst,
        boolean isLast,
        boolean hasNext,
        boolean hasPrevious)
{
    public static <P, R> PagedResult<R> preparePagedResponse(Page<P> pagedEntity, List<R> dataResponseList) {
        return PagedResult.<R>builder()
                .content(dataResponseList)
                .totalElements(pagedEntity.getTotalElements())
                .pageNumber(pagedEntity.getNumber() + 1)
                .totalPages(pagedEntity.getTotalPages())
                .hasPrevious(pagedEntity.hasPrevious())
                .hasNext(pagedEntity.hasNext())
                .isFirst(pagedEntity.isFirst())
                .isLast(pagedEntity.isLast())
                .build();
    }
}
