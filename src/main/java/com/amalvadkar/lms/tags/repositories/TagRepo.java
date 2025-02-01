package com.amalvadkar.lms.tags.repositories;

import com.amalvadkar.lms.tags.entities.TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TagRepo extends JpaRepository<TagEntity, String> , JpaSpecificationExecutor<TagEntity> {

    boolean existsByNameAndDeleteFlagIsFalse(String name);

    @Modifying
    @Query(value = """
            update tags as t set t.delete_flag = true,
            t.updated_on = utc_timestamp(),
            t.updated_by = :loggedInUserId
            where t.id = :tagId
            """, nativeQuery = true)
    int deleteTagById(@Param("tagId") String tagId, @Param("loggedInUserId") String loggedInUserId);

    @Query("""
            select t from TagEntity t
            join fetch t.createdBy
            join fetch t.updatedBy
            where t.id = :tagId
            """)
    Optional<TagEntity> findTagWithUser(@Param("tagId") String tagId);

}