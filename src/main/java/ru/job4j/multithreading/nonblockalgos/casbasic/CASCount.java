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
     *
     * <p>Имеется еще такой вариант объяснения:
     * Consider the following scenario:
     * 1.Thread 1 calls get and gets the value 1.
     * 2.Thread 1 calculates next to be 2.
     * 3.Thread 2 calls get and gets the value 1.
     * 4.Thread 2 calculates next to be 2.
     * 5.Both threads try to write the value.
     * Now because of atomics - only one thread
     * will succeed, the other will recieve false
     * from the compareAndSet and go around again.
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
