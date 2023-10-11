package com.backend.OrderHere.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.OffsetDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@DynamicUpdate
@DynamicInsert
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "order_id", nullable = false)
    private Long orderId;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @CreationTimestamp
    @Column(name = "created_time", nullable = false)
    private OffsetDateTime createdTime;

    @UpdateTimestamp
    @Column(name = "updated_time", nullable = false)
    private OffsetDateTime updatedTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_status", nullable = false)
    private OrderStatus orderStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_type", nullable = false)
    private OrderType orderType;

    @Column(name = "table_number")
    private Integer tableNumber;

    @Column(name = "pickup_time")
    private OffsetDateTime pickupTime;

    @Column(name = "address")
    private String address;

    @Column(name = "total_price", nullable = false)
    private Double totalPrice;

    @Column(name = "discount", nullable = false)
    private Double discount;

    @Column(name = "note")
    private String note;


}
