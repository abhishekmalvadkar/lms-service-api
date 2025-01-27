package com.amalvadkar.lms.tags.specification;

import com.amalvadkar.lms.tags.entities.TagEntity;
import org.springframework.data.jpa.domain.Specification;

public class TagSpecification {

    public static Specification<TagEntity> hasCategory(String searchText) {
        return (root, query, criteriaBuilder) ->
              searchText==null ? null :  criteriaBuilder.like(root.get("name"), "%" + searchText + "%");

    }

    public static Specification<TagEntity> withCreatedBy(String userId){
          return (root, query, criteriaBuilder) ->
                  userId==null ? null :  criteriaBuilder.equal(root.get("createdBy").get("id"), userId);
    }

}
