package ru.job4j.basic.sync.notifywait.waitcontrol;

/**
 * Данный класс демонстрирует
 * работу метода {@link Thread#wait()}.
 * Поток slave не запустится пока
 * flag = false. После исполнения
 * метода {@link Barrier#on()}
 * slave начнет свою работу.
 */
public class MultiUser {
    public static void main(String[] args) {
        Barrier barrier = new Barrier();
        Thread master = new Thread(
                () -> {
                    System.out.println(Thread.currentThread().getName() + " started");
                    barrier.on();
                },
                "Master"
        );
        Thread slave = new Thread(
                () -> {
                    barrier.check();
                    System.out.println(Thread.currentThread().getName() + " started");
                },
                "Slave"
        );
        master.start();
        slave.start();
    }
}
