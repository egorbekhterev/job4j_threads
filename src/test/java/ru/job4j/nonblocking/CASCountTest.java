package ru.job4j.nonblocking;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class CASCountTest {
    private final CASCount casCount = new CASCount();

    @Test
    void whenIncrementedByOne() {
        casCount.increment();
        assertThat(casCount.get()).isEqualTo(1);
    }

    @Test
    void whenParallelIncremented() throws InterruptedException {
       final Thread thread1 = new Thread(
               () -> {
                   for (int i = 0; i < 5; i++) {
                       casCount.increment();
                   }
               }
       );
       final Thread thread2 = new Thread(
               () -> {
                   for (int i = 0; i < 5; i++) {
                       casCount.increment();
                   }
               }
       );
       thread1.start();
       thread2.start();
       thread1.join();
       thread2.join();
       assertThat(casCount.get()).isEqualTo(10);
    }
}
