package ru.javawebinar.topjava.repository.jdbc;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class JdbcMealRepositoryImpl implements MealRepository {

    private static final BeanPropertyRowMapper<Meal> ROW_MAPPER = BeanPropertyRowMapper.newInstance(Meal.class);

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final SimpleJdbcInsert insertMeal;

    @Autowired
    public JdbcMealRepositoryImpl(DataSource dataSource, JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.insertMeal = new SimpleJdbcInsert(dataSource)
            .withTableName("meals")
            .usingGeneratedKeyColumns("id");

        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public Meal save(Meal meal, int userId) {
        MapSqlParameterSource map = new MapSqlParameterSource()
            .addValue("id", meal.getId())
            .addValue("user_id", userId)
            .addValue("date_time", meal.getDateTime())
            .addValue("description", meal.getDescription())
            .addValue("calories", meal.getCalories());

        if (meal.isNew()) {
            Number newKey = insertMeal.executeAndReturnKey(map);
            meal.setId(newKey.intValue());
        } else {
            int updatedLines = namedParameterJdbcTemplate.update(
                "UPDATE meals SET date_time=:date_time, description=:description,"
                    + " calories=:calories WHERE id=:id AND user_id=:user_id", map);
            if (updatedLines == 0) {
                return null;
            }
        }
        return meal;
    }

    @Override
    public boolean delete(int id, int userId) {
        MapSqlParameterSource map = new MapSqlParameterSource()
            .addValue("id", id)
            .addValue("user_id", userId);

        return namedParameterJdbcTemplate.update("DELETE FROM meals WHERE id=:id "
            + "AND user_id=:user_id", map) != 0;
    }

    @Override
    public Meal get(int id, int userId) {
        MapSqlParameterSource map = new MapSqlParameterSource()
            .addValue("id", id)
            .addValue("user_id", userId);

        List<Meal> meals = namedParameterJdbcTemplate.query("SELECT * FROM meals "
            + "WHERE id=:id AND user_id=:user_id", map, ROW_MAPPER);

        return DataAccessUtils.singleResult(meals);
    }

    @Override
    public List<Meal> getAll(int userId) {
        return jdbcTemplate.query("SELECT * FROM meals WHERE user_id=? "
            + "ORDER BY date_time DESC", ROW_MAPPER, userId);
    }

    @Override
    public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        MapSqlParameterSource map = new MapSqlParameterSource()
            .addValue("user_id", userId)
            .addValue("start_date", startDate)
            .addValue("end_date", endDate);

        return namedParameterJdbcTemplate.query("SELECT * FROM meals WHERE "
            + "meals.user_id=:user_id AND date_time>=:start_date "
            + "AND date_time<=:end_date ORDER BY date_time DESC", map, ROW_MAPPER);
    }
}
