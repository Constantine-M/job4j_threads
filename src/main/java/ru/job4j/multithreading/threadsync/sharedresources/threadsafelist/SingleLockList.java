package ru.job4j.multithreading.threadsync.sharedresources.threadsafelist;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * 4. ThreadSafe динамический список.
 *
 * <p>Данный класс описывает коллекцию,
 * которая будет корректно работать
 * в многопоточной среде.
 *
 * <p>То есть сама коллекция будет
 * общим ресурсом между нитями.
 */
@ThreadSafe
public class SingleLockList<T> implements Iterable<T> {

    @GuardedBy("this")
    private final List<T> list;

    public SingleLockList(List<T> list) {
        this.list = copy(list);
    }

    public synchronized void add(T value) {
        list.add(value);
    }

    public synchronized T get(int index) {
        return list.get(index);
    }

    /**
     * Данный метод описывает итератор.
     *
     * <p>Этот итератор будет работать в режиме
     * fail-safe - все изменения после получения
     * коллекции не будут отображаться в итераторе.
     *
     * <p>Другой режим - это fail-fast. При
     * изменении данных во время итерации,
     * коллекция выкинет исключение
     * {@link java.util.ConcurrentModificationException}.
     */
    @Override
    public synchronized Iterator<T> iterator() {
        return copy(list).iterator();
    }

    /**
     * Данный метод копирует изначальный список.
     *
     * <p>Я нашел 2 варианта исполнения -
     * короткий и более подробный. Оба
     * варианта - это аналоги метода
     * {@link Collections#synchronizedList}.
     * Метод нагляднее показывает работу
     * шаблона "Декоратор" -
     * оборачивает наш список и возвращает
     * его синхронизированный вид.
     *
     * <p>Во втором способе я использовал
     * коллектор и его метод
     * {@link Collectors#collectingAndThen(Collector, Function)},
     * где первым параметром указываем, во
     * что собрать, а вторым как мы хотим
     * этот список трансформировать. Таким
     * образом мы создали модифицируемую
     * синхронизированную копию нашего списка.
     *
     * @param origin исходный список.
     * @return thread-safe копия исходного списка.
     */
    private synchronized List<T> copy(List<T> origin) {
        /*return new ArrayList<>(origin);*/
        return origin.stream()
                    .collect(Collectors.collectingAndThen(Collectors.toList(), ArrayList::new));
    }
}
