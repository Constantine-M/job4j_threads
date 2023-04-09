package ru.job4j.multithreading.nonblockalgos.casbasic;

import java.util.concurrent.atomic.AtomicInteger;

public class CASCount {

    private final AtomicInteger count = new AtomicInteger();

    /**
     * В данном методе уместнее
     * использовать цикл do-while.
     * 1.Задаем локальные переменные
     * для текущего и следующего
     * значений.
     * 2.Затем мы увеличиваем счетчик,
     * а потом проверяем, что текущее
     * значение = значению счетчика
     * count (которое хранится в
     * памяти).
     * 3.Это нужно делать, т.к.
     * значение счетчика может
     * изменить другая нить.
     * 4.Пока значение локальной
     * переменной current = значению
     * счетчика count (который в памяти),
     * мы можем увеличивать значение
     * count на 1.
     */
    public void increment() {
        int current;
        int next;
        do {
            current = get();
            next = current + 1;
        } while (!count.compareAndSet(current, next));
    }

    public int get() {
        return count.get();
    }

    public static void main(String[] args) throws InterruptedException {
        CASCount counter = new CASCount();
        Thread first = new Thread(
                () -> {
                    for (int i = 0; i < 10; i++) {
                        counter.increment();
                        System.out.println(counter.get());
                    }
                }
        );
        Thread second = new Thread(
                () -> {
                    for (int i = 0; i < 10; i++) {
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
