package ru.job4j.nonblocking;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicReference;

@ThreadSafe
public class CASCount {

    private final AtomicReference<Integer> count = new AtomicReference<>(0);

    public void increment() {
        int counter;
        do {
            counter = count.get();
        } while (!count.compareAndSet(counter, counter + 1));
    }

    public int get() {
        return count.get();
    }
}
