package ru.job4j.wait;

import org.junit.jupiter.api.Test;

class SimpleBlockingQueueTest {

    @Test
    void whenTest() throws InterruptedException {
        SimpleBlockingQueue<Integer> simpleBlockingQueue = new SimpleBlockingQueue<>(10);
        Thread producer = new Thread(
                () -> {
                    for (int i = 0; i <= 10; i++) {
                        System.out.println("add " + i);
                        try {
                            simpleBlockingQueue.offer(i);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                }
        );

        Thread consumer = new Thread(
                () -> {
                    for (int i = 0; i <= 10; i++) {
                        try {
                            System.out.println("poll: " + simpleBlockingQueue.poll());
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                }
        );
        consumer.start();
        producer.start();
        producer.join();
        consumer.join();
    }
}