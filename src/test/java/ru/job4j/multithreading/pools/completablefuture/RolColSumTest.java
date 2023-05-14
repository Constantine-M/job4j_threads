package ru.job4j.multithreading.pools.completablefuture;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RolColSumTest {

    @Test
    void whenSumSync() {
        int[][] matrix = new int[][] {
                {5, 7, 2},
                {6, 0, 3},
                {2, 5, 4}
        };
        RolColSum.Sums[] expected = {
                new RolColSum.Sums(14, 13),
                new RolColSum.Sums(9, 12),
                new RolColSum.Sums(11, 9)
        };
        RolColSum.Sums[] result = RolColSum.sum(matrix);
        assertThat(result).isEqualTo(expected);
    }

    @Test
    void asyncSum() {
        int[][] matrix = new int[][] {
                {3, 4, 1},
                {9, 1, 6},
                {5, 1, 3}
        };
        RolColSum.Sums[] expected = {
                new RolColSum.Sums(8, 17),
                new RolColSum.Sums(16, 6),
                new RolColSum.Sums(9, 10)
        };
        RolColSum.Sums[] result = RolColSum.asyncSum(matrix);
        assertThat(result).isEqualTo(expected);
    }
}