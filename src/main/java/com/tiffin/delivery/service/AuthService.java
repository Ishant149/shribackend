package com.tiffin.delivery.service;

import com.tiffin.delivery.dto.*;
import com.tiffin.delivery.entity.User;
import com.tiffin.delivery.repository.UserRepository;
import com.tiffin.delivery.security.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    AuthenticationManager authenticationManager;
    
    @Autowired
    UserRepository userRepository;
    
    @Autowired
    PasswordEncoder encoder;
    
    @Autowired
    JwtUtils jwtUtils;

    public AuthResponse authenticateUser(AuthRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        String jwt = jwtUtils.generateJwtToken(authentication);
        User user = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow();
        
        return new AuthResponse(jwt, user.getId(), user.getName(), user.getEmail(), user.getRole(), user.getSubscriptionType());
    }

    public String registerUser(RegisterRequest signUpRequest) {
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new RuntimeException("Email is already taken!");
        }

        User user = new User(signUpRequest.getName(), signUpRequest.getEmail(), encoder.encode(signUpRequest.getPassword()));
        user.setPhone(signUpRequest.getPhone());
        user.setAddress(signUpRequest.getAddress());
        user.setLatitude(signUpRequest.getLatitude());
        user.setLongitude(signUpRequest.getLongitude());

        userRepository.save(user);
        return "User registered successfully!";
    }
}