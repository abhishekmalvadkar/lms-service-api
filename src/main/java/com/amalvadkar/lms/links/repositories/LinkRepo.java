package com.amalvadkar.lms.links.repositories;

import com.amalvadkar.lms.links.entities.LinkEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface LinkRepo extends JpaRepository<LinkEntity, String> , JpaSpecificationExecutor<LinkEntity> {

    @Query("""
            select l from LinkEntity l
            JOIN FETCH l.tags where l.id = :linkId and l.createdBy.id = :userId and l.deleteFlag=false
            """)
    Optional<LinkEntity> findLinkWithTagsWithUser(@Param("linkId") String linkId, @Param("userId") String userId);

    @Query("""
            select (count(l) > 0) from LinkEntity l
            where l.url = :url and l.createdBy.id = :userId and l.deleteFlag = false""")
    boolean isUrlExists(@Param("url") String url, @Param("userId") String userId);


    @Modifying
    @Query(value = """
            update links as l set l.delete_flag = true,
            l.updated_on = utc_timestamp(),
            l.updated_by = :loggedInUserId
            where l.id = :linkId
            """, nativeQuery = true)
    int deleteLink(@Param("linkId") String linkId,@Param("loggedInUserId") String loggedInUserId);





}
