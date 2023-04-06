package ru.job4j.multithreading.threadsync.notifywait.waitcontrol;

/**
 * Данный клас описывает принцип
 * работы метода {@link Thread#wait()}
 *
 * <p>Метод {@link Thread#wait()}
 * переводит нить в состояние ожидания,
 * если программа не может дальше
 * выполняться. Это позволяет не
 * тратить ресурсы впустую.
 *
 * <p>Когда другая нить выполнит
 * метод on или off, то у монитора
 * выполняется метод
 * {@link Thread#notifyAll()}.
 * Метод notifyAll переводит все нити
 * из состояния wait в runnable.
 * Чтобы избежать проблем с
 * согласованностью данных, метод
 * wait всегда вызывается в цикле while.
 */
public class Barrier {

    /**
     * Данное поле - это флаг,
     * общий ресурс, поэтому работа
     * с ним только в крит зоне.
     */
    private boolean flag = false;

    private final Object monitor = this;

    public void on() {
        synchronized (monitor) {
            flag = true;
            monitor.notifyAll();
        }
    }

    public void off() {
        synchronized (monitor) {
            flag = false;
            monitor.notifyAll();
        }
    }

    /**
     * Данный метод проверяет
     * состояние флага.
     */
    public void check() {
        synchronized (monitor) {
            while (!flag) {
                try {
                    monitor.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}
