package ru.job4j.basic.sync.notifywait.blockingqueue;

import org.junit.Test;

import java.util.Arrays;
import java.util.concurrent.CopyOnWriteArrayList;

import static org.assertj.core.api.Assertions.*;

public class SimpleBlockingQueueTest {

    /**
     * В данном тесте переменная buffer нужна,
     * чтобы собрать данные в список и
     * проверить их в конце.
     *
     * <p>Обрати внимание, у потребителя
     * вначале двойная проверка. Это нужно,
     * чтобы проверить, что список пустой,
     * т.к. без этой проверки есть шанс того,
     * чтобы мы сможем прочитать НЕ ВСЕ данные.
     *
     * <p>Тест будет неполным, если не
     * дописать {@link Thread#join()}.
     * Главная нить может не дождаться выполнения
     * потребителя и производителя.
     * И, соответственно, тест завалится.
     */
    @Test
    public void whenBlockingQueueWorksCorrectly() throws InterruptedException {
        final CopyOnWriteArrayList<Integer> buffer = new CopyOnWriteArrayList<>();
        final SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(5);
        Thread producer = new Thread(
                () -> {
                    try {
                        for (int i = 0; i < 5; i++) {
                            queue.offer(i);
                        }
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
        );
        producer.start();
        Thread consumer = new Thread(
                () -> {
                    while (queue.size() != 0 || !Thread.currentThread().isInterrupted()) {
                        try {
                            buffer.add(queue.poll());
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                }
        );
        consumer.start();
        producer.join();
        consumer.interrupt();
        consumer.join();
        assertThat(buffer).isEqualTo(Arrays.asList(0, 1, 2, 3, 4));
    }
}