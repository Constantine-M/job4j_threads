package ru.job4j.basic;

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
 * В нашем классе 2 варианта - с лямбдой
 * и без нее. Разница в занимаемом
 * простратве большая и читаемость
 * с лямбдой лучше.
 */
public class ConcurrentOutput {
    public static void main(String[] args) {
        Thread another = new Thread(
                () -> System.out.println(Thread.currentThread().getName())
        );
        another.start();
        System.out.println(Thread.currentThread().getName());

        Thread second = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName());
            }
        });
        second.start();
    }
}
