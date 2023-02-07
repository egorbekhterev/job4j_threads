package ru.job4j.pool;

import ru.job4j.thread.buffer.SimpleBlockingQueue;

import java.util.LinkedList;
import java.util.List;

/** Pool - хранилище для ресурсов, которое можно переиспользовать.*/

public class ThreadPool {
    /** Поле, определяющее количество ядер в системе. По совместительству - максимальное количество нитей.*/
    private final int size = Runtime.getRuntime().availableProcessors();
    /** Список нитей для хранения.*/
    private final List<Thread> threads = new LinkedList<>();
    /** tasks - Простейшая реализация блокирующей очереди.*/
    private final SimpleBlockingQueue<Runnable> tasks = new SimpleBlockingQueue<>(size);

    /** Конструктор инициализирует Pool. В каждую нить передается блокирующая очередь tasks.
     * В методе run получаем задачу из очереди tasks.*/
    public ThreadPool() {
        for (int i = 0; i < size; i++) {
            Thread thread = new Thread(() -> {
                while (!Thread.currentThread().isInterrupted()) {
                    try {
                        tasks.poll().run();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
            }});
            threads.add(thread);
            thread.start();
        }
    }

    /** Добавляет задачи в блокирующую очередь tasks.
     * @param job задача*/
    public void work(Runnable job) throws InterruptedException {
        tasks.offer(job);
    }

    /** Завершает все запущенные задачи.*/
    public void shutdown() {
        for (Thread thread : threads) {
            thread.interrupt();
        }
    }
}
