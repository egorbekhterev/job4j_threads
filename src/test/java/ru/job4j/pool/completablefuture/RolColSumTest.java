package ru.job4j.pool.completablefuture;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.*;

class RolColSumTest {

    private final int[][] matrix = new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};

    @Test
    void whenSequentialSum() {
        Sums[] expected = {
                new Sums(6, 12),
                new Sums(15, 15),
                new Sums(24, 18)
        };
        assertThat(RolColSum.sum(matrix)).isEqualTo(expected);
    }

    @Test
    void whenAsyncSum() throws ExecutionException, InterruptedException {
        Sums[] expected = {
                new Sums(6, 12),
                new Sums(15, 15),
                new Sums(24, 18)
        };
        assertThat(RolColSum.asyncSum(matrix)).isEqualTo(expected);
    }
}
