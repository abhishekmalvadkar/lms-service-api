package com.amalvadkar.lms.common.entities;

import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@MappedSuperclass
public class BaseEntity {

    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "delete_flag", nullable = false)
    private Boolean deleteFlag;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", nullable = false)
    private UserEntity createdBy;

    @Column(name = "created_on", nullable = false)
    private Instant createdOn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "updated_by", nullable = false)
    private UserEntity updatedBy;

    @Column(name = "updated_on", nullable = false)
    private Instant updatedOn;

    @PrePersist
    public void preBasePersist() {
        this.id = UUID.randomUUID().toString();
        this.deleteFlag = Boolean.FALSE;
        this.createdOn = Instant.now();
        this.updatedOn = Instant.now();
    }
}