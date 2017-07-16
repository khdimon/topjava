package ru.javawebinar.topjava.dao;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import ru.javawebinar.topjava.model.Meal;

public class MealDAOImpl implements MealDAO {

  public static Map<Integer, Meal> storage = new ConcurrentHashMap<>();
  public static AtomicInteger count = new AtomicInteger(0);
  public static MealDAO instance = new MealDAOImpl();

  {
    List<Meal> meals = Arrays.asList(
        new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
        new Meal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
        new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
        new Meal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
        new Meal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
        new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
    );

    meals.forEach(this::addMeal);
  }

  private MealDAOImpl() {}

  public static MealDAO getInstance() {
    return instance;
  }

  @Override
  public Meal getMealById(Integer id) {
    return storage.get(id);
  }

  @Override
  public Collection<Meal> getAllMeals() {
    return storage.values();
  }

  @Override
  public Meal addMeal(Meal meal) {
    if (meal.getId() != null) {
      return updateMeal(meal);
    }

    Integer id = count.incrementAndGet();
    meal.setId(id);
    storage.put(id, meal);
    return meal;
  }

  @Override
  public boolean deleteMeal(Meal meal) {
    if (meal.getId() != null && storage.containsKey(meal.getId())
        && storage.get(meal.getId()).equals(meal)) {
      storage.remove(meal.getId());
      return true;

    } else {
      return false;
    }
  }

  @Override
  public Meal updateMeal(Meal meal) {
    if (meal.getId() == null) {
      return addMeal(meal);
    }

    if (!storage.containsKey(meal.getId())) {
      return null;
    }

    storage.put(meal.getId(), meal);
    return meal;
  }
}
