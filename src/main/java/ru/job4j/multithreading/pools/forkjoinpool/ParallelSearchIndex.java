package ru.job4j.multithreading.pools.forkjoinpool;

import java.util.Arrays;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.stream.IntStream;

/**
 * Данный класс описывает параллельный
 * поиск индекса в массиве объектов.
 */
public class ParallelSearchIndex<T> extends RecursiveTask<Integer> {

    private final T[] array;

    private final T element;

    public ParallelSearchIndex(T[] array, T element) {
        this.array = array;
        this.element = element;
    }

    @Override
    protected Integer compute() {
        validateArray(array);
        int index = -1;
        if (array.length <= 10) {
            index = linearSearch(array, element);
        }
        int mid = array.length / 2;
        ParallelSearchIndex<T> s1 = new ParallelSearchIndex<>(Arrays.copyOfRange(array, 0, mid), element);
        ParallelSearchIndex<T> s2 = new ParallelSearchIndex<>(Arrays.copyOfRange(array, mid + 1, array.length - 1), element);
        ForkJoinTask.invokeAll(s1, s2);
        int res1 = s2.join();
        int res2 = s1.join();
        /*Далее нужно все как-то объединить и где-то написать метод поиска индекса*/
        if (index == -1) {
            throw new IllegalArgumentException("Element not found.");
        }
        return index;
    }

    /**
     * Данный метод осуществляет
     * линейный поиск индекса
     * объекта массива.

     * @param array массив объектов, в
     *              котором осуществляем поиск.
     * @param element элемент, который ищем.
     * @return индекс элемента, который ищем.
     */
    private int linearSearch(T[] array, T element) {
        return IntStream.range(0, array.length)
                .filter(index -> array[index].equals(element))
                .findFirst()
                .orElse(-1);
    }

    /**
     * Данный метод проводит валидацию
     * массива. Возможно, здесь появится
     * что-нибудь еще. Поэтому я и вынес
     * это в отдельный метод.
     * @param array валидируемый массив.
     */
    private void validateArray(T[] array) {
        if (array.length == 0) {
            throw new IllegalArgumentException("Array is empty!");
        }
    }
}
