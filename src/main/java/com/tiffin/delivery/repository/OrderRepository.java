package com.tiffin.delivery.repository;

import com.tiffin.delivery.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserIdOrderByOrderDateDesc(Long userId);
    List<Order> findByStatusOrderByOrderDateDesc(Order.OrderStatus status);
    
    @Query("SELECT COUNT(o) FROM Order o WHERE o.orderDate >= :startDate")
    long countOrdersFromDate(LocalDateTime startDate);
    
    @Query("SELECT SUM(o.totalAmount) FROM Order o WHERE o.orderDate >= :startDate AND o.paymentStatus = 'COMPLETED'")
    Double getTotalRevenueFromDate(LocalDateTime startDate);
    
    @Query("SELECT o FROM Order o WHERE o.deliveryDate BETWEEN :startDate AND :endDate")
    List<Order> findOrdersForDeliveryBetween(LocalDateTime startDate, LocalDateTime endDate);
}