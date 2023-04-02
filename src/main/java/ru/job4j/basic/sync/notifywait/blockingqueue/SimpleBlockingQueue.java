package ru.job4j.basic.sync.notifywait.blockingqueue;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 1. Реализовать шаблон Producer Consumer.
 *
 * <p>Bounded blocking queue - это блокирующая
 * очередь, ограниченная по размеру. В данном
 * шаблоне Producer помещает данные в очередь,
 * а Consumer извлекает данные из очереди.
 *
 * <p>Если очередь заполнена полностью, то
 * при попытке добавления поток Producer
 * блокируется, до тех пор пока Consumer
 * не извлечет очередные данные, т.е. в очереди
 * появится свободное место. И наоборот,
 * если очередь пуста поток Consumer блокируется,
 * до тех пор пока Producer не поместит
 * в очередь данные.
 */
@ThreadSafe
public class SimpleBlockingQueue<T> {

    private final int queueMaxSize;

    /**
     * Данная очередь является
     * общим ресурсом для Producer
     * и для Consumer.
     */
    @GuardedBy("this")
    private Queue<T> queue = new LinkedList<>();

    public SimpleBlockingQueue(int queueMaxSize) {
        this.queueMaxSize = queueMaxSize;
    }

    /**
     * Данный метод добавляет объект
     * в очередь.
     * @param value добавляемый объект.
     */
    public synchronized void offer(T value) {
        while (queue.size() != 0) {
            try {
                queue.wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        queue.add(value);
        queue.notify();
    }

    /**
     * Данный метод возвращает объект
     * из внутренней очереди.
     * @return the head of this queue.
     */
    public synchronized T poll() {
        while (queue.isEmpty()) {
            try {
                queue.wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        queue.notify();
        return queue.remove();
    }

    public int size() {
        return queue.size();
    }
}
