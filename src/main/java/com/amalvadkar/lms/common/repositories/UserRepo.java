package com.amalvadkar.lms.common.repositories;

import com.amalvadkar.lms.common.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<UserEntity, String> {
}