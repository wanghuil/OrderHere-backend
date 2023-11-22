package com.backend.orderhere.service;

import com.backend.orderhere.dto.OrderDishDTO;
import com.backend.orderhere.dto.order.OrderGetDTO;
import com.backend.orderhere.dto.order.PlaceOrderDTO;
import com.backend.orderhere.dto.order.UpdateOrderStatusDTO;
import com.backend.orderhere.exception.ResourceNotFoundException;
import com.backend.orderhere.mapper.OrderMapper;
import com.backend.orderhere.model.Dish;
import com.backend.orderhere.model.LinkOrderDish;
import com.backend.orderhere.model.Order;
import com.backend.orderhere.model.User;
import com.backend.orderhere.model.enums.OrderStatus;
import com.backend.orderhere.model.enums.OrderType;
import com.backend.orderhere.repository.DishRepository;
import com.backend.orderhere.repository.LinkOrderDishRepository;
import com.backend.orderhere.repository.OrderRepository;
import com.backend.orderhere.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final LinkOrderDishRepository linkOrderDishRepository;
    private final DishRepository dishRepository;
    private final UserRepository userRepository;


    @Autowired
    public OrderService(OrderRepository orderRepository, OrderMapper orderMapper, LinkOrderDishRepository linkOrderRepository, DishRepository dishRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.linkOrderDishRepository = linkOrderRepository;
        this.dishRepository = dishRepository;
        this.orderMapper = orderMapper;
        this.userRepository = userRepository;
    }

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


    public Order PlaceOrder(PlaceOrderDTO placeOrderDTO) {
        User user = userRepository.findByUserId(placeOrderDTO.getUserId()).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Order order = orderMapper.dtoToOrder(placeOrderDTO);
        order.setUser(user);
        order = orderRepository.save(order);
        List<LinkOrderDish> links = new ArrayList<LinkOrderDish>();
        for (OrderDishDTO orderDishDTO : placeOrderDTO.getDishes()) {
            LinkOrderDish link = new LinkOrderDish();
            link.setOrder(order);
            Dish dish = dishRepository.findById(orderDishDTO.getDishId()).orElseThrow(() -> new RuntimeException("Dish not found with ID" + orderDishDTO.getDishId()));
            link.setDish(dish);
            link.setDishQuantity(orderDishDTO.getDishQuantity());
            links.add(link);
        }
        linkOrderDishRepository.saveAll(links);
        return order;
    }
}
