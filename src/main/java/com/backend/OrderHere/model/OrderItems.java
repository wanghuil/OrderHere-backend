package com.backend.OrderHere.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.*;
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

//    @ManyToOne TODO
    @Column(name = "order_id", nullable = false)
    private Integer OrderId;

//    @ManyToOne TODO
    @Column(name = "dish_id", nullable = false)
    private Integer DishId;

    @Column(name = "dish_quantity", nullable = false)
    private Integer DishQuantity;
}

