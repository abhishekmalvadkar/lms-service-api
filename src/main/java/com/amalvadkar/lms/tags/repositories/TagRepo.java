package com.amalvadkar.lms.tags.repositories;

import com.amalvadkar.lms.tags.entities.TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TagRepo extends JpaRepository<TagEntity, String> {

    boolean existsByNameAndDeleteFlagIsFalse(String name);

    @Modifying
    @Query(value = """
            UPDATE tags as t SET t.delete_flag = true,
            t.updated_on = UTC_TIMESTAMP(),
            t.updated_by = :loggedInUserId
            WHERE t.id = :id""",nativeQuery = true)
    int deleteTagById(@Param("id") String id, @Param("loggedInUserId") String loggedInUserId);

    @Query("""
            select t from TagEntity t
            join fetch t.createdBy
            join fetch t.updatedBy
            where t.id = :tagId
            """)
    Optional<TagEntity> findTagWithUser(@Param("tagId") String tagId);

}