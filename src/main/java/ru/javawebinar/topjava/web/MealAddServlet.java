package ru.javawebinar.topjava.web;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ru.javawebinar.topjava.dao.MealDAO;
import ru.javawebinar.topjava.dao.MealDAOImpl;
import ru.javawebinar.topjava.model.Meal;

public class MealAddServlet extends HttpServlet {

  private MealDAO mealDAO = MealDAOImpl.getInstance();

  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    request.setCharacterEncoding("UTF-8");
    LocalDate date = LocalDate.parse(request.getParameter("date"));
    LocalTime time = LocalTime.parse(request.getParameter("time"));
    LocalDateTime dateTime = LocalDateTime.of(date, time);
    String description = request.getParameter("description");
    Integer calories = Integer.parseInt(request.getParameter("calories"));
    Meal meal = new Meal(dateTime, description, calories);
    mealDAO.addMeal(meal);
    response.sendRedirect("meals");
  }

  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    request.getRequestDispatcher("/mealAdd.jsp").forward(request, response);
  }
}
