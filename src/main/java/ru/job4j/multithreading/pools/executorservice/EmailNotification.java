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
     * в шаблон, а затем формирует
     * задачу, которая будет создавать
     * данные для пользователя
     * и передавать их в метод
     * {@link EmailNotification#send}.
     *
     * @param user пользователь.
     */
    public void emailTo(User user) {
        var subject = String.format("Notification %s to email %s", user.name(), user.email());
        var body = String.format("Add a new event to %s", user.name());
        pool.submit(new Thread(
                () -> send(subject, body, user.email())
        ));
    }

    private void send(String subject, String body, String email) {

    }

    /**
     * Данный метод закрывает пул.
     */
    public void close() {
        pool.shutdown();
        while (!pool.isTerminated()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
