package ru.job4j.basic.sync.sharedresources;

import net.jcip.annotations.ThreadSafe;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Данный класс описывает кеш,
 * в котором хранит пользователей.
 *
 * Чтобы сделать код потокобезопасным,
 * необходимо избавиться от общих
 * ресурсов. Для этого мы создали
 * метод, который возвращает локальную
 * копию ресурса {@link User#of)}.
 *
 * В этом случае нити first и second
 * работают с локальными объектами
 * {@link User}. Изменение локального
 * объекта не влечет изменений в самом кеше.
 * Методы {@link UserCache#add} и
 * {@link UserCache#findById} работают
 * с копиями объекта {@link User}.
 */
@ThreadSafe
public class UserCache {

    private final ConcurrentHashMap<Integer, User> users = new ConcurrentHashMap();

    private final AtomicInteger id = new AtomicInteger();

    public void add(User user) {
        users.put(id.incrementAndGet(), User.of(user.getName()));
    }

    public User findById(int id) {
        return User.of(users.get(id).getName());
    }

    public List<User> findAll() {
        return new ArrayList<>(users.values());
    }
}
