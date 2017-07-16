package ru.javawebinar.topjava.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Meal {

  private Integer id;
  private final LocalDateTime dateTime;
  private final String description;
  private final int calories;

  public Meal(LocalDateTime dateTime, String description, int calories) {
    this.dateTime = dateTime;
    this.description = description;
    this.calories = calories;
  }

  public LocalDateTime getDateTime() {
    return dateTime;
  }

  public String getDescription() {
    return description;
  }

  public int getCalories() {
    return calories;
  }

  public LocalDate getDate() {
    return dateTime.toLocalDate();
  }

  public LocalTime getTime() {
    return dateTime.toLocalTime();
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Meal meal = (Meal) o;

    if (calories != meal.calories) {
      return false;
    }
    if (id != null ? !id.equals(meal.id) : meal.id != null) {
      return false;
    }
    if (!dateTime.equals(meal.dateTime)) {
      return false;
    }
    return description.equals(meal.description);
  }

  @Override
  public int hashCode() {
    int result = id != null ? id.hashCode() : 0;
    result = 31 * result + dateTime.hashCode();
    result = 31 * result + description.hashCode();
    result = 31 * result + calories;
    return result;
  }
}
