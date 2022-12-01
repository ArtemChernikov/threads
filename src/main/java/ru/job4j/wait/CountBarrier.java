package ru.job4j.wait;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

/**
 * Класс, который блокирует выполнение по условию счетчика
 *
 * @author Artem Chernikov
 * @version 1.0
 * @since 01.12.2022
 */
@ThreadSafe
public class CountBarrier {
    /**
     * Поле монитор
     */
    private final Object monitor = this;
    /**
     * Поле количество вызовов метода {@link CountBarrier#count()}.
     */
    private final int total;
    /**
     * Поле счетчик
     */
    @GuardedBy("monitor")
    private int count = 0;

    public CountBarrier(final int total) {
        this.total = total;
    }

    /**
     * Метод используется для увеличения счетчика {@link CountBarrier#count},
     * также будит все нити, так как состояние программы поменялось
     */
    public void count() {
        synchronized (monitor) {
            count++;
            monitor.notifyAll();
        }
    }

    /**
     * Метод используется для ожидания изменений счетчика, пока он не будет >= {@link CountBarrier#total}
     */
    public void await() {
        synchronized (monitor) {
            while (count < total) {
                try {
                    monitor.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    public static void main(String[] args) {
        CountBarrier countBarrier = new CountBarrier(5);
        Thread thread1 = new Thread(
                () -> {
                    System.out.println(Thread.currentThread().getName() + " started");
                    for (int i = 0; i <= 10; i++) {
                        try {
                            Thread.sleep(1000);
                            countBarrier.count();
                            System.out.println("\rcount " + i);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }

                    }
                },
                "Thread1"
        );
        Thread thread2 = new Thread(
                () -> {
                    countBarrier.await();
                    System.out.println(Thread.currentThread().getName() + " started");
                },
                "Thread2"
        );
        thread2.start();
        thread1.start();
    }
}
