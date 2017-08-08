package ru.javawebinar.topjava;

import static ru.javawebinar.topjava.model.BaseEntity.START_SEQ;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import ru.javawebinar.topjava.matcher.BeanMatcher;
import ru.javawebinar.topjava.model.Meal;

public class MealTestData {

  public static final BeanMatcher<Meal> MATCHER = new BeanMatcher<>(
      (expected, actual) -> expected == actual ||
          (
              Objects.equals(expected.getId(), actual.getId())
                  && Objects.equals(expected.getDateTime(), actual.getDateTime())
                  && Objects.equals(expected.getDescription(), actual.getDescription())
                  && Objects.equals(expected.getCalories(), actual.getCalories())
          )
  );

  public static final int USER_ID = START_SEQ;
  public static final int ADMIN_ID = START_SEQ + 1;

  public static final int USER_MEAL_ID = START_SEQ + 2;

  public static final List<Meal> USER_MEALS = Arrays.asList(
      new Meal(USER_MEAL_ID, LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
      new Meal(USER_MEAL_ID + 1, LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
      new Meal(USER_MEAL_ID + 2, LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
      new Meal(USER_MEAL_ID + 3, LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
      new Meal(USER_MEAL_ID + 4, LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
      new Meal(USER_MEAL_ID + 5, LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
  );

  public static final int ADMIN_MEAL_ID = USER_MEAL_ID + USER_MEALS.size();

  public static final List<Meal> ADMIN_MEALS = Arrays.asList(
      new Meal(ADMIN_MEAL_ID, LocalDateTime.of(2015, Month.JUNE, 1, 14, 0), "Админ ланч", 510),
      new Meal(ADMIN_MEAL_ID + 1, LocalDateTime.of(2015, Month.JUNE, 1, 21, 0), "Админ ужин", 1500)
  );
}
