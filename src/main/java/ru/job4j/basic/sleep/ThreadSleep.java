package ru.job4j.basic.sleep;

/**
 * Данный класс описывает пример,
 * в котором применяется метод
 * {@link Thread#sleep(long)}.
 * Этот метод переводит нить в состояние
 * {@link Thread.State#TIMED_WAITING}.
 * Так же этот метод может выкинуть
 * исключение {@link InterruptedException}.
 * Его нельзя выкинуть за сигнатуру
 * метода, поэтому мы его оборачиваем
 * в try-catch.
 */
public class ThreadSleep {

    public static void main(String[] args) {
        Thread thread = new Thread(
                () -> {
                    try {
                        System.out.println("Start loading ... ");
                        Thread.sleep(3000);
                        System.out.println("Loaded.");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
        );
        thread.start();
        System.out.println("Main");
    }
}
