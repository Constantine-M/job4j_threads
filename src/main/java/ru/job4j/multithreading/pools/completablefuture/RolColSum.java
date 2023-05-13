package ru.job4j.multithreading.pools.completablefuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

/**
 * Данный класс описывает решение
 * задачи, в рамках которой нужно
 * подсчитать суммы по строкам и
 * столбцам квадратной матрицы.
 */
public class RolColSum {

    private static final Logger LOG = LoggerFactory.getLogger(RolColSum.class.getName());

    /**
     * Данный внутренний класс
     * описывает суммы по строкам
     * и столбцам.
     */
    public static class Sums {

        private int rowSum;

        private int colSum;

        public Sums(int rowSum, int colSum) {
            this.rowSum = rowSum;
            this.colSum = colSum;
        }

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

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof Sums sums)) {
                return false;
            }
            return getRowSum() == sums.getRowSum() && getColSum() == sums.getColSum();
        }

        @Override
        public int hashCode() {
            return Objects.hash(getRowSum(), getColSum());
        }

        @Override
        public String
        toString() {
            return "Sums{"
                    + "rowSum=" + rowSum
                    + ", colSum=" + colSum
                    + '}';
        }
    }

    /**
     * Данный метод подсчитывает суммы по строкам и
     * столбцам квадратной матрицы.
     *
     * <p>Но данный метод делает это
     * последовательно!
     *
     * <p>1.Создаем результирующую матрицу,
     * которую будем наполнять. Создаем
     * переменную для временного хранения
     * суммы (либо значений строки, либо
     * значений столбца).
     * 2.Проходим сначала по строкам и
     * записываем во временное хранилище
     * значение суммы значений строки.
     * 3.Начинаем заполнять результирующий
     * массив. Создаем объект {@link Sums}
     * и заполняем поле суммы по строке.
     * Второе поле оставляем нулевым.
     * 4.Обнуляем временную переменную.
     * 5.Далее точно так же проходим
     * по столбцам. Перед проходом обнуляем
     * переменную, отвечающую за индекс
     * результирующего массива, т.к.
     * мы будем заново проходить по
     * результирующему массиву и заполнять
     * у объектов {@link Sums} второе поле,
     * которое описывает сумму по столбцам.
     */
    public static Sums[] sum(int[][] matrix) {
        int n = matrix.length;
        int temp = 0;
        Sums[] result = new Sums[n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                temp += matrix[i][j];
            }
            result[i] = new Sums(temp, 0);
            temp = 0;
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                temp += matrix[j][i];
            }
            result[i].setColSum(temp);
            temp = 0;
        }
        return result;
    }

    public static Sums[] asyncSum(int[][] matrix) {
        int n = matrix.length;
        Sums[] result = new Sums[n];
        for (int i = 0; i < n; i++) {
            result[i] = new Sums(0, 0);
        }
        CompletableFuture.allOf(RolColSum.asyncRowSum(result, matrix),
                                RolColSum.asyncColSum(result, matrix));
        return result;
    }

    /**
     * Данный метод в отдельном потоке
     * подсчитывает суммы значений по
     * строкам квадратной матрицы.
     *
     * <p>Чтобы корректно произвести debug
     * данного метода, следует (из личной
     * практики) добавить 2 breakpoint-а:
     * 1.на сам метод
     * 2.на строку внутри блока,
     * который выполняется в фоновом режиме.
     * Для обеих точек останова следует
     * тыкнуть ПКМ и выбрать "thread",
     * чтобы IDEA предложила тебе переключиться
     * на другую нить.
     *
     * @param result результирующий массив.
     * @param matrix матрица, у которой собсно
     *               и нужно подсчитать всё.
     */
    private static CompletableFuture<Void> asyncRowSum(Sums[] result, int[][] matrix) {
        return CompletableFuture.runAsync(
                () -> {
                    LOG.info("Sum of each row in thread: {}", Thread.currentThread().getName());
                    int temp = 0;
                    for (int i = 0; i < matrix.length; i++) {
                        for (int j = 0; j < matrix.length; j++) {
                            temp += matrix[i][j];
                        }
                        result[i].setRowSum(temp);
                        temp = 0;
                    }
                }
        );
    }

    private static CompletableFuture<Void> asyncColSum(Sums[] result, int[][] matrix) {
        return CompletableFuture.runAsync(
                () -> {
                    LOG.info("Sum of each column in thread: {}", Thread.currentThread().getName());
                    int temp = 0;
                    for (int i = 0; i < matrix.length; i++) {
                        for (int j = 0; j < matrix.length; j++) {
                            temp += matrix[j][i];
                        }
                        result[i].setColSum(temp);
                        temp = 0;
                    }
                }
        );
    }

    public static void main(String[] args) {
        int[][] matrix = new int[][] {
                {5, 7, 2},
                {6, 0, 3},
                {2, 5, 4}
        };
        RolColSum.Sums[] result = asyncSum(matrix);
        System.out.println(Arrays.toString(result));
    }
}
