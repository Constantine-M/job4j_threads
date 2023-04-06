package ru.job4j.multithreading.threadbasic;

/**
 * Данный класс описывет самые основные
 * моменты при работе с потоками.
 *
 * Статический метод
 * {@link Thread#getName()}
 * позволяет получить экземпляр текущей
 * нити выполнения. То есть той нити,
 * в который выполняется этот оператор.
 *
 * Метод {@link Thread#start()} указывает
 * виртуальной машине, что операторы,
 * описанные в конструкторе, нужно
 * запустить в отдельной нити. Если
 * убрать этот оператор, то вывода
 * имени второй нити не будет.
 *
 * Конструктор класса {@link Thread}
 * принимает функциональный интерфейс
 * {@link Runnable}. Это интерфейс имеет
 * один метод {@link Runnable#run()}.
 * Методы, определенные в этом методе,
 * будут выполняться в многозадачной среде.
 * Реализуем это через анонимный класс,
 * который для тебя second.
 * При создании нити лучше использовать
 * лямбда выражения - код намного короче
 * и читать его легче.
 */
public class ConcurrentOutput {
    public static void main(String[] args) {
        Thread another = new Thread(
                () -> System.out.println(Thread.currentThread().getName())
        );
        another.start();
        System.out.println(Thread.currentThread().getName());

        Thread second = new Thread(
                () -> System.out.println(Thread.currentThread().getName())
        );
        second.start();
    }
}
