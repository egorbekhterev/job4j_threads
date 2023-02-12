package ru.job4j.pool.completablefuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RolColSum {

    /** Подсчет суммы для строки и столбца.
     * @param matrix квадратная матрица.
     * @param pointer указатель на i-ую строку и столбец.
     * @return объект Sums, с суммой значения для i-ых строки и столбца.*/
    private static Sums bypass(int[][] matrix, int pointer) {
        int rowSum = 0;
        int colSum = 0;
        for (int j = 0; j < matrix.length; j++) {
            rowSum += matrix[pointer][j];
            colSum += matrix[j][pointer];
        }
        return new Sums(rowSum, colSum);
    }

    /** Последовательный подсчет суммы по строкам и столбцам квадратной матрицы.
     * @param matrix квадратная матрица.
     * @return массив сумм для всех столбцов и строк матрицы.*/
    public static Sums[] sum(int[][] matrix) {
        Sums[] sums = new Sums[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            sums[i] = bypass(matrix, i);
        }
        return sums;
    }

    /** Асинхронный подсчет суммы строки и столбца.
     * @param matrix квадратная матрица.
     * @param pointer указатель на i-ую строку и столбец.
     * @return возвращает результат асинхронной задачи.*/
    private static CompletableFuture<Sums> returnSum(int[][] matrix, int pointer) {
        return CompletableFuture.supplyAsync(() -> bypass(matrix, pointer));
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
