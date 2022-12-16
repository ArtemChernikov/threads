package ru.job4j.pools.completablefuture;

import java.util.concurrent.CompletableFuture;

/**
 * Класс описываеет алгоритмы для подсчета сумм колонн и строк в матрице
 *
 * @author Artem Chernikov
 * @version 1.0
 * @since 15.12.2022
 */
public class RolColSum {
    /**
     * Метод используется для подсчета сумм колонн и строк в матрице по каждому индексу
     *
     * @param matrix - матрица
     * @return - возвращает массив с объектами {@link Sums}
     */
    public static Sums[] sum(int[][] matrix) {
        Sums[] sums = new Sums[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            sums[i] = getSum(matrix, i);
        }
        return sums;
    }

    /**
     * Асинхронный метод используется для подсчета сумм колонн и строк в матрице по каждому индексу
     *
     * @param matrix - матрица
     * @return - возвращает массив с объектами {@link Sums}
     */
    public static Sums[] asyncSum(int[][] matrix) {
        Sums[] sums = new Sums[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            int finalI = i;
            CompletableFuture.runAsync(() -> sums[finalI] = getSum(matrix, finalI));
        }
        return sums;
    }

    /**
     * Метод используется для подсчета суммы в колонне и строке матрицы по индексу
     *
     * @param matrix - матрциа
     * @param index  - индекс
     * @return - возвращает объект {@link Sums}
     */
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
