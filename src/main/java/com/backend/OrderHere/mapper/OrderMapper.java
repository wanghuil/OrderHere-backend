package com.backend.OrderHere.mapper;

import com.backend.OrderHere.dto.Order.OrderGetDTO;
import com.backend.OrderHere.dto.Order.UpdateOrderStatusDTO;
import com.backend.OrderHere.model.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderMapper {
    @Mapping(source = "user.userId", target = "userId")
    @Mapping(source = "user.username", target = "userName")
    OrderGetDTO fromOrderToOrderGetDTO(Order order);

    UpdateOrderStatusDTO fromOrdertoUpdateOrderStatusDTO(Order updatedOrder);
}