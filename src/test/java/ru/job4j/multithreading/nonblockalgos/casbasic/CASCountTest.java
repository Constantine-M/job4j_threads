package ru.job4j.multithreading.nonblockalgos.casbasic;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.concurrent.CopyOnWriteArrayList;

import static org.assertj.core.api.Assertions.*;

class CASCountTest {

    @Test
    void whenIncrementCount() throws InterruptedException {
        CASCount counter = new CASCount();
        Thread first = new Thread(
                counter::increment
        );
        Thread second = new Thread(
                counter::increment
        );
        first.start();
        second.start();
        first.join();
        second.join();
        assertThat(counter.get()).isEqualTo(2);
    }

    @Test
    void whenIncrementCount20Times() throws InterruptedException {
        CopyOnWriteArrayList<Integer> buffer = new CopyOnWriteArrayList<>();
        CASCount counter = new CASCount();
        Thread first = new Thread(
                () -> {
                    for (int i = 0; i < 10; i++) {
                        counter.increment();
                        buffer.add(counter.get());
                    }
                }
        );
        Thread second = new Thread(
                () -> {
                    for (int i = 0; i < 10; i++) {
                        counter.increment();
                        buffer.add(counter.get());
                    }
                }
        );
        first.start();
        first.join();
        second.start();
        second.join();
        assertThat(buffer).isEqualTo(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10,
                11, 12, 13, 14, 15, 16, 17, 18, 19, 20));
    }
}