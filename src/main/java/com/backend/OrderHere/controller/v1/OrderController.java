package com.backend.OrderHere.controller.v1;

import com.backend.OrderHere.dto.Order.OrderGetDTO;
import com.backend.OrderHere.dto.Order.UpdateOrderStatusDTO;
import com.backend.OrderHere.model.enums.OrderStatus;
import com.backend.OrderHere.model.enums.OrderType;
import com.backend.OrderHere.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/public/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<List<OrderGetDTO>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderGetDTO> getOrderById(@PathVariable Integer orderId) {
        return ResponseEntity.ok(orderService.getOrderById(orderId));
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

}
