package ru.job4j.pools.completablefuture;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.job4j.pools.completablefuture.RolColSum.*;

class RolColSumTest {

    @Test
    void whenSum() {
        int[][] matrix = {{1, 14, 10}, {1, 22, 33}, {11, 2, 3}};
        Sums[] sums = sum(matrix);
        Sums[] expected = {
                new Sums(25, 13),
                new Sums(56, 38),
                new Sums(16, 46)
        };
        assertThat(sums).isEqualTo(expected);
    }

    @Test
    public void whenAsyncSum() {
        int[][] array = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        Sums[] sums = asyncSum(array);
        Sums[] expected = new Sums[]{
                new Sums(6, 12),
                new Sums(15, 15),
                new Sums(24, 18)
        };
        assertThat(sums).isEqualTo(expected);
    }
}