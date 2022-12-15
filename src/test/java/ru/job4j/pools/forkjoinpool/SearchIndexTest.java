package ru.job4j.pools.forkjoinpool;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.job4j.pools.forkjoinpool.SearchIndex.search;

class SearchIndexTest {

    @Test
    void whenLineSearch() {
        Integer[] array = {1, 10, 15, 2, 2, 3, 4};
        assertThat(search(array, 15)).isEqualTo(2);
    }

    @Test
    void whenRecursiveSearchInteger() {
        Integer[] array = {1, 10, 15, 2, 2, 3, 4, 10, 20, 33, 44, 55, 25};
        assertThat(search(array, 20)).isEqualTo(8);
    }

    @Test
    void whenRecursiveSearchString() {
        String[] array = {"1", "10", "15", "2", "2", "3", "4", "10", "20", "33", "44", "55", "25"};
        assertThat(search(array, "15")).isEqualTo(2);
    }

    @Test
    void whenLineSearchNotFoundElement() {
        Integer[] array = {1, 10, 15, 2, 2, 3, 4};
        assertThat(search(array, 100)).isEqualTo(-1);
    }

    @Test
    void whenRecursiveSearchIntegerNotFoundElement() {
        Integer[] array = {1, 10, 15, 2, 2, 3, 4, 10, 20, 33, 44, 55, 25};
        assertThat(search(array, 100)).isEqualTo(-1);
    }
}