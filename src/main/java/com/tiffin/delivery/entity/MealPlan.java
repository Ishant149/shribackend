package com.tiffin.delivery.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "meal_plans")
public class MealPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Meal name is required")
    @Size(min = 2, max = 100, message = "Meal name must be between 2 and 100 characters")
    private String name;
    
    @Size(max = 500, message = "Description cannot exceed 500 characters")
    private String description;
    
    @NotNull(message = "Regular price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Regular price must be greater than 0")
    @Digits(integer = 10, fraction = 2, message = "Invalid price format")
    private BigDecimal regularPrice;
    
    @NotNull(message = "Discounted price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Discounted price must be greater than 0")
    @Digits(integer = 10, fraction = 2, message = "Invalid price format")
    private BigDecimal discountedPrice;
    
    private String imageUrl;
    
    @NotNull(message = "Meal type is required")
    @Enumerated(EnumType.STRING)
    private MealType mealType;
    
    private boolean active = true;
    private LocalDateTime createdAt = LocalDateTime.now();
    
    // Constructors
    public MealPlan() {}
    
    public MealPlan(String name, String description, BigDecimal regularPrice, BigDecimal discountedPrice, MealType mealType) {
        this.name = name;
        this.description = description;
        this.regularPrice = regularPrice;
        this.discountedPrice = discountedPrice;
        this.mealType = mealType;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public BigDecimal getRegularPrice() { return regularPrice; }
    public void setRegularPrice(BigDecimal regularPrice) { this.regularPrice = regularPrice; }
    
    public BigDecimal getDiscountedPrice() { return discountedPrice; }
    public void setDiscountedPrice(BigDecimal discountedPrice) { this.discountedPrice = discountedPrice; }
    
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    
    public MealType getMealType() { return mealType; }
    public void setMealType(MealType mealType) { this.mealType = mealType; }
    
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public enum MealType {
        BREAKFAST, LUNCH, DINNER, COMBO
    }
}