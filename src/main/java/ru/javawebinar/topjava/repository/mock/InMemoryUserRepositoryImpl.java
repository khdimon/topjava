package ru.javawebinar.topjava.repository.mock;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.NamedEntity;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import java.util.Collections;
import java.util.List;

@Repository
public class InMemoryUserRepositoryImpl implements UserRepository {

  private static final Logger log = LoggerFactory.getLogger(InMemoryUserRepositoryImpl.class);

  private Map<Integer, User> repository = new HashMap<>();
  private AtomicInteger counter = new AtomicInteger(0);

  {
    User user = new User(null, "Petr", "petr@mymail.com",
        "qwerty123", Role.ROLE_USER);
    User admin = new User(null, "Vasia", "admin@mymail.com",
        "ytrewq321", Role.ROLE_ADMIN);
    this.save(user);
    this.save(admin);
  }

  @Override
  public boolean delete(int id) {
    log.info("delete {}", id);
    return repository.remove(id) != null;
  }

  @Override
  public User save(User user) {
    if (user.isNew()) {
      log.info("save {}", user);
      user.setId(counter.incrementAndGet());
    } else {
      log.info("update {}", user);
    }
    repository.put(user.getId(), user);
    return user;
  }

  @Override
  public User get(int id) {
    log.info("get {}", id);
    return repository.get(id);
  }

  @Override
  public List<User> getAll() {
    log.info("getAll");
    List<User> result = new ArrayList<>(repository.values());
    result.sort(Comparator.comparing(NamedEntity::getName));
    return result;
  }

  @Override
  public User getByEmail(String email) {
    log.info("getByEmail {}", email);
    return repository.values().stream()
        .filter(u -> u.getEmail().equals(email))
        .collect(Collectors.toList())
        .get(0);

    /*for (User user : repository.values()) {
      if (user.getEmail().equals(email)) {
        return user;
      }
    }
    return null;*/
  }
}
