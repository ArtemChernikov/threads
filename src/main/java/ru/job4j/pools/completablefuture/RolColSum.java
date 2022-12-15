package ru.job4j.pools.completablefuture;

/**
 * @author Artem Chernikov
 * @version 1.0
 * @since 15.12.2022
 */
public class RolColSum {
    public static class Sums {
        private int rowSum;
        private int colSum;

        public int getRowSum() {
            return rowSum;
        }

        public void setRowSum(int rowSum) {
            this.rowSum = rowSum;
        }

        public int getColSum() {
            return colSum;
        }

        public void setColSum(int colSum) {
            this.colSum = colSum;
        }
    }

    public static Sums[] sum(int[][] matrix) {
        int rowSum = 0;
        int colSum = 0;
        Sums sums = new Sums();
        for (int i = 0; i < matrix.length; i++) {

            colSum += matrix[i][0];
            for (int j = 0; j < matrix[i].length; j++) {
                rowSum += matrix[i][j];
            }
        }
        sums.setColSum(colSum);
        sums.setRowSum(rowSum);
        return sums;
    }

    public static Sums[] asyncSum(int[][] matrix) {

    }
}
