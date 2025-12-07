package com.tiffin.delivery.controller;

import com.tiffin.delivery.entity.MealPlan;
import com.tiffin.delivery.entity.Order;
import com.tiffin.delivery.entity.User;
import com.tiffin.delivery.repository.MealPlanRepository;
import com.tiffin.delivery.repository.OrderRepository;
import com.tiffin.delivery.repository.UserRepository;
import com.tiffin.delivery.exception.ResourceNotFoundException;
import com.tiffin.delivery.exception.BadRequestException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/admin")
public class AdminController {
    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    private MealPlanRepository mealPlanRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    // Meal Management
    @PostMapping("/meals")
    public ResponseEntity<MealPlan> createMeal(@Valid @RequestBody MealPlan mealPlan) {
        try {
            logger.info("Creating new meal: {}", mealPlan.getName());
            MealPlan savedMeal = mealPlanRepository.save(mealPlan);
            logger.info("Meal created successfully with id: {}", savedMeal.getId());
            return ResponseEntity.ok(savedMeal);
        } catch (Exception e) {
            logger.error("Error creating meal: {}", mealPlan.getName(), e);
            throw new BadRequestException("Failed to create meal: " + e.getMessage());
        }
    }

    @PutMapping("/meals/{id}")
    public ResponseEntity<MealPlan> updateMeal(@PathVariable Long id, @Valid @RequestBody MealPlan mealPlan) {
        try {
            logger.info("Updating meal with id: {}", id);
            return mealPlanRepository.findById(id)
                    .map(existingMeal -> {
                        existingMeal.setName(mealPlan.getName());
                        existingMeal.setDescription(mealPlan.getDescription());
                        existingMeal.setRegularPrice(mealPlan.getRegularPrice());
                        existingMeal.setDiscountedPrice(mealPlan.getDiscountedPrice());
                        existingMeal.setMealType(mealPlan.getMealType());
                        existingMeal.setActive(mealPlan.isActive());
                        MealPlan updatedMeal = mealPlanRepository.save(existingMeal);
                        logger.info("Meal updated successfully: {}", updatedMeal.getName());
                        return ResponseEntity.ok(updatedMeal);
                    })
                    .orElseThrow(() -> {
                        logger.warn("Meal not found with id: {}", id);
                        return new ResourceNotFoundException("Meal not found with id: " + id);
                    });
        } catch (Exception e) {
            logger.error("Error updating meal with id: {}", id, e);
            throw new BadRequestException("Failed to update meal: " + e.getMessage());
        }
    }

    @DeleteMapping("/meals/{id}")
    public ResponseEntity<?> deleteMeal(@PathVariable Long id) {
        try {
            logger.info("Deleting meal with id: {}", id);
            return mealPlanRepository.findById(id)
                    .map(meal -> {
                        meal.setActive(false); // Soft delete
                        mealPlanRepository.save(meal);
                        logger.info("Meal soft deleted: {}", meal.getName());
                        return ResponseEntity.ok().build();
                    })
                    .orElseThrow(() -> {
                        logger.warn("Meal not found with id: {}", id);
                        return new ResourceNotFoundException("Meal not found with id: " + id);
                    });
        } catch (Exception e) {
            logger.error("Error deleting meal with id: {}", id, e);
            throw new BadRequestException("Failed to delete meal: " + e.getMessage());
        }
    }

    // Order Management
    @GetMapping("/orders")
    public ResponseEntity<List<Order>> getAllOrders() {
        try {
            logger.info("Fetching all orders");
            List<Order> orders = orderRepository.findAll();
            logger.info("Found {} orders", orders.size());
            return ResponseEntity.ok(orders);
        } catch (Exception e) {
            logger.error("Error fetching orders", e);
            throw e;
        }
    }

    @PutMapping("/orders/{id}/status")
    public ResponseEntity<Order> updateOrderStatus(@PathVariable Long id, @RequestBody StatusUpdateRequest request) {
        try {
            logger.info("Updating order status for id: {} to {}", id, request.getStatus());
            return orderRepository.findById(id)
                    .map(order -> {
                        order.setStatus(Order.OrderStatus.valueOf(request.getStatus()));
                        Order updatedOrder = orderRepository.save(order);
                        logger.info("Order status updated successfully");
                        return ResponseEntity.ok(updatedOrder);
                    })
                    .orElseThrow(() -> {
                        logger.warn("Order not found with id: {}", id);
                        return new ResourceNotFoundException("Order not found with id: " + id);
                    });
        } catch (Exception e) {
            logger.error("Error updating order status for id: {}", id, e);
            throw new BadRequestException("Failed to update order status: " + e.getMessage());
        }
    }

    // User Management
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        try {
            logger.info("Fetching all users");
            List<User> users = userRepository.findAll();
            logger.info("Found {} users", users.size());
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            logger.error("Error fetching users", e);
            throw e;
        }
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        logger.info("Fetching user with id: {}", id);
        return userRepository.findById(id)
                .map(user -> {
                    logger.info("Found user: {}", user.getEmail());
                    return ResponseEntity.ok(user);
                })
                .orElseThrow(() -> {
                    logger.warn("User not found with id: {}", id);
                    return new ResourceNotFoundException("User not found with id: " + id);
                });
    }

    public static class StatusUpdateRequest {
        private String status;
        
        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
    }
}