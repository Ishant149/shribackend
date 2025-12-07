package com.tiffin.delivery.controller;

import com.tiffin.delivery.dto.*;
import com.tiffin.delivery.service.AuthService;
import com.tiffin.delivery.exception.BadRequestException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    
    @Autowired
    AuthService authService;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody AuthRequest loginRequest) {
        try {
            logger.info("Login attempt for email: {}", loginRequest.getEmail());
            AuthResponse response = authService.authenticateUser(loginRequest);
            logger.info("Login successful for email: {}", loginRequest.getEmail());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Login failed for email: {}", loginRequest.getEmail(), e);
            throw new BadRequestException("Invalid email or password");
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest signUpRequest) {
        try {
            logger.info("Registration attempt for email: {}", signUpRequest.getEmail());
            String message = authService.registerUser(signUpRequest);
            logger.info("Registration successful for email: {}", signUpRequest.getEmail());
            return ResponseEntity.ok(new MessageResponse(message));
        } catch (Exception e) {
            logger.error("Registration failed for email: {}", signUpRequest.getEmail(), e);
            throw new BadRequestException(e.getMessage());
        }
    }

    public static class MessageResponse {
        private String message;
        public MessageResponse(String message) { this.message = message; }
        public String getMessage() { return message; }
        public void setMessage(String message) { this.message = message; }
    }
}