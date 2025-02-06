package com.amalvadkar.lms.links.specification;

import com.amalvadkar.lms.common.constants.UserEntityFields;
import com.amalvadkar.lms.common.entities.UserEntity;
import com.amalvadkar.lms.links.constants.LinkEntityFields;
import com.amalvadkar.lms.links.entities.LinkEntity;
import com.amalvadkar.lms.links.models.request.FetchLinkRequest;
import com.amalvadkar.lms.tags.constants.TagEntityFields;
import com.amalvadkar.lms.tags.entities.TagEntity;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LinkSpecification {

    public static Specification<LinkEntity> getLink(FetchLinkRequest fetchLinkRequest, String loggedInUserId) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = buildLinkPredicates(fetchLinkRequest, loggedInUserId, root, query, criteriaBuilder);
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    private static List<Predicate> buildLinkPredicates(FetchLinkRequest fetchLinkRequest, String loggedInUserId, Root<LinkEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        String searchText = fetchLinkRequest.getSearchText();

        if (notCount(query)) {
            root.fetch(LinkEntityFields.TAGS, JoinType.INNER);
        }

        if (StringUtils.hasText(searchText)) {
            predicates.add(criteriaBuilder.like(root.get(LinkEntityFields.TITLE), "%" + searchText + "%"));
        }

        if (StringUtils.hasText(fetchLinkRequest.getTagId())) {
            Join<LinkEntity, TagEntity> tagsJoin = root.join(LinkEntityFields.TAGS, JoinType.INNER);
            predicates.add(criteriaBuilder.equal(tagsJoin.get(TagEntityFields.ID), fetchLinkRequest.getTagId()));
        }

        Join<LinkEntity, UserEntity> createdByJoin = root.join(LinkEntityFields.CREATED_BY, JoinType.INNER);
        predicates.add(criteriaBuilder.equal(createdByJoin.get(UserEntityFields.ID), loggedInUserId));

        predicates.add(criteriaBuilder.isFalse(root.get(LinkEntityFields.DELETE_FLAG)));
        return predicates;
    }

    private static boolean notCount(CriteriaQuery<?> query) {
        return Long.class != Objects.requireNonNull(query).getResultType();
    }


}
