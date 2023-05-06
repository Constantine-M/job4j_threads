package ru.job4j.multithreading.pools.forkjoinpool;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * Данный класс описывает параллельный
 * поиск индекса в массиве объектов.
 */
public class ParallelSearchIndex<T> extends RecursiveTask<Integer> {

    private final T[] array;

    /**
     * Данное поле описывает
     * элемент, индекс которого
     * мы ищем.
     */
    private final T element;

    private final int from;

    private final int to;

    public ParallelSearchIndex(T[] array, T element, int from, int to) {
        this.array = array;
        this.element = element;
        this.from = from;
        this.to = to;
    }

    /**
     * Данный метод рекурсивно производит
     * вычисления. Подробнее:
     * 1.Если кол-во индексов в массиве
     * <= 10, то находим индекс с помощью
     * линейного поиска (простой перебор).
     * 2.Если кол-во индексов в массиве
     * > 10, то дробим массив надвое,
     * т.е. создаем 2 объекта (задачи)
     * {@link ParallelSearchIndex} с
     * размером массива 1/2 (2 половинки).
     * 3.Далее вызываем метод
     * {@link ForkJoinTask#invokeAll}
     * и передаем туда 2 наших задачи.
     * Данный метод - это аналог метода
     * fork(), но, как сказал Алексей Шипилев,
     * работает умнее. По сути метод
     * {@link ForkJoinTask#fork()} организует
     * асинхронное выполнение новой
     * задачи. Это аналогично тому, что
     * мы запустили бы рекурсивный
     * метод еще раз.
     * 4.То есть в итоге имеем следующее:
     * пока кол-во индексов больше 10 -
     * дробим массив до тех пор, пока их
     * не станет меньше 10 или 10.
     * И тогда используем линейный поиск.
     * Здесь нет других поисков, только
     * линейный. Суть в дроблении на подзадачи.
     * 5.Метод {@link ForkJoinTask#join()}
     * ожидает завершения задачи и возвращает
     * результат её выполнения, но
     * во время ожидания поток не
     * блокируется, а может начать
     * выполнение других задач.
     * 6.Т.к. в одной из 2 нитей вероятно
     * имеется правильный ответ, а именно
     * индекс, то возвращаем макс число
     * из 2 нитей (точно положительное).
     *
     * @return индекс объекта, который ищем.
     */
    @Override
    protected Integer compute() {
        if ((to - from) <= 10) {
            return linearSearch(array, element);
        }
        int mid = ((to - from)) / 2;
        ParallelSearchIndex<T> ps1 = new ParallelSearchIndex<>(array, element, from, mid);
        ParallelSearchIndex<T> ps2 = new ParallelSearchIndex<>(array, element, mid + 1, to);
        ForkJoinTask.invokeAll(ps1, ps2);
        int res1 = ps2.join();
        int res2 = ps1.join();
        return Math.max(res1, res2);
    }

    /**
     * Данный метод осуществляет
     * линейный поиск индекса
     * объекта массива.
     *
     * <p>Мы не использовали Stream API,
     * т.к. с помощью stream мы пройдем
     * весь массив, а нам нужно пройти только
     * участок {from, to}.
     *
     * @param array массив объектов, в
     *              котором осуществляем поиск.
     * @param element элемент, который ищем.
     * @return индекс элемента, который ищем.
     */
    private int linearSearch(T[] array, T element) {
        int result = -1;
        for (int i = from; i <= to; i++) {
            if (array[i].equals(element)) {
                result = i;
                break;
            }
        }
        return result;
    }

    /**
     * Данный метод непосредственно
     * запускает поиск.
     * @param array массив объектов, в котором осуществляем поиск.
     * @param element объект, который ищем.
     * @return результат выполнения метода compute().
     */
    public static <T> int search(T[] array, Object element) {
        ForkJoinPool fjp = new ForkJoinPool();
        return fjp.invoke(new ParallelSearchIndex<>(array, element, 0, array.length - 1));
    }
}
