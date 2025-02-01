package com.amalvadkar.lms.tags.specification;

import com.amalvadkar.lms.tags.entities.TagEntity;
import com.amalvadkar.lms.tags.models.request.FetchTagsRequest;
import com.amalvadkar.lms.tags.transformer.TagTransformer;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class TagSpecification {

    private static final String NAME = "name";
    private static final String CREATED_BY = "createdBy";
    private static final String DELETE_FLAG = "deleteFlag";
    private static final String ID = "id";

    public static Specification<TagEntity> getTags(FetchTagsRequest fetchTagsRequest , String loggedInUserId){
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (StringUtils.hasText(fetchTagsRequest.getSearchText())){
                predicates.add(criteriaBuilder.like(root.get(NAME), "%" + TagTransformer.transformTag(fetchTagsRequest.getSearchText()) + "%"));
            }

            predicates.add(criteriaBuilder.equal(root.get(CREATED_BY).get(ID), loggedInUserId));
            predicates.add(criteriaBuilder.equal(root.get(DELETE_FLAG) , false));

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

}
