package com.amalvadkar.lms.links.repositories;

import com.amalvadkar.lms.links.entities.LinkEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface LinkRepo extends JpaRepository<LinkEntity, String> {

    @Query("""
            select l from LinkEntity l
            JOIN FETCH l.tags where l.id = :linkId and l.createdBy.id = :userId and l.deleteFlag=false
            """)
    Optional<LinkEntity> findLinkWithTagsWithUser(@Param("linkId") String linkId, @Param("userId") String userId);

    @Query("""
            select (count(l) > 0) from LinkEntity l
            where l.url = :url and l.createdBy.id = :userId and l.deleteFlag = false""")
    boolean isUrlExists(@Param("url") String url, @Param("userId") String userId);


}
