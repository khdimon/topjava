package ru.javawebinar.topjava.repository.mock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {
    private Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach(m -> this.save(m.getUserId(), m));
    }

    @Override
    public Meal save(int userId, Meal meal) {
        if (meal.getUserId() != userId) {
            return null;
        }
        repository.computeIfAbsent(meal.getUserId(), k -> new HashMap<>());
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
        }
        repository.get(meal.getUserId()).put(meal.getId(), meal);
        return meal;
    }

    @Override
    public boolean delete(int userId, int mealId) {
        return (repository.get(userId) != null) && (repository.get(userId).remove(mealId) != null);
    }

    @Override
    public Meal get(int userId, int mealId) {
        if (repository.get(userId) == null) {
            return null;
        }
        return repository.get(userId).get(mealId);
    }

    @Override
    public List<Meal> getAll(int userId) {
        if (repository.get(userId) == null) {
            return new ArrayList<>();
        }
        List<Meal> result = new ArrayList<>(repository.get(userId).values());
        result.sort((m1, m2) -> m2.getDateTime().compareTo(m1.getDateTime()));
        return result;
    }
}