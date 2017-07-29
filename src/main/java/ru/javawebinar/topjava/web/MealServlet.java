package ru.javawebinar.topjava.web;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import ru.javawebinar.topjava.web.meal.MealRestController;

public class MealServlet extends HttpServlet {

  private static final Logger log = LoggerFactory.getLogger(MealServlet.class);

  private MealRestController controller;

  @Override
  public void init(ServletConfig config) throws ServletException {
    super.init(config);

    try (ConfigurableApplicationContext appCtx =
        new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
      controller = appCtx.getBean(MealRestController.class);
    }
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    request.setCharacterEncoding("UTF-8");

    String requestType = request.getParameter("type");
    if (requestType.equals("filter")) {
      doGet(request, response);
      return;
    }

    String id = request.getParameter("id");

    if (id.isEmpty()) {
      Meal meal = controller.createTemp(null,
          LocalDateTime.parse(request.getParameter("dateTime")),
          request.getParameter("description"),
          Integer.valueOf(request.getParameter("calories")));
      controller.create(meal);

    } else {
      Meal meal = controller.get(Integer.parseInt(id));
      controller.update(meal, Integer.parseInt(id));
    }

    response.sendRedirect("meals");
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    String action = request.getParameter("action");

    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    String startDateStr = request.getParameter("startDate");
    LocalDate startDate = startDateStr != null && !startDateStr.isEmpty() ?
        LocalDate.parse(startDateStr, dateFormatter) :
        null;

    String endDateStr = request.getParameter("endDate");
    LocalDate endDate = endDateStr != null && !endDateStr.isEmpty() ?
        LocalDate.parse(request.getParameter("endDate"), dateFormatter) :
        null;

    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

    String startTimeStr = request.getParameter("startTime");
    LocalTime startTime = startTimeStr != null && !startTimeStr.isEmpty() ?
        LocalTime.parse(startTimeStr, timeFormatter) :
        null;

    String endTimeStr = request.getParameter("endTime");
    LocalTime endTime = endTimeStr != null && !endTimeStr.isEmpty() ?
        LocalTime.parse(endTimeStr, timeFormatter) :
        null;

    switch (action == null ? "all" : action) {
      case "delete":
        int id = getId(request);
        controller.delete(id);
        response.sendRedirect("meals");
        break;
      case "create":
      case "update":
        final Meal meal = "create".equals(action) ?
            controller.createTemp(null, LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES),
                "", 1000) :
            controller.get(getId(request));
        request.setAttribute("meal", meal);
        request.getRequestDispatcher("/meal.jsp").forward(request, response);
        break;
      case "all":
      default:
        request.setAttribute("meals",
            controller.getAll(startTime, endTime, startDate, endDate));
        request.getRequestDispatcher("/meals.jsp").forward(request, response);
        break;
    }
  }

  private int getId(HttpServletRequest request) {
    String paramId = Objects.requireNonNull(request.getParameter("id"));
    return Integer.valueOf(paramId);
  }
}
