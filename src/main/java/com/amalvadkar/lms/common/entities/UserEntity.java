package com.amalvadkar.lms.common.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@Setter
public class UserEntity extends BaseEntity {

    @Column(name="first_name",nullable = false)
    private String firstName;

    @Column(name="last_name",nullable = false)
    private String lastName;

    @Column(name = "email" , nullable = false, unique = true)
    private String email;

    @Column(name = "last_login_time")
    private LocalDateTime lastLoginTime;

    @Column(name = "verification_token")
    private String verificationToken;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", nullable = false)
    private RoleEntity roleEntity;
}
