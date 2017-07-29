package ru.javawebinar.topjava.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.exception.NotFoundException;

public interface MealService {

  Meal save(int userId, Meal meal) throws NotFoundException;

  void delete(int userId, int MealId) throws NotFoundException;

  Meal get(int userId, int mealId) throws NotFoundException;

  void update(int userId, Meal meal) throws NotFoundException;

  List<MealWithExceed> getAll(int userId, LocalTime startTime, LocalTime endTime,
      LocalDate startDate, LocalDate endDate, int calories)
      throws NotFoundException;
}