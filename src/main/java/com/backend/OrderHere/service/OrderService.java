package com.backend.OrderHere.service;

import com.backend.OrderHere.dto.Order.OrderGetDTO;
import com.backend.OrderHere.dto.Order.UpdateOrderStatusDTO;
import com.backend.OrderHere.exception.ResourceNotFoundException;
import com.backend.OrderHere.mapper.Order.OrderMapper;
import com.backend.OrderHere.mapper.Order.UpdateOrderStatusMapper;
import com.backend.OrderHere.model.Order;
import com.backend.OrderHere.model.enums.OrderStatus;
import com.backend.OrderHere.model.enums.OrderType;
import com.backend.OrderHere.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    private final OrderMapper orderMapper;

    private final UpdateOrderStatusMapper UpdateOrderStatusMapper;

    public List<OrderGetDTO> getAllOrders() {
        return orderRepository.findAll().stream().map(orderMapper::fromOrderToOrderGetDTO).collect(Collectors.toList());
    }

    public OrderGetDTO getOrderById(Integer orderId) {
        return orderMapper.fromOrderToOrderGetDTO(orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Order not found")));
    }

    public List<OrderGetDTO> getOrderByOrderStatus(OrderStatus orderStatus) {
        return orderRepository.findByOrderStatus(orderStatus).stream().map(orderMapper::fromOrderToOrderGetDTO).collect(Collectors.toList());
    }

    public List<OrderGetDTO> getOrderByOrderType(OrderType orderType) {
        return orderRepository.findByOrderType(orderType).stream().map(orderMapper::fromOrderToOrderGetDTO).collect(Collectors.toList());
    }

    public UpdateOrderStatusDTO updateOrderStatus(UpdateOrderStatusDTO updateOrderStatusDTO) {

        Order order = orderRepository.findById(updateOrderStatusDTO.getOrderId()).orElseThrow(() -> new ResourceNotFoundException("Order not found"));
        order.setOrderStatus(updateOrderStatusDTO.getOrderStatus());
        orderRepository.save(order);
        return UpdateOrderStatusMapper.fromOrdertoUpdateOrderStatusDTO(order);
    }

}
