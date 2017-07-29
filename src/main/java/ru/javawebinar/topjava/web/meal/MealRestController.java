package ru.javawebinar.topjava.web.meal;

import static ru.javawebinar.topjava.util.ValidationUtil.checkIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.web.MealServlet;

@Controller
public class MealRestController {

    @Autowired
    private MealService service;

    private static final Logger log = LoggerFactory.getLogger(MealServlet.class);

    public List<MealWithExceed> getAll(LocalTime startTime, LocalTime endTime,
        LocalDate startDate, LocalDate endDate) {

        log.info("getAll");

        return service.getAll(AuthorizedUser.id(), startTime, endTime, startDate, endDate,
            AuthorizedUser.getCaloriesPerDay());
    }

    public Meal get(int mealId) {
        return service.get(AuthorizedUser.id(), mealId);
    }

    public Meal create(Meal meal) {
        log.info("Create {}", meal);
        checkNew(meal);
        return service.save(AuthorizedUser.id(), meal);
    }

    public void delete(int mealId) {
        log.info("Delete {}", mealId);
        service.delete(AuthorizedUser.id(), mealId);
    }

    public void update(Meal meal, int mealId) {
        log.info("Update {}", meal);
        checkIdConsistent(meal, mealId);
        service.update(AuthorizedUser.id(), meal);
    }

    public Meal createTemp(Integer id, LocalDateTime dateTime, String description,
        int calories) {

        return new Meal(id, dateTime, description, calories, AuthorizedUser.id());
    }
}