package ru.job4j.multithreading.threadsync.sharedresources;

public class User {

    private int id;
    private String name;

    /**
     * Данный метод возвращает локальную
     * копию объекта {@link User}.
     * Благодаря локальной копии
     * нити будут работать с локальной
     * копией, что делает код
     * потокобезопасным.
     * @param name имя пользователя.
     * @return копия объекта {@link User}.
     */
    public static User of(String name) {
        User user = new User();
        user.name = name;
        return user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{"
                + "id=" + id
                + ", name='" + name + '\''
                + '}';
    }
}
