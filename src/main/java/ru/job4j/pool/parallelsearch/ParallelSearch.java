package ru.job4j.pool.parallelsearch;

import java.util.concurrent.RecursiveTask;

public class ParallelSearch<T> extends RecursiveTask<Integer> {
    private final T[] array;
    private final int firstElement;
    private final int lastElement;
    private final T element;

    public ParallelSearch(T[] array, int firstElement, int lastElement, T element) {
        this.array = array;
        this.firstElement = firstElement;
        this.lastElement = lastElement;
        this.element = element;
    }

    private int linearSearch() {
        int rsl = -1;
        for (int i = firstElement; i <= lastElement; i++) {
            if (element.equals(array[i])) {
                rsl = i;
                break;
            }
        }
        return rsl;
    }

    @Override
    protected Integer compute() {
        if (lastElement - firstElement < 10) {
            return linearSearch();
        }
        int mid = (lastElement + firstElement) / 2;
        ParallelSearch<T> leftPart = new ParallelSearch<>(array, firstElement, mid, element);
        ParallelSearch<T> rightPart = new ParallelSearch<>(array, mid + 1, lastElement, element);
        leftPart.fork();
        rightPart.fork();
        var left = leftPart.join();
        var right = rightPart.join();
        return Math.max(left, right);
    }
}
