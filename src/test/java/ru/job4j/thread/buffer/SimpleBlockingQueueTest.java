package ru.job4j.thread.buffer;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.*;

class SimpleBlockingQueueTest {

    @Test
    void whenNormalQueue() throws InterruptedException {
        final int limit = 5;
        final SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(limit);
        Thread producer = new Thread(() -> {
            for (int i = 0; i < limit; i++) {
                try {
                    queue.offer(i);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        List<Integer> list = new ArrayList<>();
        Thread consumer = new Thread(
                () -> {
                    for (int i = 0; i < limit; i++) {
                        try {
                            list.add(queue.poll());
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                }
        );
        producer.start();
        consumer.start();
        producer.join();
        consumer.join();
        assertThat(list).isEqualTo(List.of(0, 1, 2, 3, 4));
    }

    @Test
    public void whenFetchAllThenGetIt() throws InterruptedException {
        final CopyOnWriteArrayList<Integer> buffer = new CopyOnWriteArrayList<>();
        final SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(5);
        Thread producer = new Thread(
                () -> IntStream.range(0, 5).forEach(
                        value -> {
                            try {
                                queue.offer(value);
                            } catch (InterruptedException e) {
                                Thread.currentThread().interrupt();
                            }
                        }
                )
        );
        producer.start();
        Thread consumer = new Thread(
                () -> {
                    while (!queue.isEmpty() || !Thread.currentThread().isInterrupted()) {
                        try {
                            buffer.add(queue.poll());
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                }
        );
        consumer.start();
        producer.join();
        consumer.interrupt();
        consumer.join();
        assertThat(buffer).isEqualTo(Arrays.asList(0, 1, 2, 3, 4));
    }
}
