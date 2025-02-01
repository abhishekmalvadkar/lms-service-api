package com.amalvadkar.lms.tags.entities;

import com.amalvadkar.lms.common.entities.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tags")
@Getter
@Setter
public class TagEntity extends BaseEntity {

    @Column(name = "name", nullable = false)
    private String name;

}
