package com.tiffin.delivery.repository;

import com.tiffin.delivery.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    
    List<User> findBySubscriptionType(User.SubscriptionType subscriptionType);
    
    @Query("SELECT COUNT(u) FROM User u WHERE u.subscriptionType = 'MONTHLY' AND u.subscriptionEndDate > :currentDate")
    long countActiveSubscribers(LocalDateTime currentDate);
    
    @Query("SELECT COUNT(u) FROM User u WHERE u.createdAt >= :startDate")
    long countNewUsersFromDate(LocalDateTime startDate);
}