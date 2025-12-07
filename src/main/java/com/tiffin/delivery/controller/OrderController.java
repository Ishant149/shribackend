package com.tiffin.delivery.controller;

import com.tiffin.delivery.dto.OrderRequest;
import com.tiffin.delivery.entity.Order;
import com.tiffin.delivery.security.UserPrincipal;
import com.tiffin.delivery.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<Order> createOrder(@AuthenticationPrincipal UserPrincipal userPrincipal, @RequestBody OrderRequest orderRequest) {
        Order order = orderService.createOrder(userPrincipal.getId(), orderRequest);
        return ResponseEntity.ok(order);
    }

    @GetMapping("/my")
    public ResponseEntity<List<Order>> getMyOrders(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        List<Order> orders = orderService.getUserOrders(userPrincipal.getId());
        return ResponseEntity.ok(orders);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Order> updateOrderStatus(@PathVariable Long id, @RequestParam Order.OrderStatus status) {
        Order order = orderService.updateOrderStatus(id, status);
        return ResponseEntity.ok(order);
    }
}