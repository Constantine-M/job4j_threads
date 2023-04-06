package ru.job4j.multithreading.threadsync.sharedresources.jcip;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

/**
 * 2. JCIP. Настройка библиотеки.
 *
 * Данный класс описывает счетчик.
 * На нем мы применяем функционал
 * библиотеки JCIP.
 *
 * Аннотация {@link ThreadSafe}
 * говорит пользователям данного класса,
 * что класс можно использовать в
 * многопоточном режиме и он будет
 * работать правильно.
 *
 * Аннотация {@link GuardedBy()}
 * выставляется над общим ресурсом.
 * Аннотация имеет входящий параметр.
 * Он указывает на монитор, по
 * которому мы будем синхронизироваться.
 */
@ThreadSafe
public class Count {

    @GuardedBy("this")
    private int value;

    public synchronized void increment() {
        this.value++;
    }

    public synchronized int get() {
        return this.value;
    }
}
