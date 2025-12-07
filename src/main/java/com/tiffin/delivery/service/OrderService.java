package com.tiffin.delivery.service;

import com.tiffin.delivery.dto.OrderRequest;
import com.tiffin.delivery.entity.*;
import com.tiffin.delivery.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private MealPlanRepository mealPlanRepository;

    public Order createOrder(Long userId, OrderRequest orderRequest) {
        User user = userRepository.findById(userId).orElseThrow();
        Order order = new Order();
        order.setUser(user);
        order.setDeliveryAddress(orderRequest.getDeliveryAddress());
        order.setDeliveryLatitude(orderRequest.getDeliveryLatitude());
        order.setDeliveryLongitude(orderRequest.getDeliveryLongitude());
        order.setDeliveryDate(orderRequest.getDeliveryDate());
        order.setNotes(orderRequest.getNotes());

        BigDecimal totalAmount = BigDecimal.ZERO;
        for (OrderRequest.OrderItemRequest itemRequest : orderRequest.getItems()) {
            MealPlan mealPlan = mealPlanRepository.findById(itemRequest.getMealPlanId()).orElseThrow();
            BigDecimal price = user.getSubscriptionType() == User.SubscriptionType.MONTHLY ? 
                mealPlan.getDiscountedPrice() : mealPlan.getRegularPrice();
            totalAmount = totalAmount.add(price.multiply(BigDecimal.valueOf(itemRequest.getQuantity())));
        }
        
        order.setTotalAmount(totalAmount);
        return orderRepository.save(order);
    }

    public List<Order> getUserOrders(Long userId) {
        return orderRepository.findByUserIdOrderByOrderDateDesc(userId);
    }

    public Order updateOrderStatus(Long orderId, Order.OrderStatus status) {
        Order order = orderRepository.findById(orderId).orElseThrow();
        order.setStatus(status);
        if (status == Order.OrderStatus.DELIVERED) {
            order.setDeliveredAt(LocalDateTime.now());
        }
        return orderRepository.save(order);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
}