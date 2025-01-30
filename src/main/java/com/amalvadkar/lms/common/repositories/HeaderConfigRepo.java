package com.amalvadkar.lms.common.repositories;

import com.amalvadkar.lms.common.entities.HeaderConfigEntity;
import com.amalvadkar.lms.common.exceptions.ResourceNOtFountException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;

import java.util.Optional;

public interface HeaderConfigRepo extends JpaRepository<HeaderConfigEntity, String> {

    @Query("select h from HeaderConfigEntity h where h.id = :headerConfigId and h.deleteFlag = false")
    Optional<HeaderConfigEntity> findHeaderConfigById(@Param("headerConfigId") String headerConfigId);

    default HeaderConfigEntity fetchHeaderConfigById(String headerConfigId){
      return   findHeaderConfigById(headerConfigId).orElseThrow(()-> new ResourceNOtFountException("header config not found", HttpStatus.NOT_FOUND));
    }
}
