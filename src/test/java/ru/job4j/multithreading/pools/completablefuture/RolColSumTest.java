package ru.job4j.multithreading.pools.completablefuture;

import org.junit.jupiter.api.Test;
import ru.job4j.multithreading.pools.completablefuture.task1.RolColSum;
import ru.job4j.multithreading.pools.completablefuture.task1.Sums;

import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.assertThat;

class RolColSumTest {

    @Test
    void whenSumSync() {
        int[][] matrix = new int[][] {
                {5, 7, 2},
                {6, 0, 3},
                {2, 5, 4}
        };
        Sums[] expected = {
                new Sums(14, 13),
                new Sums(9, 12),
                new Sums(11, 9)
        };
        Sums[] result = RolColSum.sum(matrix);
        assertThat(result).isEqualTo(expected);
    }

    @Test
    void asyncSum() throws ExecutionException, InterruptedException {
        int[][] matrix = new int[][] {
                {3, 4, 1},
                {9, 1, 6},
                {5, 1, 3}
        };
        Sums[] expected = {
                new Sums(8, 17),
                new Sums(16, 6),
                new Sums(9, 10)
        };
        Sums[] result = RolColSum.asyncSum(matrix);
        assertThat(result).isEqualTo(expected);
    }
}