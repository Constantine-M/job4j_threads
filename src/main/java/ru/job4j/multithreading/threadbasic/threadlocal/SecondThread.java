package ru.job4j.multithreading.threadbasic.threadlocal;

/**
 * Для демонстрации возможностей
 * {@link ThreadLocal} мы создали
 * данный класс и поместили его
 * в переменную {@link ThreadLocal}.
 * Данные этого класса не будут
 * видны в других нитях.
 */
public class SecondThread extends Thread {

    @Override
    public void run() {
        ThreadLocalDemo.tl.set("This is thread #2..");
        System.out.println(ThreadLocalDemo.tl.get());
    }
}
