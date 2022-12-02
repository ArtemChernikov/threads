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
                        simpleBlockingQueue.offer(i);
                    }
                }
        );

        Thread consumer = new Thread(
                () -> {
                    for (int i = 0; i <= 10; i++) {
                        System.out.println("poll: " + simpleBlockingQueue.poll());
                    }
                }
        );
        consumer.start();
        producer.start();
        producer.join();
        consumer.join();
    }
}