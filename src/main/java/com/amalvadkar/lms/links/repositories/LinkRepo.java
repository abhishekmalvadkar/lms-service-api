package com.amalvadkar.lms.links.repositories;

import com.amalvadkar.lms.common.exceptions.ResourceNotFoundException;
import com.amalvadkar.lms.links.entities.LinkEntity;
import com.amalvadkar.lms.links.models.request.ViewLinkRequest;
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

    @Modifying
    @Query(value = """
            update links l set l.view_count = l.view_count + 1 ,
            l.updated_by = :loggedInUserId,
            l.updated_on = utc_timestamp()
            where l.id = :linkId
            """, nativeQuery = true)
    int updateViewCount(@Param("linkId") String linkId, @Param("loggedInUserId") String loggedInUserId);

    @Query(value = """
            select l.url from links l
            where l.id = :linkId
            """, nativeQuery = true)
    Optional<String> findLinkUrlById(@Param("linkId") String linkId);


    default String fetchLinkUrl(ViewLinkRequest viewLinkRequest) {
        return findLinkUrlById(viewLinkRequest.linkId())
                .orElseThrow(() -> new ResourceNotFoundException("link not found"));
    }
}
