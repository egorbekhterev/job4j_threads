package ru.job4j.pool.parallelsearch;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ForkJoinPool;

import static org.assertj.core.api.Assertions.*;

class ParallelSearchTest {

    private final ForkJoinPool forkJoinPool = new ForkJoinPool();

    @Test
    void whenIntLong() {
        Integer[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23,
                24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38};
        var rsl = forkJoinPool.invoke(
                new ParallelSearch<>(array, 0, array.length - 1, 30));
        assertThat(rsl).isEqualTo(29);
    }

    @Test
    void whenString() {
        String[] array = {"Артем", "Борис", "Владимир", "Григорий", "Денис"};
        var rsl = forkJoinPool.invoke(
                new ParallelSearch<>(array, 0, array.length - 1, "Григорий"));
        assertThat(rsl).isEqualTo(3);
    }

    @Test
    void whenIntShort() {
        Integer[] array = {1, 2, 3, 4, 5, 6, 7};
        var rsl = forkJoinPool.invoke(
                new ParallelSearch<>(array, 0, array.length - 1, 5));
        assertThat(rsl).isEqualTo(4);
    }

    @Test
    void whenElementNotFound() {
        Integer[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23,
                24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38};
        var rsl = forkJoinPool.invoke(
                new ParallelSearch<>(array, 0, array.length - 1, 77));
        assertThat(rsl).isEqualTo(-1);
    }
}
