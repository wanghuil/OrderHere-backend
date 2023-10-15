package com.backend.OrderHere.model;

import com.backend.OrderHere.model.enums.UserRoleEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.util.List;


@Entity
@Getter
@Setter
@DynamicUpdate
@DynamicInsert
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false, unique = true)
    private Integer userId;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "user_email", nullable = false, unique = true)
    private String email;

    @Column(name = "avatar_url")
    private String avatarUrl;

    @Column(name = "points")
    private int point;

    @Column(name = "address")
    private String address;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_role", nullable = false)
    private UserRoleEnum userRoleEnum;

    @CreationTimestamp
    @Column(name = "created_time", nullable = false)
    private ZonedDateTime createdTime;

    @UpdateTimestamp
    @Column(name = "updated_time", nullable = false)
    private ZonedDateTime updatedTime;
}
