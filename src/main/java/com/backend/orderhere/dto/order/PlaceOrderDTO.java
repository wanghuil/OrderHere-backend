package com.backend.orderhere.dto.order;

import com.backend.orderhere.dto.OrderDishDTO;
import com.backend.orderhere.model.User;
import com.backend.orderhere.model.enums.OrderStatus;
import com.backend.orderhere.model.enums.OrderType;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class PlaceOrderDTO {
    private User user;
    private Integer tableNumber;
    private OrderType orderType;
    private OrderStatus orderStatus;
    private BigDecimal discount;
    private List<OrderDishDTO> dishes;
    private Double totalPrice;
}
