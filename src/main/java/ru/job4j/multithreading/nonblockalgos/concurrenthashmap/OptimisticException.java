package ru.job4j.multithreading.nonblockalgos.concurrenthashmap;

/**
 * В кеше нужно перед обновлением данных
 * проверить поле {@link Base#version}.
 * Если version у модели и в кеше
 * одинаковы, то можно обновить.
 * Если нет, то выбросить данное
 * исключение.
 */
public class OptimisticException extends RuntimeException {

    public OptimisticException(String message) {
        super(message);
    }
}
