package com.tiffin.delivery.dto;

import com.tiffin.delivery.entity.User;

public class AuthResponse {
    private String token;
    private String type = "Bearer";
    private Long id;
    private String name;
    private String email;
    private User.Role role;
    private User.SubscriptionType subscriptionType;
    
    public AuthResponse(String token, Long id, String name, String email, User.Role role, User.SubscriptionType subscriptionType) {
        this.token = token;
        this.id = id;
        this.name = name;
        this.email = email;
        this.role = role;
        this.subscriptionType = subscriptionType;
    }
    
    // Getters and Setters
    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
    
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public User.Role getRole() { return role; }
    public void setRole(User.Role role) { this.role = role; }
    
    public User.SubscriptionType getSubscriptionType() { return subscriptionType; }
    public void setSubscriptionType(User.SubscriptionType subscriptionType) { this.subscriptionType = subscriptionType; }
}