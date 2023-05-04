package ru.job4j.multithreading.pools.forkjoinpool;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;


class ParallelSearchIndexTest {

    @Test
    void whenSearchInArrayOfStrings() {
        String[] arrayString = {"Hello", "World", "Hell", "War", "O", "LD", "FJP", "Hard", "Task", "Keyboard", "Mouse"};
        int index = ParallelSearchIndex.search(arrayString, "Hell");
        assertThat(index).isEqualTo(2);
    }

    @Test
    void whenSearchInArrayOfInts() {
        Integer[] array = {1, 5, 9, 8, 99, 2012, 18, 17, 4, 63, 12, 13, 7};
        int index = ParallelSearchIndex.search(array, 8);
        assertThat(index).isEqualTo(3);
    }

    @Test
    void whenMassiveHasSmallSize() {
        String[] array = {"Hi", "my",  "name", "is", "Consta", "and", "I", "am", "learning", "Java"};
        int index = ParallelSearchIndex.search(array, "Java");
        assertThat(index).isEqualTo(9);
    }

    @Test
    void whenMassiveHasBigSize() {
        Integer[] array = {4, 8, 15, 16, 23, 42, 2, 1, 4, 3, 7, 5, 8, 6};
        int index = ParallelSearchIndex.search(array, 7);
        assertThat(index).isEqualTo(10);
    }

    @Test
    void whenElementNotFoundThenNegativeResult() {
        Integer[] array = {4, 8, 15, 16, 23, 42, 2, 1, 4, 3, 7, 5, 8, 6};
        int index = ParallelSearchIndex.search(array, 99);
        assertThat(index).isEqualTo(-1);
    }
}