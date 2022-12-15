package ru.job4j.pools.completablefuture;

import java.util.concurrent.CompletableFuture;

/**
 * @author Artem Chernikov
 * @version 1.0
 * @since 15.12.2022
 */
public class RolColSum {

    public static Sums[] sum(int[][] matrix) {
        Sums[] sums = new Sums[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            sums[i] = getSum(matrix, i);
        }
        return sums;
    }

    public static Sums[] asyncSum(int[][] matrix) {
        Sums[] sums = new Sums[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            int finalI = i;
            CompletableFuture.runAsync(() -> sums[finalI] = getSum(matrix, finalI));
        }
        return sums;
    }

    private static Sums getSum(int[][] matrix, int index) {
        int rowSum = 0;
        int colSum = 0;
        for (int i = 0; i < matrix.length; i++) {
            rowSum += matrix[index][i];
            colSum += matrix[i][index];
        }
        return new Sums(rowSum, colSum);
    }
}
