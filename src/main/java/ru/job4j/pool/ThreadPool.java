package ru.job4j.pool;

import ru.job4j.wait.SimpleBlockingQueue;

import java.util.LinkedList;
import java.util.List;

/**
 * Класс демонстрирует модель пула потоков, котрые можно переиспользовать
 * для выполнения различных задач
 * Используется класс {@link SimpleBlockingQueue}
 *
 * @author Artem Chernikov
 * @version 1.0
 * @since 08.12.2022
 */
public class ThreadPool {
    private final List<Thread> threads = new LinkedList<>();
    private final SimpleBlockingQueue<Runnable> tasks = new SimpleBlockingQueue<>(10);
    private final int threadsSize = Runtime.getRuntime().availableProcessors();

    public ThreadPool() {
        for (int i = 0; i < threadsSize; i++) {
            threads.add(new Thread(
                    () -> {
                        try {
                                tasks.poll().run();
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
            ));
        }
        threads.forEach(Thread::start);
    }

    public synchronized void work(Runnable job) throws InterruptedException {
        tasks.offer(job);
    }

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
