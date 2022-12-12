package ru.job4j.pool.threadpool;

import ru.job4j.wait.SimpleBlockingQueue;

import java.util.LinkedList;
import java.util.List;

/**
 * Класс демонстрирует модель пула потоков, котрые можно
 * переиспользовать для выполнения различных задач
 * Используется класс {@link SimpleBlockingQueue}
 *
 * @author Artem Chernikov
 * @version 1.0
 * @since 08.12.2022
 */
public class ThreadPool {
    /**
     * Поле число ядер устройства
     */
    private final int cores = Runtime.getRuntime().availableProcessors();
    /**
     * Поле список потоков
     */
    private final List<Thread> threads = new LinkedList<>();
    /**
     * Поле блокирующая очередь с задачами для потоков
     */
    private final SimpleBlockingQueue<Runnable> tasks = new SimpleBlockingQueue<>(cores);

    /**
     * Конструктор, при создании объекта {@link ThreadPool} создается
     * определенное количество потоков, равное числу ядер устройства {@link ThreadPool#cores},
     * где у каждого потока в методе {@link Thread#run()} определено действие:
     * "Вытащить задачу для выполнения из блокирующей очереди и запусить ее",
     * далее мы:
     * 1) Запускаем поток к исполнению
     * 2) Добавляем поток в список потоков {@link ThreadPool#threads}
     */
    public ThreadPool() {
        for (int i = 0; i < cores; i++) {
            Thread thread = new Thread(
                    () -> {
                        try {
                            tasks.poll().run();
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
            );
            thread.start();
            threads.add(thread);
        }
    }

    /**
     * Метод используется для добавления задач для потоков в блокирующую очередь
     *
     * @param job - задача
     * @throws InterruptedException - может выбросить {@link InterruptedException}
     */
    public synchronized void work(Runnable job) throws InterruptedException {
        tasks.offer(job);
    }

    /**
     * Метод используется для завершения всех выполняемых потоками задач
     */
    public void shutdown() {
        threads.forEach(Thread::interrupt);
    }

    public static void main(String[] args) throws InterruptedException {
        ThreadPool threadPool = new ThreadPool();
        threadPool.work(() -> System.out.println("first"));
        threadPool.work(() -> System.out.println("second"));
        threadPool.work(() -> System.out.println("third"));
        threadPool.shutdown();
    }
}
