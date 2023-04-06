package ru.job4j.multithreading.nonblockalgos.casbasic;

import java.util.concurrent.atomic.AtomicInteger;

public class CASCount {

    private final AtomicInteger count = new AtomicInteger();

    public void increment() {
        count.incrementAndGet();
    }

    public int get() {
        return count.get();
    }

    public static void main(String[] args) throws InterruptedException {
        CASCount counter = new CASCount();
        Thread first = new Thread(
                () -> {
                    for (int i = 0; i < 100; i++) {
                        counter.increment();
                        System.out.println(counter.get());
                    }
                }
        );
        Thread second = new Thread(
                () -> {
                    for (int i = 0; i < 100; i++) {
                        counter.increment();
                        System.out.println(counter.get());
                    }
                }
        );
        first.start();
        second.start();
        first.join();
        second.join();
    }
}
