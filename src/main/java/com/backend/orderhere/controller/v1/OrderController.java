package com.backend.orderhere.controller.v1;

import com.backend.orderhere.dto.order.OrderGetDTO;
import com.backend.orderhere.dto.order.PlaceOrderDTO;
import com.backend.orderhere.dto.order.UpdateOrderStatusDTO;
import com.backend.orderhere.model.Order;
import com.backend.orderhere.model.enums.OrderStatus;
import com.backend.orderhere.model.enums.OrderType;
import com.backend.orderhere.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/public/orders")

public class OrderController {
    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public ResponseEntity<List<OrderGetDTO>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderGetDTO> getOrderById(@PathVariable Integer orderId) {
        return ResponseEntity.ok(orderService.getOrderById(orderId));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderGetDTO>> getOrdersByUserId(@PathVariable Integer userId) {
        return ResponseEntity.ok(orderService.getOrderByUserId(userId));
    }

    @GetMapping("/status")
    public ResponseEntity<List<OrderGetDTO>> getOrderByOrderStatus(@RequestParam("orderStatus") OrderStatus orderStatus) {
        return ResponseEntity.ok(orderService.getOrderByOrderStatus(orderStatus));
    }

    @GetMapping("/type")
    public ResponseEntity<List<OrderGetDTO>> getOrderByOrderType(@RequestParam("orderType") OrderType orderType) {
        return ResponseEntity.ok(orderService.getOrderByOrderType(orderType));
    }

    @PatchMapping("/status")
    public ResponseEntity<UpdateOrderStatusDTO> updateOrderStatus(@RequestBody UpdateOrderStatusDTO updateOrderStatusDTO) {
        return ResponseEntity.ok().body(orderService.updateOrderStatus(updateOrderStatusDTO));
    }

    @PostMapping
    public ResponseEntity<Order> placeOrder(@RequestBody PlaceOrderDTO placeOrderDTO) {
        Order order = orderService.PlaceOrder(placeOrderDTO);
        return ResponseEntity.ok(order);
    }
}

