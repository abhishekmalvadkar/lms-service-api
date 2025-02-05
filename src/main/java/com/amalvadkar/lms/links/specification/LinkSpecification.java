package com.amalvadkar.lms.links.specification;

import com.amalvadkar.lms.links.entities.LinkEntity;
import com.amalvadkar.lms.links.models.request.FetchLinkRequest;
import com.amalvadkar.lms.tags.entities.TagEntity;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class LinkSpecification {

    public static final String CREATED_BY = "createdBy";
    public static final String DELETE_FLAG = "deleteFlag";
    public static final String TITLE = "title";
    public static final String TAGS = "tags";
    public static final String ID = "id";


    public static Specification<LinkEntity> getLink(FetchLinkRequest fetchLinkRequest, String loggedInUserId){
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Only apply fetch if the query is retrieving entities (not count)
            if (!Long.class.equals(query.getResultType())) {
                root.fetch(TAGS, JoinType.INNER);
            }

            if (StringUtils.hasText(fetchLinkRequest.getSearchText())){
                predicates.add(criteriaBuilder.like(root.get(TITLE), "%" + fetchLinkRequest.getSearchText() + "%"));
            }

            if(StringUtils.hasText(fetchLinkRequest.getTagId())){
                // Use a separate JOIN (without fetch) for filtering
                Join<LinkEntity, TagEntity> joinTags = root.join(TAGS, JoinType.INNER);
                predicates.add(criteriaBuilder.equal(joinTags.get(ID), fetchLinkRequest.getTagId()));
            }

            predicates.add(criteriaBuilder.equal(root.get(CREATED_BY).get(ID), loggedInUserId));
            predicates.add(criteriaBuilder.equal(root.get(DELETE_FLAG) , false));

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }


}
