package ru.job4j.multithreading.threadbasic.threadlocal;

/**
 * Данный класс показывает работу {@link ThreadLocal}.
 * Здесь мы видим, что каждое значение, которое
 * мы записывали из разных нитей в переменную tl,
 * вывелось на экран, то есть в каждой нити у
 * нас хранится своя копия переменной tl.
 *
 * Будьте внимательны, так как {@link ThreadLocal}
 * изолирует только ссылки на объекты.
 * Если в разных нитях записать в эту
 * переменную один и тот же объект, то
 * при работе с этим объектом проявятся
 * все проблемы многопоточности.
 */
public class ThreadLocalDemo {

    public static ThreadLocal<String> tl = new ThreadLocal<>();

    public static void main(String[] args) throws InterruptedException {
        Thread first = new FirstThread();
        Thread second = new SecondThread();
        tl.set("This is the main thread..");
        System.out.println(tl.get());
        first.start();
        second.start();
        first.join();
        second.join();
    }
}
