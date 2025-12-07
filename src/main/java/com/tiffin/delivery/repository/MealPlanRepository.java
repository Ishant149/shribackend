package com.tiffin.delivery.repository;

import com.tiffin.delivery.entity.MealPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MealPlanRepository extends JpaRepository<MealPlan, Long> {
    List<MealPlan> findByActiveTrue();
    List<MealPlan> findByMealTypeAndActiveTrue(MealPlan.MealType mealType);
}