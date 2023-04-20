package ru.job4j.multithreading.nonblockalgos.concurrenthashmap;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Данный класс описывает наш кеш.
 *
 * <p>В кеше должна быть возможность
 * проверять актуальность данных.
 * Для этого в модели данных
 * используется поле {@link Base#version}.
 */
public class Cache {

    private final Map<Integer, Base> memory = new ConcurrentHashMap<>();

    public boolean add(Base model) {
        return memory.putIfAbsent(model.getId(), model) == null;
    }

    /**
     * Данный метод увеличивает счетчик
     * версии нашей модели {@link Base}.
     *
     * <p>Здесь мы пользуемся функцией
     * {@link Map#computeIfPresent (key, BiFunction)}.
     * Принцип следующий: вычисления
     * со значением будут выполнены,
     * если элемент с ключом key уже
     * существует.
     *
     * <p>Значение - это наша модель
     * {@link Base}, у которой нужно
     * увеличить счетчик.
     *
     * <p>Вторым аргументом функция
     * принимает BiFunction. Внутри лямбды
     * мы проверяем, что версия в кеше
     * и версия модели одинаковые.
     * Важно, чтобы все вычисления
     * происходили ВНУТРИ лямбды!
     * Если версии одинаковые, то обновляем
     * модель - перезаписываем модель
     * со старыми данными, но увеличенным
     * счетчиком и возвращаем этот
     * обновленный объект.
     *
     * @param model обновляемая модель {@link Base}.
     * @return true если элемент обновился.
     */
    public boolean update(Base model) {
        return memory.computeIfPresent(model.getId(),
                (key, value) -> {
                    if (value.getVersion() != model.getVersion()) {
                        throw new OptimisticException("Versions are not equal");
                    }
                    Base updateBase = new Base(value.getId(), value.getVersion() + 1);
                    updateBase.setName(value.getName());
                    return updateBase;
                }
        ) != null;
    }

    /**
     * The following APIs are also overridden 
     * to support atomicity, without a default 
     * interface implementation:
     * {@link ConcurrentHashMap#putIfAbsent}
     * {@link ConcurrentHashMap#remove}
     * {@link ConcurrentHashMap#replace (key, oldValue, newValue)}
     * {@link ConcurrentHashMap#replace (key, value)}
     * The rest of actions are directly inherited
     * with basically consistent with Map.
     */
    public void delete(Base model) {
        memory.remove(model.getId());
    }

    public Base get(int baseId) {
        return memory.get(baseId);
    }
}
