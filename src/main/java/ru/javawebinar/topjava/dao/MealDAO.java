package ru.javawebinar.topjava.dao;

import java.util.Collection;
import java.util.List;
import ru.javawebinar.topjava.model.Meal;

public interface MealDAO {

  Meal getMealById(Integer id);

  Collection<Meal> getAllMeals();

  Meal addMeal(Meal meal);

  boolean deleteMeal(Meal meal);

  Meal updateMeal(Meal meal);
}
