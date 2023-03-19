package ru.job4j.basic.sync.sharedresources.jcip;

import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

public class CountTest {

    /**
     * Каждая нить будет дергать счетчик и
     * увеличивать его значение на единицу.
     * В конце теста мы проверяем, что наш
     * счетчик увеличился на 2.
     */
    @Test
    public void whenExecute2ThreadThen2() throws InterruptedException {
        var count = new Count();
        var first = new Thread(count::increment);
        var second = new Thread(count::increment);
        /* Запускаем нити. */
        first.start();
        second.start();
        /* Заставляем главную нить дождаться выполнения наших нитей. */
        first.join();
        second.join();
        /* Проверяем результат. */
        assertThat(count.get()).isEqualTo(2);
    }
}