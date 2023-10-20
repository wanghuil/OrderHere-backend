package com.backend.OrderHere.mapper.Order;

import com.backend.OrderHere.dto.Order.UpdateOrderStatusDTO;
import com.backend.OrderHere.model.Order;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UpdateOrderStatusMapper {
    UpdateOrderStatusDTO fromOrdertoUpdateOrderStatusDTO(Order updatedOrder);

}
