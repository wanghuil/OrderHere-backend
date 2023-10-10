package com.backend.OrderHere.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@DynamicUpdate
@DynamicInsert
@Table(name = "orderItems")
public class OrderItems {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "order_item_id", nullable = false)
    private UUID orderItemId;

    @ManyToOne
    @Column(name = "order_id", nullable = false)
    private Integer OrderId;
    
    @ManyToOne
    @Column(name = "dish_id", nullable = false)
    private Integer DishId;

    @Column(name = "dish_quantity", nullable = false)
    private Integer DishQuantity;
}

