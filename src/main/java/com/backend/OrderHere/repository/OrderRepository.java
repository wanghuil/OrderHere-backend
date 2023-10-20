package com.backend.OrderHere.repository;

import com.backend.OrderHere.model.Order;
import com.backend.OrderHere.model.User;
import com.backend.OrderHere.model.enums.OrderStatus;
import com.backend.OrderHere.model.enums.OrderType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    List<Order> findByOrderStatus(OrderStatus orderStatus);

    List<Order> findByOrderType(OrderType orderType);

    List<Order> findByUserUserId(Integer userId);
}
