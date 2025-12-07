package com.tiffin.delivery.dto;

import java.time.LocalDateTime;
import java.util.List;

public class OrderRequest {
    private List<OrderItemRequest> items;
    private String deliveryAddress;
    private Double deliveryLatitude;
    private Double deliveryLongitude;
    private LocalDateTime deliveryDate;
    private String notes;
    
    public OrderRequest() {}
    
    // Getters and Setters
    public List<OrderItemRequest> getItems() { return items; }
    public void setItems(List<OrderItemRequest> items) { this.items = items; }
    
    public String getDeliveryAddress() { return deliveryAddress; }
    public void setDeliveryAddress(String deliveryAddress) { this.deliveryAddress = deliveryAddress; }
    
    public Double getDeliveryLatitude() { return deliveryLatitude; }
    public void setDeliveryLatitude(Double deliveryLatitude) { this.deliveryLatitude = deliveryLatitude; }
    
    public Double getDeliveryLongitude() { return deliveryLongitude; }
    public void setDeliveryLongitude(Double deliveryLongitude) { this.deliveryLongitude = deliveryLongitude; }
    
    public LocalDateTime getDeliveryDate() { return deliveryDate; }
    public void setDeliveryDate(LocalDateTime deliveryDate) { this.deliveryDate = deliveryDate; }
    
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
    
    public static class OrderItemRequest {
        private Long mealPlanId;
        private Integer quantity;
        
        public OrderItemRequest() {}
        
        public Long getMealPlanId() { return mealPlanId; }
        public void setMealPlanId(Long mealPlanId) { this.mealPlanId = mealPlanId; }
        
        public Integer getQuantity() { return quantity; }
        public void setQuantity(Integer quantity) { this.quantity = quantity; }
    }
}