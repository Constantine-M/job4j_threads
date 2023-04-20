package ru.job4j.multithreading.nonblockalgos.concurrenthashmap;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

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

    @Test
    void whenChangeNameThenIncrementVersion() {
        Cache cache = new Cache();
        cache.add(new Base(1, 0));
        Base base = cache.get(1);
        base.setName("First");
        cache.update(base);
        Base updatedBase = cache.get(1);
        assertThat(updatedBase.getVersion()).isEqualTo(1);
    }

    @Test
    void whenDeleteBaseWithId1ThenCacheNull() {
        Cache cache = new Cache();
        cache.add(new Base(1, 0));
        Base base = cache.get(1);
        base.setName("First");
        cache.update(base);
        Base updatedBase = cache.get(1);
        cache.delete(updatedBase);
        assertThat(cache.get(1)).isNull();
    }
}