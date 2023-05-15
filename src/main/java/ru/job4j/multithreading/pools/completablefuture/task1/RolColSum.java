package ru.job4j.multithreading.pools.completablefuture.task1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * Данный класс описывает решение
 * задачи, в рамках которой нужно
 * подсчитать суммы по строкам и
 * столбцам квадратной матрицы.
 */
public class RolColSum {

    private static final Logger LOG = LoggerFactory.getLogger(RolColSum.class.getName());

    /**
     * Данный метод последовательно подсчитывает
     * суммы по строкам и столбцам квадратной матрицы.
     */
    public static Sums[] sum(int[][] matrix) {
        return RolColSum.handle(matrix);
    }

    /**
     * Данный метод в отдельном потоке
     * подсчитывает суммы значений по
     * строкам и столбцам квадратной матрицы.
     *
     * @param matrix матрица, у которой и нужно
     *               всё подсчитать.
     */
    public static Sums[] asyncSum(int[][] matrix) throws ExecutionException, InterruptedException {
        return CompletableFuture.supplyAsync(
                () -> handle(matrix)
        ).get();
    }

    /**
     * Данный метод подсчитывает суммы по строкам и
     * столбцам квадратной матрицы.
     *
     * <p>Его мы вынесли отдельно,
     * т.к. он используется без изменений в
     * методах {@link RolColSum#sum} и
     * {@link RolColSum#asyncSum}.
     * Именно здесь собрана вся логика.
     *
     * <p>Но данный метод делает это
     * последовательно!
     *
     * <p>1.Создаем результирующую матрицу,
     * которую будем наполнять. Создаем
     * переменную для временного хранения
     * суммы (либо значений строки, либо
     * значений столбца).
     * 2.Проходим по матрице и
     * записываем во временное хранилище
     * значение суммы значений строки и столбца.
     * 3.Начинаем заполнять результирующий
     * массив. Создаем объект {@link Sums}
     * и заполняем поле суммы по строке и
     * поле суммы по столбцу.
     * 4.Обнуляем временные переменные.
     * 5.Повторяем пп.2, 3, 4 пока не
     * закончится матрица.
     */
    private static Sums[] handle(int[][] matrix) {
        LOG.info("Processing in thread: {}", Thread.currentThread().getName());
        int n = matrix.length;
        int tempRowSum = 0;
        int tempColSum = 0;
        Sums[] result = new Sums[n];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                tempRowSum += matrix[i][j];
                tempColSum += matrix[j][i];
            }
            result[i] = new Sums(tempRowSum, tempColSum);
            LOG.info("Intermediate result: {}", Arrays.toString(result));
            tempRowSum = 0;
            tempColSum = 0;
        }
        return result;
    }
}
