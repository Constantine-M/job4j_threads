package ru.job4j.basic.sync.sharedresources.racecondition;

/**
 * Данный класс описывает решение
 * проблемы состояния гонки, которое
 * демонстрируется в {@link RaceConditionExample}.
 *
 * <p>Чтобы решить данную проблему,
 * можно переписать код с применением
 * synchronized, который позволяет
 * только одной нити работать с
 * объектом одновременно.
 */
public class RaceConditionSolved {

    private int num = 0;

    public synchronized void incr() {
        for (int i = 0; i < 99999; i++) {
            int current = num;
            int next = num + 1;
            if (current + 1 != next) {
                throw new IllegalStateException("Некорректное сравнение: " + current + " + 1 = " + next);
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        RaceConditionSolved rc = new RaceConditionSolved();
        Thread t1 = new Thread(rc::incr);
        t1.start();
        Thread t2 = new Thread(rc::incr);
        t2.start();
        t1.join();
        t2.join();
    }
}
