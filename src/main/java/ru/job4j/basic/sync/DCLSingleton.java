package ru.job4j.basic.sync;

/**
 * Данный класс описывает паттерн
 * double-checked locking.
 *
 * Ранее я описывал вариант в
 * классе {@link Cache}. У этого
 * варианта есть только один недостаток.
 * Синхронизация полезна только один
 * раз, при первом обращении к
 * {@link  Cache#instOf()},
 * после этого каждый раз,
 * при обращении этому методу,
 * синхронизация просто забирает время.
 *
 * Самый распространенный способ
 * решить эту проблему - это DCL
 * (double-checked locking).
 *
 * Без ключевого слова "volatile"
 * может произойти так, что главная
 * нить запишет новое значение
 * переменной в кеш процессора, а
 * дополнительная нить будет продолжать
 * читать переменную inst из регистра.
 * То есть, другой поток может получить
 * и начать использовать (на основании
 * условия, что указатель не нулевой)
 * не полностью сконструированный объект.
 */
public class DCLSingleton {

    private static volatile DCLSingleton inst;

    public static DCLSingleton instOf() {
        if (inst == null) {
            synchronized (DCLSingleton.class) {
                if (inst == null) {
                    inst = new DCLSingleton();
                }
            }
        }
        return inst;
    }

    private DCLSingleton() {

    }
}
