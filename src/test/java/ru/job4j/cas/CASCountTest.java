package ru.job4j.cas;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CASCountTest {
    @Test
    public void whenIncrement() throws InterruptedException {
        CASCount casCount = new CASCount();
        Thread first = new Thread(
                casCount::increment
        );
        Thread second = new Thread(
                casCount::increment
        );
        first.start();
        second.start();
        first.join();
        second.join();
        assertThat(casCount.get()).isEqualTo(2);
    }

}