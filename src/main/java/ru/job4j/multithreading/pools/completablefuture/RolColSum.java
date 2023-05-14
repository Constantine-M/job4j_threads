package ru.job4j.multithreading.pools.completablefuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Objects;
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
    public static Sums[] sum(int[][] matrix) {
        int n = matrix.length;
        int tempRowSum = 0;
        int tempColSum = 0;
        Sums[] result = new Sums[n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                tempRowSum += matrix[i][j];
                tempColSum += matrix[j][i];
            }
            result[i] = new Sums(tempRowSum, tempColSum);
            tempRowSum = 0;
            tempColSum = 0;
        }
        return result;
    }

    public static Sums[] asyncSum(int[][] matrix) {
        int n = matrix.length;
        Sums[] result = new Sums[n];
        LOG.info("Processing in thread before async method: {}", Thread.currentThread().getName());
        RolColSum.asyncRowAndColSum(result, matrix);
        LOG.info("Processing in thread after async method: {}", Thread.currentThread().getName());
        return result;
    }

    /**
     * Данный метод в отдельном потоке
     * подсчитывает суммы значений по
     * строкам и столбцам квадратной матрицы.
     *
     * Данный метод намеренно вынес отдельно,
     * чтобы акцентировать внимание на
     * отладке.
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
     * <p>Если у вас только один worker, то
     * IDEA не предлагает переключиться на него.
     * Она сделает это автоматически в свое время,
     * но только при условии, если ты правильно
     * расставишь breakpoint-ы. Так вот, в данном
     * случае я еще добавил breakpoint в методе
     * asyncSum() на строке, где мы вызываем
     * ассинхронный метод (asyncRowAndColSum()).
     *
     * @param result результирующий массив.
     * @param matrix матрица, у которой собсно
     *               и нужно подсчитать всё.
     */
    private static void asyncRowAndColSum(Sums[] result, int[][] matrix) {
        CompletableFuture.runAsync(
                () -> {
                    LOG.info("Processing in thread: {}", Thread.currentThread().getName());
                    int tempRowSum = 0;
                    int tempColSum = 0;
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
                }
        );
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        int[][] matrix = new int[][]{
                {5, 7, 2},
                {6, 0, 3},
                {2, 5, 4}
        };
        RolColSum.Sums[] result = new Sums[matrix.length];
        CompletableFuture<Sums[]> sums = CompletableFuture.supplyAsync(
                () -> {
                    LOG.info("Processing in thread: {}", Thread.currentThread().getName());
                    int tempRowSum = 0;
                    int tempColSum = 0;
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
        );
        System.out.println(Arrays.toString(sums.get()));
    }
}
