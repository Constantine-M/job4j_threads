package ru.job4j.multithreading.pools.executorservice;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 2. ExecutorService рассылка почты.
 */
public class EmailNotification {

    private final ExecutorService pool = Executors.newFixedThreadPool(
            Runtime.getRuntime().availableProcessors());

    /**
     * Данный метод берет данные
     * пользователя и подставляет
     * в шаблон.
     *
     * @param user пользователь.
     */
    public String emailTo(User user) {
        return String.format("Notification %s to email %s", user.name(), user.email());
    }

    /*public void work() {
        pool.submit(new Thread(
                () -> {
                    send(emailTo());
                }
        ));
    }*/

    public void send(String subject, String body, String email) {

    }

    /**
     * Данный метод закрывает пул.
     */
    public void close() {
        pool.shutdown();
    }
}
