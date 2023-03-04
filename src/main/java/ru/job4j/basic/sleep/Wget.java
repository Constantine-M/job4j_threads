package ru.job4j.basic.sleep;

/**
 * Данный класс демонстирирует возможности
 * метода {@link Thread#sleep(long)}
 * и симулирует процесс загрузки.
 *
 * Метод print печатает символы в
 * строку без перевода каретки.
 * Символ \r указывает, что каретку
 * каждый раз нужно вернуть в начало строки.
 */
public class Wget {
    public static void main(String[] args) {
        String ls = System.lineSeparator();
        System.out.println("Starting loading simulator..");
        Thread thread = new Thread(
                () -> {
                    try {
                        for (int index = 1; index <= 100; index++) {
                            System.out.print("\rLoading: " + index + "%");
                            Thread.sleep(1000);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(ls + "Loading complete! Thread#sleep() learned!");
                }
        );
        thread.start();
    }
}
