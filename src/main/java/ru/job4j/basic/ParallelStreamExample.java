package ru.job4j.basic;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Данный класс является
 * примером и показывает, что
 * Stream API поддерживает
 * многопоточность.
 * Чтобы создать из потока
 * параллельный поток, необходимо
 * воспользоваться методом
 * {@link List#parallelStream()}.
 * Данный метод вернет параллельный
 * поток, если это возможно.
 * Здесь же мы используем метод
 * {@link Stream#sequential()},
 * чтобы вернуть последовательный
 * поток. И также размышляем о
 * том, какие методы сохраняют
 * порядок чисел (к примеру), а какие нет.
 * {@link Stream#forEach} - не
 * вернет исходную последовательность.
 * А к примеру, метод
 * {@link Stream#forEachOrdered}
 * сможет вернуть.
 */
public class ParallelStreamExample {
    public static void main(String[] args) {
        String ls = System.lineSeparator();
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
        Stream<Integer> stream = list.parallelStream();
        System.out.println("Is the stream parallel? - " + stream.isParallel());
        Optional<Integer> multiplication = stream.reduce((a, b) -> a * b);
        System.out.println("Result of multiplication: " + multiplication.get() + ls);
        IntStream parallel = IntStream.range(1, 100).parallel();
        System.out.println("Is the intStream parallel? - " + parallel.isParallel());
        IntStream sequential = parallel.sequential();
        System.out.println("Is the intStream parallel after sequential method? - " + sequential.isParallel() + ls);
        list.stream().parallel().peek(System.out::println).toList();
        System.out.println(ls + "For ex, we have int list {2, 1, 4, 3, 5}."
                .concat(" Here is the result of ForEachOrdered: "));
        List<Integer> intList = Arrays.asList(2, 1, 4, 3, 5);
        intList.stream().parallel().forEachOrdered(System.out::println);
    }
}
