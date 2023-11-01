package com.backend.orderhere.dto;

import com.backend.orderhere.model.enums.OrderStatus;
import com.backend.orderhere.model.enums.OrderType;
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
