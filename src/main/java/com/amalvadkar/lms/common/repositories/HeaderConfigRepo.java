package com.amalvadkar.lms.common.repositories;

import com.amalvadkar.lms.common.entities.HeaderConfigEntity;
import com.amalvadkar.lms.common.exceptions.ResourceNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface HeaderConfigRepo extends JpaRepository<HeaderConfigEntity, String> {

    String HEADER_CONFIG_NOT_FOUND_MSG = "header config not found";

    @Query("select h from HeaderConfigEntity h where h.id = :headerConfigId and h.deleteFlag = false")
    Optional<HeaderConfigEntity> findHeaderConfigById(@Param("headerConfigId") String headerConfigId);

    default HeaderConfigEntity fetchHeaderConfig(String headerConfigId){
      return  findHeaderConfigById(headerConfigId)
              .orElseThrow(()-> new ResourceNotFoundException(HEADER_CONFIG_NOT_FOUND_MSG));
    }
}
