package com.tiffin.delivery.repository;

import com.tiffin.delivery.entity.WeeklyMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface WeeklyMenuRepository extends JpaRepository<WeeklyMenu, Long> {
    List<WeeklyMenu> findByDateBetweenOrderByDate(LocalDate startDate, LocalDate endDate);
    Optional<WeeklyMenu> findByDateAndMealPlan_MealType(LocalDate date, com.tiffin.delivery.entity.MealPlan.MealType mealType);
    List<WeeklyMenu> findByDayOfWeekAndAvailableTrue(DayOfWeek dayOfWeek);
}