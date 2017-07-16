package ru.javawebinar.topjava.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class MealWithExceed {

  private Integer id;
  private final LocalDateTime dateTime;
  private final LocalDate date;
  private final LocalTime time;
  private final String description;
  private final int calories;
  private final boolean exceed;

  public MealWithExceed(Integer id, LocalDateTime dateTime, String description, int calories, boolean exceed) {
    this.id = id;
    this.dateTime = dateTime;
    this.date = dateTime.toLocalDate();
    this.time = dateTime.toLocalTime();
    this.description = description;
    this.calories = calories;
    this.exceed = exceed;
  }

  @Override
  public String toString() {
    return "UserMealWithExceed{" +
        "dateTime=" + dateTime +
        ", description='" + description + '\'' +
        ", calories=" + calories +
        ", exceed=" + exceed +
        '}';
  }

  public LocalDateTime getDateTime() {
    return dateTime;
  }

  public LocalDate getDate() {
    return date;
  }

  public LocalTime getTime() {
    return time;
  }

  public String getDescription() {
    return description;
  }

  public int getCalories() {
    return calories;
  }

  public boolean isExceed() {
    return exceed;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }
}