package com.backend.OrderHere.dto;

import lombok.Data;

@Data
public class OrderDishDTO {
    private Integer dishId;
    private String dishName;
    private Integer dishQuantity;
    private Double dishPrice;
}
