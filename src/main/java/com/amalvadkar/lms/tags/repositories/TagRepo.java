package com.amalvadkar.lms.tags.repositories;

import com.amalvadkar.lms.tags.entities.TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepo extends JpaRepository<TagEntity, String> {
}