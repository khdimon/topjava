package ru.javawebinar.topjava.service;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

@Service
public class MealServiceImpl implements MealService {

  @Autowired
  private MealRepository repository;

  @Override
  public Meal save(int userId, Meal meal) {
    return checkNotFoundWithId(repository.save(userId, meal), meal.getId());
  }

  @Override
  public void delete(int userId, int mealId) throws NotFoundException {
    checkNotFoundWithId(repository.delete(userId, mealId), mealId);
  }

  @Override
  public Meal get(int userId, int mealId) throws NotFoundException {
    return checkNotFoundWithId(repository.get(userId, mealId), mealId);
  }

  @Override
  public void update(int userId, Meal meal) {
    checkNotFoundWithId(repository.save(userId, meal), meal.getId());
  }

  @Override
  public List<MealWithExceed> getAll(int userId, LocalTime startTime, LocalTime endTime,
      LocalDate startDate, LocalDate endDate, int calories) {
    if (startDate == null) {
      startDate = LocalDate.MIN;
    }
    if (endDate == null) {
      endDate = LocalDate.MAX;
    }
    if (startTime == null) {
      startTime = LocalTime.MIN;
    }
    if (endTime == null) {
      endTime = LocalTime.MAX;
    }

    return MealsUtil.getFilteredByDayAndTimeWithExceeded(repository.getAll(userId),
        startTime, endTime, startDate, endDate, calories);
  }
}