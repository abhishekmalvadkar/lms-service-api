package com.amalvadkar.lms.tags.specification;

import com.amalvadkar.lms.common.constants.UserEntityFields;
import com.amalvadkar.lms.common.entities.UserEntity;
import com.amalvadkar.lms.tags.constants.TagEntityFields;
import com.amalvadkar.lms.tags.entities.TagEntity;
import com.amalvadkar.lms.tags.models.request.FetchTagsRequest;
import com.amalvadkar.lms.tags.transformer.TagTransformer;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class TagSpecification {

    public static Specification<TagEntity> getTags(FetchTagsRequest fetchTagsRequest , String loggedInUserId){
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = buildTagsPredicates(fetchTagsRequest, loggedInUserId, root, criteriaBuilder);
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    private static List<Predicate> buildTagsPredicates(FetchTagsRequest fetchTagsRequest, String loggedInUserId, Root<TagEntity> root, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        if (StringUtils.hasText(fetchTagsRequest.getSearchText())){
            String dashedSearchText = TagTransformer.transformTag(fetchTagsRequest.getSearchText());
            predicates.add(criteriaBuilder.like(root.get(TagEntityFields.NAME), "%" + dashedSearchText + "%"));
        }

        Join<TagEntity, UserEntity> createdByJoin = root.join(TagEntityFields.CREATED_BY, JoinType.INNER);
        predicates.add(criteriaBuilder.equal(createdByJoin.get(UserEntityFields.ID), loggedInUserId));

        predicates.add(criteriaBuilder.isFalse(root.get(TagEntityFields.DELETE_FLAG)));
        return predicates;
    }

}
