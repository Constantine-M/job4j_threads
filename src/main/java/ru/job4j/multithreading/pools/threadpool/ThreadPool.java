package ru.job4j.multithreading.pools.threadpool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.multithreading.threadsync.notifywait.blockingqueue.SimpleBlockingQueue;

import java.util.LinkedList;
import java.util.List;

/**
 * 1. Реализовать ThreadPool.
 */
public class ThreadPool {

    private final static Logger LOG = LoggerFactory.getLogger(ThreadPool.class.getName());

    private final List<Thread> threads = new LinkedList<>();

    private final SimpleBlockingQueue<Runnable> tasks = new SimpleBlockingQueue<>(5);

    public ThreadPool() {
        int size = Runtime.getRuntime().availableProcessors();
        for (int i = 0; i < size; i++) {
            threads.add(new Thread(
                    () -> {
                        try {
                            tasks.poll();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
            ));
        }
    }

    /**
     * Данный метод добавляет задачи
     * в блокирующую очередь.
     *
     * <p>Чтобы он не добавлял их постоянно,
     * нужно сделать проверку на то, что
     * работа всех потоков не остановлена.
     *
     * <p>Если работа всех потоков была
     * остановлена, то метод должен выбросить
     * исключение. Я добавил логгер
     * для личного удобства, чтобы было
     * видно, что в этот момент произойдет
     * (остановка ThreadPool).
     *
     * @param job добавляемая задача.
     */
    public void work(Runnable job) {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                tasks.offer(job);
            } catch (InterruptedException e) {
                LOG.error("Threadpool is stopped.");
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Данный метод останавливает
     * все нити (все запущенные задачи).
     */
    public void shutdown() {
        threads.forEach(Thread::interrupt);
    }
}
