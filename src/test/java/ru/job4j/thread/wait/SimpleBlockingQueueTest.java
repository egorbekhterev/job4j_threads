package ru.job4j.thread.wait;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class SimpleBlockingQueueTest {

    @Test
    void when() throws InterruptedException {
        int limit = 5;
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(limit);
        Thread producer = new Thread(() -> {
            for (int i = 0; i < limit; i++) {
                queue.offer(i);
            }
        });
        List<Integer> list = new ArrayList<>();
        Thread consumer = new Thread(
                () -> {
                    for (int i = 0; i < limit; i++) {
                        list.add(queue.poll());
                    }
                }
        );
        producer.start();
        consumer.start();
        producer.join();
        consumer.join();
        assertThat(list).isEqualTo(List.of(0, 1, 2, 3, 4));
    }
}
