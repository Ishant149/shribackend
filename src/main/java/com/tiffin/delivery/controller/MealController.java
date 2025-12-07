package com.tiffin.delivery.controller;

import com.tiffin.delivery.entity.MealPlan;
import com.tiffin.delivery.repository.MealPlanRepository;
import com.tiffin.delivery.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/meals")
public class MealController {
    private static final Logger logger = LoggerFactory.getLogger(MealController.class);
    
    @Autowired
    private MealPlanRepository mealPlanRepository;

    @GetMapping
    public ResponseEntity<List<MealPlan>> getAllMeals() {
        try {
            logger.info("Fetching all active meals");
            List<MealPlan> meals = mealPlanRepository.findByActiveTrue();
            logger.info("Found {} active meals", meals.size());
            return ResponseEntity.ok(meals);
        } catch (Exception e) {
            logger.error("Error fetching meals", e);
            throw e;
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<MealPlan> getMealById(@PathVariable Long id) {
        logger.info("Fetching meal with id: {}", id);
        return mealPlanRepository.findById(id)
                .map(meal -> {
                    logger.info("Found meal: {}", meal.getName());
                    return ResponseEntity.ok(meal);
                })
                .orElseThrow(() -> {
                    logger.warn("Meal not found with id: {}", id);
                    return new ResourceNotFoundException("Meal not found with id: " + id);
                });
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<List<MealPlan>> getMealsByType(@PathVariable MealPlan.MealType type) {
        try {
            logger.info("Fetching meals by type: {}", type);
            List<MealPlan> meals = mealPlanRepository.findByMealTypeAndActiveTrue(type);
            logger.info("Found {} meals for type: {}", meals.size(), type);
            return ResponseEntity.ok(meals);
        } catch (Exception e) {
            logger.error("Error fetching meals by type: {}", type, e);
            throw e;
        }
    }
}