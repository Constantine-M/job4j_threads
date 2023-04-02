package ru.job4j.basic.sync.notifywait.blockingqueue;

import org.junit.Ignore;
import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

public class SimpleBlockingQueueTest {

    @Ignore
    @Test
    public void whenQueueFullCapacity() throws InterruptedException {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(3);
        Thread producer = new Thread(
                () -> {
                    for (int i = 0; i < 3; i++) {
                        queue.offer(i);
                    }
                }
        );
        Thread consumer = new Thread(
                () -> {
                    for (int i = 0; i < 3; i++) {
                        queue.poll();
                    }
                }
        );
        producer.start();
        producer.join();
        assertThat(queue.size()).isEqualTo(3);
        consumer.start();
        consumer.join();
    }

}