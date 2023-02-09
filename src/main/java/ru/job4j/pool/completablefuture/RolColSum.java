package ru.job4j.pool.completablefuture;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RolColSum {
    public static class Sums {
        private final int rowSum;
        private final int colSum;

        public Sums(int rowSum, int colSum) {
            this.rowSum = rowSum;
            this.colSum = colSum;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Sums sums = (Sums) o;
            return rowSum == sums.rowSum && colSum == sums.colSum;
        }

        @Override
        public int hashCode() {
            return Objects.hash(rowSum, colSum);
        }

        @Override
        public String toString() {
            return "Sums{"
                    + "rowSum=" + rowSum
                    + ", colSum=" + colSum
                    + '}';
        }
    }

    /** Последовательный подсчет суммы по строкам и столбцам квадратной матрицы.
     * @param matrix квадратная матрица.
     * @return массив сумм для всех столбцов и строк матрицы.*/
    public static Sums[] sum(int[][] matrix) {
        Sums[] sums = new Sums[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            int rowSum = 0;
            int colSum = 0;
            for (int j = 0; j < matrix[i].length; j++) {
                rowSum += matrix[i][j];
                colSum += matrix[j][i];
            }
            sums[i] = new Sums(rowSum, colSum);
        }
        return sums;
    }

    /** Асинхронный подсчет суммы строки и столбца.
     * @param matrix квадратная матрица.
     * @param pointer указатель на i-ую строку и столбец.
     * @return возвращает результат асинхронной задачи.*/
    private static CompletableFuture<Sums> returnSum(int[][] matrix, int pointer) {
        return CompletableFuture.supplyAsync(() -> {
            int rowSum = 0;
            int colSum = 0;
            for (int i = 0; i < matrix.length; i++) {
                rowSum += matrix[pointer][i];
                colSum += matrix[i][pointer];
            }
            return new Sums(rowSum, colSum);
        });
    }

    /** Асинхронный подсчет суммы по строкам и столбцам квадратной матрицы.
     * @param matrix квадратная матрица.
     * @return массив сумм для всех столбцов и строк матрицы*/
    public static Sums[] asyncSum(int[][] matrix) throws ExecutionException, InterruptedException {
        Sums[] sums = new Sums[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            sums[i] = returnSum(matrix, i).get();
        }
        return sums;
    }
}
