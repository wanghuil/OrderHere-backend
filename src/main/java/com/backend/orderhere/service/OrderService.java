package com.backend.orderhere.service;

import com.backend.orderhere.dto.OrderDishDTO;
import com.backend.orderhere.dto.order.OrderGetDTO;
import com.backend.orderhere.dto.order.PlaceOrderDTO;
import com.backend.orderhere.dto.order.UpdateOrderStatusDTO;
import com.backend.orderhere.dto.user.UserSignUpRequestDTO;
import com.backend.orderhere.exception.ResourceNotFoundException;
import com.backend.orderhere.filter.JwtUtil;
import com.backend.orderhere.mapper.OrderMapper;
import com.backend.orderhere.model.*;
import com.backend.orderhere.model.enums.OrderStatus;
import com.backend.orderhere.model.enums.OrderType;
import com.backend.orderhere.repository.*;
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
    private final RestaurantRepository restaurantRepository;
    private final UserService userService;


    @Autowired
    public OrderService(OrderRepository orderRepository, OrderMapper orderMapper, LinkOrderDishRepository linkOrderRepository, DishRepository dishRepository, UserRepository userRepository, RestaurantRepository restaurantRepository, UserService userService) {
        this.orderRepository = orderRepository;
        this.linkOrderDishRepository = linkOrderRepository;
        this.dishRepository = dishRepository;
        this.orderMapper = orderMapper;
        this.userRepository = userRepository;
        this.restaurantRepository = restaurantRepository;
        this.userService = userService;
    }

    public List<OrderGetDTO> getAllOrders() {
        return orderRepository.findAll().stream().map(orderMapper::fromOrderToOrderGetDTO).collect(Collectors.toList());
    }

    public OrderGetDTO getOrderById(Integer orderId) {
        return orderMapper.fromOrderToOrderGetDTO(orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Order not found")));
    }

    public List<OrderGetDTO> getOrderByUserId(Integer userId) {
        List<Order> orders = orderRepository.findByUserUserId(userId);
        if (orders.isEmpty()) {
            throw new ResourceNotFoundException("No orders found for the user");
        }
        return orders.stream()
                .map(orderMapper::fromOrderToOrderGetDTO)
                .collect(Collectors.toList());
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

    private UserSignUpRequestDTO createAnonymousUserSignUpRequest() {
        UserSignUpRequestDTO userSignUpRequestDTO = new UserSignUpRequestDTO();
        userSignUpRequestDTO.setUserName("anonymous" + System.currentTimeMillis());
        userSignUpRequestDTO.setFirstName("Anonymous");
        userSignUpRequestDTO.setLastName("User");
        userSignUpRequestDTO.setEmail("anonymous" + System.currentTimeMillis() + "@example.com");
        userSignUpRequestDTO.setPassword("defaultPassword");
        return userSignUpRequestDTO;
    }

    public Order PlaceOrder(String token, PlaceOrderDTO placeOrderDTO) {
//        User user = userRepository.findByUserId(placeOrderDTO.getUserId()).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Integer userId = (token != null) ? JwtUtil.getUserIdFromToken(token) : null;
        User user;
        if (userId == null) {
            UserSignUpRequestDTO anonymousUserDTO = createAnonymousUserSignUpRequest();
            user = userService.createAndReturnUser(anonymousUserDTO);
        } else {
            user = userRepository.findByUserId(userId)
                    .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        }
//        User user = userRepository.findByUserId(JwtUtil.getUserIdFromToken(token)).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Order order = orderMapper.dtoToOrder(placeOrderDTO);
        Restaurant restaurant = restaurantRepository.findById(placeOrderDTO.getRestaurantId())
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found with ID: " + placeOrderDTO.getRestaurantId()));
        order.setUser(user);
        order.setRestaurant(restaurant);
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
