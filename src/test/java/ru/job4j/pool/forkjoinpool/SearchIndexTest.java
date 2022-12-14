package ru.job4j.pool.forkjoinpool;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SearchIndexTest {

    @Test
    void whenLineSearch() {
        Integer[] array = {1, 10, 15, 2, 2, 3, 4};
        SearchIndex<Integer> searchIndex = new SearchIndex<>(array, 0, array.length, 15);
        assertThat(searchIndex.search()).isEqualTo(15);
    }

    @Test
    void whenRecursiveSearchInteger() {
        Integer[] array = {1, 10, 15, 2, 2, 3, 4, 10, 20, 33, 44, 55, 25};
        SearchIndex<Integer> searchIndex = new SearchIndex<>(array, 0, array.length, 15);
        assertThat(searchIndex.search()).isEqualTo(15);
    }

    @Test
    void whenRecursiveSearchString() {
        String[] array = {"1", "10", "15", "2", "2", "3", "4", "10", "20", "33", "44", "55", "25"};
        SearchIndex<String> searchIndex = new SearchIndex<>(array, 0, array.length, "15");
        assertThat(searchIndex.search()).isEqualTo("15");
    }

    @Test
    void whenLineSearchNotFoundElement() {
        Integer[] array = {1, 10, 15, 2, 2, 3, 4};
        SearchIndex<Integer> searchIndex = new SearchIndex<>(array, 0, array.length, 100);
        assertThat(searchIndex.search()).isNull();
    }

    @Test
    void whenRecursiveSearchIntegerNotFoundElement() {
        Integer[] array = {1, 10, 15, 2, 2, 3, 4, 10, 20, 33, 44, 55, 25};
        SearchIndex<Integer> searchIndex = new SearchIndex<>(array, 0, array.length, 100);
        assertThat(searchIndex.search()).isNull();
    }
}