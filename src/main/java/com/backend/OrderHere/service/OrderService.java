package com.backend.OrderHere.service;

import com.backend.OrderHere.dto.Order.OrderGetDTO;
import com.backend.OrderHere.dto.Order.UpdateOrderStatusDTO;
import com.backend.OrderHere.exception.ResourceNotFoundException;
import com.backend.OrderHere.mapper.OrderMapper;
import com.backend.OrderHere.model.Order;
import com.backend.OrderHere.model.enums.OrderStatus;
import com.backend.OrderHere.model.enums.OrderType;
import com.backend.OrderHere.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    private final OrderMapper orderMapper;

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

    @Transactional
    public UpdateOrderStatusDTO updateOrderStatus(UpdateOrderStatusDTO updateOrderStatusDTO) {

        Order order = orderRepository.findById(updateOrderStatusDTO.getOrderId()).orElseThrow(() -> new ResourceNotFoundException("Order not found"));
        order.setOrderStatus(updateOrderStatusDTO.getOrderStatus());
        orderRepository.save(order);
        return orderMapper.fromOrdertoUpdateOrderStatusDTO(order);
    }

}
