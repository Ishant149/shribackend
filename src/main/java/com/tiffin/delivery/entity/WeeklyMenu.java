package com.tiffin.delivery.entity;

import jakarta.persistence.*;
import java.time.DayOfWeek;
import java.time.LocalDate;

@Entity
@Table(name = "weekly_menu")
public class WeeklyMenu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Enumerated(EnumType.STRING)
    private DayOfWeek dayOfWeek;
    
    private LocalDate date;
    
    @ManyToOne
    @JoinColumn(name = "meal_plan_id")
    private MealPlan mealPlan;
    
    private String specialNote;
    private boolean available = true;
    
    // Constructors
    public WeeklyMenu() {}
    
    public WeeklyMenu(DayOfWeek dayOfWeek, LocalDate date, MealPlan mealPlan) {
        this.dayOfWeek = dayOfWeek;
        this.date = date;
        this.mealPlan = mealPlan;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public DayOfWeek getDayOfWeek() { return dayOfWeek; }
    public void setDayOfWeek(DayOfWeek dayOfWeek) { this.dayOfWeek = dayOfWeek; }
    
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
    
    public MealPlan getMealPlan() { return mealPlan; }
    public void setMealPlan(MealPlan mealPlan) { this.mealPlan = mealPlan; }
    
    public String getSpecialNote() { return specialNote; }
    public void setSpecialNote(String specialNote) { this.specialNote = specialNote; }
    
    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) { this.available = available; }
}