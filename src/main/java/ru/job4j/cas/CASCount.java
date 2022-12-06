package ru.job4j.cas;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicReference;

/**
 * Класс описывает модель неблокирующего счетчика при помощи CAS операций
 *
 * @author Artem Chernikov
 * @version 1.0
 * @since 06.12.2022
 */
@ThreadSafe
public class CASCount {
    private final AtomicReference<Integer> count = new AtomicReference<>(0);

    public void increment() {
        Integer oldCount;
        int newCount;
        do {
            oldCount = count.get();
            newCount = oldCount + 1;
        } while (!count.compareAndSet(oldCount, newCount));
    }

    public int get() {
        return count.get();
    }
}
