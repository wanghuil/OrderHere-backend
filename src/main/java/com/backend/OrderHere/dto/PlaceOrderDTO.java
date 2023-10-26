package com.backend.OrderHere.dto;

import com.backend.OrderHere.model.enums.OrderStatus;
import com.backend.OrderHere.model.enums.OrderType;
import com.backend.OrderHere.dto.OrderDishDTO;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class PlaceOrderDTO {
    private Integer user;
    private Integer tableNumber;
    private OrderType orderType;
    private OrderStatus orderStatus;
    private BigDecimal discount;
    private List<OrderDishDTO> dishes;
    private Double totalPrice;
}
