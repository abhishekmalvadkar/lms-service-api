package com.amalvadkar.lms.tags.dao;

import com.amalvadkar.lms.tags.models.dto.TagUpdateDto;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class TagDao {

    private final EntityManager em;

    public int updateTag(TagUpdateDto tagUpdateDto) {
        String updateTagNativeQuery = prepareUpdateTagNativeQuery(tagUpdateDto);
        return em.createNativeQuery(updateTagNativeQuery)
                .setParameter("name", tagUpdateDto.value())
                .setParameter("userId", tagUpdateDto.userId())
                .setParameter("tagId", tagUpdateDto.tagId())
                .executeUpdate();
    }

    private static String prepareUpdateTagNativeQuery(TagUpdateDto tagUpdateDto) {
        return String.format("""
                update %s as t
                set %s = :name ,
                updated_on = utc_timestamp(),
                updated_by = :userId
                where t.id = :tagId
                and delete_flag = false""", tagUpdateDto.tableName(), tagUpdateDto.columnName());
    }

}
