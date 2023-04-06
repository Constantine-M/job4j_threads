package ru.job4j.multithreading.threadsync.sharedresources.racecondition;

/**
 * Данный класс описывает пример
 * состояния гонки.
 *
 * <p>Состояние гонки наступает,
 * когда две и более нитей имеют
 * одновременный доступ к общему
 * ресурсу, вследствие чего
 * приложение имеет нестабильное
 * поведение.
 */
public class RaceConditionExample {

    public static int num = 0;

    public static void main(String[] args) {
        Runnable task = () -> {
            for (int i = 0; i < 99999; i++) {
                int current = num;
                int next = ++num;
                if (current + 1 != next) {
                    throw new IllegalStateException("Некорректное сравнение: " + current + " + 1 = " + next);
                }
            }
        };
        new Thread(task).start();
        new Thread(task).start();
    }
}
