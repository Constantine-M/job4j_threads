package ru.job4j.basic.sync.notifywait.waitcontrol;


/**
 * 0. Управление нитью через wait.
 *
 * <p>Если забыть про synchronized,
 * то во время дебага управление
 * будет без вашей воли перескакивать
 * на другую нить. А следом прога
 * будет вылетать с исключением
 * {@link IllegalMonitorStateException}
 * и сообщением:
 * "current thread not owner.".
 * Это сообщение абсолютно непонятное
 * и ни на что не указывает.
 *
 * <p>В одном из источников нашел
 * объяснение, которое позволило
 * обратить внимание на отсутствие блока.
 *
 * <p>This message means that the thread
 * calling wait( ), notify( ), or notifyAll( )
 * must “own” (acquire) the lock
 * for the object before it can call
 * any of these methods.
 * You can ask another object to perform
 * an operation that manipulates its own lock.
 * To do this, you must first capture
 * that object’s lock. For example,
 * if you want to notify( ) an object x,
 * you must do so inside a synchronized
 * block that acquires the lock for x:
 *
 * synchronized(x) {
 *   x.notify();
 * }
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
            System.out.println("Something changed. Counter up to " + count);
        }
    }

    /**
     * Данный метод дает команду
     * нити на ожидание.
     *
     * <p>В этом методе не было
     * synchronized блока. А в методе
     * {@link CountBarrier#count()} был.
     */
    public void await() {
        synchronized (monitor) {
            while (count < total) {
                try {
                    monitor.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    public static void main(String[] args) {
        CountBarrier barrier = new CountBarrier(5);
        Thread first = new Thread(
                () -> {
                    System.out.println("First thread runs. Second thread is waiting");
                    for (int i = 0; i < 5; i++) {
                        barrier.count();
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
