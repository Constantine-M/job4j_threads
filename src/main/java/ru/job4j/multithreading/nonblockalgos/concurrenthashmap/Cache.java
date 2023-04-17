package ru.job4j.multithreading.nonblockalgos.concurrenthashmap;

import java.util.HashMap;
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

    /*public boolean update(Base model) {
        Base stored = memory.get(model.getId());
        int storedVer = memory.get(model.getId()).getVersion();
        int currentVer = model.getVersion();
        memory.computeIfPresent(storedVer, currentVer, (a, b) -> {
            if (storedVer != currentVer) {
                throw new OptimisticException("Versions are not equal");
            }
        });
        return true;
    }*/

    /*public boolean update(Base model) {
        Base stored = memory.get(model.getId());
        boolean updated = memory.computeIfPresent(
                (k, v) -> checkVersion(stored.getVersion(), model.getVersion())
        );
        return true;
    }*/

    /*public boolean update(Base model) {
        Base stored = memory.get(model.getId());
        boolean updated = memory.computeIfPresent(
                (k, v) -> checkVersion(stored.getVersion(), model.getVersion())
        );
        if (!updated) {
            throw new OptimisticException("Versions are not equal");
        }
        return true;
    }*/

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

    /**
     * Test method.
     */
    private synchronized boolean checkVersion(int stored, int current) {
        return stored == current;
    }
}
