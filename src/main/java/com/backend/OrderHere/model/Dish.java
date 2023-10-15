package com.backend.OrderHere.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.ZonedDateTime;


@Entity
@Getter
@Setter
@DynamicUpdate
@DynamicInsert
@Table(name = "dish")
public class Dish {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dish_id", nullable = false)
    private Integer DishId;

    @Column(name = "dish_name", nullable = false)
    private String DishName;

    @Column(name = "description", nullable = true)
    private String Description;

    @Column(name = "price", nullable = false, precision = 5, scale = 2)
    private BigDecimal Price;

    @Column(name = "image_url", nullable = true)
    private String ImageUrl;

    @Column(name = "category", nullable = false)
    private String Category;

    @Column(name = "rating", nullable = true, precision = 3, scale = 1)
    private BigDecimal Rating;

    @Column(name = "restaurant_id", nullable = false)
    private Integer RestaurantId;

    @Column(name = "availability", nullable = false)
    private Boolean Availability;

    @CreationTimestamp
    @Column(name = "created_time", nullable = false)
    private ZonedDateTime createdTime;

    @UpdateTimestamp
    @Column(name = "updated_time", nullable = false)
    private ZonedDateTime updatedTime;
}
