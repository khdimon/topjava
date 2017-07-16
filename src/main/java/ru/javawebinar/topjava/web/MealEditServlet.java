package ru.javawebinar.topjava.web;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ru.javawebinar.topjava.dao.MealDAO;
import ru.javawebinar.topjava.dao.MealDAOImpl;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

public class MealEditServlet extends HttpServlet {

  private MealDAO mealDAO = MealDAOImpl.getInstance();

  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    request.setCharacterEncoding("UTF-8");
    Integer id = Integer.parseInt(request.getParameter("id"));
    LocalDate date = LocalDate.parse(request.getParameter("date"));
    LocalTime time = LocalTime.parse(request.getParameter("time"));
    LocalDateTime dateTime = LocalDateTime.of(date, time);
    String description = request.getParameter("description");
    Integer calories = Integer.parseInt(request.getParameter("calories"));
    Meal meal = mealDAO.getMealById(id);
    Meal newMeal = new Meal(dateTime, description, calories);
    newMeal.setId(id);
    mealDAO.updateMeal(newMeal);
    response.sendRedirect("meals");
  }

  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    String action = request.getParameter("action");
    if (action.equals("edit")) {
      Integer id = Integer.parseInt(request.getParameter("id"));
      Meal meal = mealDAO.getMealById(id);
      request.setAttribute("meal", meal);
      request.getRequestDispatcher("/mealEdit.jsp").forward(request, response);

    } else if (action.equals("delete")) {
      Integer id = Integer.parseInt(request.getParameter("id"));
      Meal meal = mealDAO.getMealById(id);
      mealDAO.deleteMeal(meal);
      request.getRequestDispatcher("/meals").forward(request, response);
    } else {
      request.getRequestDispatcher("/meals").forward(request, response);
    }
  }
}
