package com.backend.OrderHere.model;

import lombok.Getter;
import lombok.Setter;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.*;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.ZonedDateTime;

@Entity
@Getter
@Setter
@DynamicUpdate
@DynamicInsert
@Table(name = "rating")
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "rating_id", nullable = false, unique = true)
    private Long ratingId;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private Integer userId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "dish_id", nullable = false)
    private Integer dishId;

    @Column(name = "rating_value", nullable = false)
    private Double ratingValue;

    @Column(name = "comments")
    private String comments;

    @CreationTimestamp
    @Column(name = "created_time", nullable = false)
    private ZonedDateTime createdTime;

    @UpdateTimestamp
    @Column(name = "updated_time", nullable = false)
    private ZonedDateTime updatedTime;
}
