package ru.job4j.wait;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Класс описывает реализацию шаблона Producer Consumer с помощью собственной версии
 * bounded blocking queue (блокирующая очередь, ограниченная по размеру),
 * в данном шаблоне Producer помещает данные в очередь, а Consumer извлекает данные из очереди
 *
 * @author Artem Chernikov
 * @version 1.0
 * @since 01.12.2022
 */
@ThreadSafe
public class SimpleBlockingQueue<T> {
    @GuardedBy("this")
    private final Queue<T> queue = new LinkedList<>();
    private final int limit;

    public SimpleBlockingQueue(int limit) {
        this.limit = limit;
    }

    public synchronized void offer(T value) throws InterruptedException {
        while (queue.size() == limit) {
            wait();
        }
        queue.add(value);
        notifyAll();
    }

    public synchronized T poll() throws InterruptedException {
        while (queue.size() == 0) {
            wait();
        }
        T rsl = queue.poll();
        notifyAll();
        return rsl;
    }
}
