package ru.javawebinar.topjava.web;

import static org.slf4j.LoggerFactory.getLogger;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.MealDAO;
import ru.javawebinar.topjava.dao.MealDAOImpl;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

public class MealServlet extends HttpServlet {

  private static final Logger log = getLogger(MealServlet.class);
  private MealDAO mealDAO = MealDAOImpl.getInstance();

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    log.debug("redirect to meals");

    List<Meal> meals = new ArrayList<>(mealDAO.getAllMeals());
    List<MealWithExceed> mealsWithExceed =
        MealsUtil.getMealsWithExceed(meals, MealsUtil.CALORIES_PER_DAY);
    mealsWithExceed.sort((m1, m2) -> m1.getDateTime().compareTo(m2.getDateTime()));
    request.setAttribute("meals", mealsWithExceed);

    request.getRequestDispatcher("/meals.jsp").forward(request, response);
    //response.sendRedirect("meals.jsp");
  }
}
