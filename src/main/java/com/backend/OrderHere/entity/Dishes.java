package com.backend.OrderHere.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.ZonedDateTime;


@Entity
@Getter
@Setter
@DynamicUpdate
@DynamicInsert
@Table(name = "Dishes")
public class Dishes {
    @Id
    @Column(name = "dish_id", nullable = false)
    private Integer DishId;

    @Column(name = "dish_name", nullable = false)
    private String DishName;

    @Column(name = "description", nullable = true)
    private String Description;
    
    @Column(name = "price", nullable = false,precision = 10, scale = 2)
    private Double Price;

    @Column(name = "image_url", nullable = true)
    private String ImageUrl;

    @Column(name = "category", nullable = false)
    private String Category;


    @Column(name = "rating", nullable = true,precision = 10, scale = 2)
    private Double Rating;

    @Column(name = "restaurant_id", nullable = false)
    private Integer RestaurantId;

    @Enumerated(EnumType.STRING)
    @Column(name = "availability", nullable = false)
    private Boolean Availability;

    @Column(name = "created_at", nullable = false)
    private ZonedDateTime CreatedAt;

    @Column(name = "updated_at", nullable = false)
    private ZonedDateTime UpdatedAt;
}
