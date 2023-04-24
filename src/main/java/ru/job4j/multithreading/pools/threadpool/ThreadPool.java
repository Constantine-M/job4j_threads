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

    private final static int THREAD_POOL_SIZE = Runtime.getRuntime().availableProcessors();

    private final List<Thread> threads = new LinkedList<>();

    private final SimpleBlockingQueue<Runnable> tasks = new SimpleBlockingQueue<>(THREAD_POOL_SIZE);

    /**
     * В данном конструкторе фактически
     * настроен весь конвейер.
     *
     * <p>1.Создается кол-во потоков =
     * кол-ву ядер процессора.
     * 2.Каждая нить возвращает объект
     * из внутренней очереди (по сути
     * выполняет задачу).
     *
     * <p>Конвейер будет работать
     * до тех пор, пока не вызовут
     * метод {@link Thread#interrupt()}.
     * Я добавил логгер для личного удобства,
     * чтобы было видно, что в этот
     * момент произойдет (остановка ThreadPool).
     */
    public ThreadPool() {
        for (int i = 0; i < THREAD_POOL_SIZE; i++) {
            threads.add(new Thread(
                    () -> {
                        while (!Thread.currentThread().isInterrupted()) {
                            try {
                                tasks.poll();
                            } catch (InterruptedException e) {
                                LOG.error("Threadpool is stopped. " + e);
                                Thread.currentThread().interrupt();
                            }
                        }
                    }
            ));
        }
    }

    /**
     * Данный метод добавляет задачи
     * в блокирующую очередь.
     *
     * @param job добавляемая задача.
     */
    public void work(Runnable job) throws InterruptedException {
        tasks.offer(job);
    }

    /**
     * Данный метод останавливает
     * все нити (все запущенные задачи).
     */
    public void shutdown() {
        threads.forEach(Thread::interrupt);
    }
}
