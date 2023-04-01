package ru.job4j.basic.sync.notifywait.waitcontrol;

import ru.job4j.basic.sync.sharedresources.jcip.Count;

/**
 * 0. Управление нитью через wait.
 */
public class CountBarrier {

    private final Object monitor = this;

    /**
     * Данное поле содержит количество
     * вызовов метода
     * {@link CountBarrier#count()}.
     */
    private final int total;

    private int count = 0;

    public CountBarrier(final int total) {
        this.total = total;
    }

    /**
     * Данный метод увеличивает
     * счетчик поля count.
     */
    public void count() {
        synchronized (monitor) {
            count++;
            monitor.notifyAll();
        }
    }

    public void await() {
        while (count < total) {
            try {
                monitor.wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public static void main(String[] args) {
        CountBarrier barrier = new CountBarrier(5);
        Thread first = new Thread(
                () -> {
                    for (int i = 0; i < 5; i++) {
                        barrier.count();
                        System.out.println("First thread. Counter ran " + (i + 1) + " times");
                    }
                }
        );
        Thread second = new Thread(
                () -> {
                    barrier.await();
                    System.out.println("Second thread is over here");
                }
        );
        first.start();
        second.start();
    }
}
