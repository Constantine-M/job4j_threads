package ru.job4j.multithreading.nonblockalgos.concurrenthashmap;

import org.junit.Ignore;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Disabled
class CacheTest {

    /**
     * Так как тест последовательный,
     * то здесь тоже последний выиграл.
     */
    @Test
    void whenLastWin() {
        Base startBase = new Base(1, 0);
        Cache cache = new Cache();
        cache.add(startBase);
        Base first = cache.get(1);
        first.setName("First");
        cache.update(first);
        Base second = cache.get(1);
        second.setName("Last");
        cache.update(second);
        assertThat(cache.get(1).getName()).isEqualTo("Last");
    }

    @Ignore
    @Test
    void whenVersionUpdate() {
        Base startBase = new Base(1, 0);
        Cache cache = new Cache();
        cache.add(startBase);
        Base first = cache.get(1);
        first.setName("First");
        cache.update(first);
        Base second = cache.get(1);
        assertThat(first.getVersion()).isEqualTo(1);
    }
}