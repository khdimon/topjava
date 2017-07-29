package ru.javawebinar.topjava.repository;

import java.util.List;
import ru.javawebinar.topjava.model.Meal;

import java.util.Collection;

public interface MealRepository {
    Meal save(int userId, Meal meal);

    boolean delete(int userId, int mealId);

    Meal get(int userId, int mealId);

    List<Meal> getAll(int userId);
}
