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

    public static final String  DELETE_FLAG = "deleteFlag";


    public static Specification<LinkEntity> getLink(FetchLinkRequest fetchLinkRequest, String loggedInUserId){
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (StringUtils.hasText(fetchLinkRequest.getSearchText())){
                predicates.add(criteriaBuilder.like(root.get("title"), "%" + fetchLinkRequest.getSearchText() + "%"));
            }

            if(StringUtils.hasText(fetchLinkRequest.getTagId())){
                Join<LinkEntity,TagEntity> tagJoin = root.join("tags", JoinType.INNER);
                predicates.add(criteriaBuilder.equal(tagJoin.get("id"), fetchLinkRequest.getTagId()));
            }

            predicates.add(criteriaBuilder.equal(root.get(CREATED_BY).get("id"), loggedInUserId));
            predicates.add(criteriaBuilder.equal(root.get(DELETE_FLAG) , false));

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }


}
