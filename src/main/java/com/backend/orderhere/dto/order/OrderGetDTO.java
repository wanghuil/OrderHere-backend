package com.backend.orderhere.dto.order;

import lombok.Data;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Data
public class OrderGetDTO {

  private Integer orderId;
  private Integer restaurantId;
  private Integer userId;
  private String userName;
  private String orderStatus;
  private String orderType;
  private Integer tableNumber;
  private String pickupTime;
  private String address;
  private BigDecimal totalPrice;
  private String note;
  private ZonedDateTime updatedTime;


}
