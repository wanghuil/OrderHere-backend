package com.backend.OrderHere.dto.Order;

import com.backend.OrderHere.model.enums.OrderStatus;
import lombok.Data;

@Data
public class UpdateOrderStatusDTO {

    private Integer orderId;
    private OrderStatus orderStatus;

}
