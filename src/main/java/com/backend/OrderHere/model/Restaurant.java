package com.backend.OrderHere.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@DynamicUpdate
@DynamicInsert
@Table(name = "restaurant")
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "restaurant_id", nullable = false)
    private Integer restaurantId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String contactNumber;

    @Column
    private BigDecimal averageRating;

    @CreationTimestamp
    @Column(name = "created_time", nullable = false)
    private ZonedDateTime createdTime;

    @UpdateTimestamp
    @Column(name = "updated_time", nullable = false)
    private ZonedDateTime updatedTime;
}
