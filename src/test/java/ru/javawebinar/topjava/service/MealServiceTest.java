package ru.javawebinar.topjava.service;

import static ru.javawebinar.topjava.MealTestData.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.DbPopulator;
import ru.javawebinar.topjava.util.exception.NotFoundException;

@ContextConfiguration({
    "classpath:spring/spring-app.xml",
    "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
public class MealServiceTest {

  static {
    SLF4JBridgeHandler.install();
  }

  @Autowired
  private MealService service;

  @Autowired
  private DbPopulator dbPopulator;

  @Before
  public void setUp() throws Exception {
    dbPopulator.execute();
  }

  @Test
  public void testGet() throws Exception {
    Meal meal = service.get(USER_MEAL_ID, USER_ID);
    MATCHER.assertEquals(USER_MEALS.get(0), meal);
  }

  @Test(expected = NotFoundException.class)
  public void testGetNotFound() throws Exception {
    service.get(1, USER_ID);
  }

  @Test(expected = NotFoundException.class)
  public void testGetWrongUser() throws Exception {
    service.get(USER_MEAL_ID, ADMIN_ID);
  }

  @Test
  public void testDelete() throws Exception {
    service.delete(USER_MEAL_ID, USER_ID);
    List<Meal> expected = new ArrayList<>(USER_MEALS);
    expected.remove(0);
    expected.sort((meal1, meal2) -> meal2.getDateTime().compareTo(meal1.getDateTime()));
    MATCHER.assertCollectionEquals(expected, service.getAll(USER_ID));
  }

  @Test(expected = NotFoundException.class)
  public void testNotFoundDelete() throws Exception {
    service.delete(1, USER_ID);
  }

  @Test(expected = NotFoundException.class)
  public void testWrongUserDelete() throws Exception {
    service.delete(USER_MEAL_ID, ADMIN_ID);
  }

  @Test
  public void testGetBetweenDates() throws Exception {
    //arrange
    List<Meal> expected = new ArrayList<>();
    expected.add(USER_MEALS.get(0));
    expected.add(USER_MEALS.get(1));
    expected.add(USER_MEALS.get(2));
    expected.sort((meal1, meal2) -> meal2.getDateTime().compareTo(meal1.getDateTime()));

    //act
    List<Meal> actual = service.getBetweenDates(LocalDate.of(2015, Month.MAY, 30),
        LocalDate.of(2015, Month.MAY, 30), USER_ID);

    //assert
    MATCHER.assertCollectionEquals(expected, actual);
  }

  @Test
  public void testGetBetweenDateTimes() throws Exception {
    //arrange
    List<Meal> expected = new ArrayList<>();
    expected.add(USER_MEALS.get(1));
    expected.add(USER_MEALS.get(2));
    expected.sort((meal1, meal2) -> meal2.getDateTime().compareTo(meal1.getDateTime()));

    //act
    List<Meal> actual = service.getBetweenDateTimes(LocalDateTime.of(2015, Month.MAY, 30, 12, 0),
        LocalDateTime.of(2015, Month.MAY, 30, 20, 0), USER_ID);

    //assert
    MATCHER.assertCollectionEquals(expected, actual);
  }

  @Test
  public void testGetAll() throws Exception {
    Collection<Meal> all = service.getAll(USER_ID);
    List<Meal> expected = new ArrayList<>(USER_MEALS);
    expected.sort((meal1, meal2) -> meal2.getDateTime().compareTo(meal1.getDateTime()));
    MATCHER.assertCollectionEquals(expected, all);
  }

  @Test
  public void testUpdate() throws Exception {
    Meal updated = new Meal(USER_MEALS.get(0));
    updated.setDateTime(LocalDateTime.of(2015, Month.JUNE, 3, 11, 0));
    updated.setDescription("UpdatedDescription");
    updated.setCalories(400);
    service.update(updated, USER_ID);
    MATCHER.assertEquals(updated, service.get(USER_MEAL_ID, USER_ID));
  }

  @Test(expected = NotFoundException.class)
  public void testWrongUserUpdate() throws Exception {
    Meal updated = new Meal(USER_MEALS.get(1));
    updated.setDateTime(LocalDateTime.of(2015, Month.JUNE, 3, 12, 0));
    updated.setDescription("UpdatedDescription");
    updated.setCalories(450);
    service.update(updated, ADMIN_ID);
  }

  @Test
  public void testSave() throws Exception {
    Meal newMeal = new Meal(null, LocalDateTime.of(2015, Month.JUNE, 2, 9, 0), "Завтрак", 555);
    Meal created = service.save(newMeal, USER_ID);
    int id = created.getId();
    newMeal.setId(id);
    MATCHER.assertEquals(newMeal, service.get(id, USER_ID));
  }
}