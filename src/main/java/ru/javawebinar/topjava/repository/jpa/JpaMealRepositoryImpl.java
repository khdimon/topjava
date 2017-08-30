package ru.javawebinar.topjava.repository.jpa;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class JpaMealRepositoryImpl implements MealRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public Meal save(Meal meal, int userId) {
        User user = em.find(User.class, userId);
        if (meal.isNew()) {
            meal.setUser(user);
            em.persist(meal);
            return meal;
        } else {
            if (em.find(Meal.class, meal.getId()).getUser().getId() != userId) {
                return null;
            }
            meal.setUser(user);
            return em.merge(meal);
        }
    }

    @Override
    @Transactional
    public boolean delete(int id, int userId) {
        Meal meal = em.find(Meal.class, id);
        if (meal == null || meal.getUser().getId() != userId) {
            return false;
        }
        em.remove(meal);
        return true;
    }

    @Override
    public Meal get(int id, int userId) {
        Meal meal = em.find(Meal.class, id);
        if (meal.getUser().getId() != userId) {
            return null;
        }
        return meal;
    }

    @Override
    public List<Meal> getAll(int userId) {
        List<Meal> list = em.createNamedQuery(Meal.GET_ALL)
                .setParameter("user_id", userId)
                .getResultList();
        list.sort((m1, m2) -> ((Meal) m2).getDateTime().compareTo(((Meal) m1).getDateTime()));
        return list;
    }

    @Override
    public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        List<Meal> list = em.createNamedQuery(Meal.GET_BETWEEN)
                .setParameter("user_id", userId)
                .setParameter("start_date", startDate)
                .setParameter("end_date", endDate)
                .getResultList();
        list.sort((m1, m2) -> ((Meal) m2).getDateTime().compareTo(((Meal) m1).getDateTime()));
        return list;
    }
}