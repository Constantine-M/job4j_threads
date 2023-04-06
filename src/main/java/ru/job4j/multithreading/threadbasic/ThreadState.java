package ru.job4j.multithreading.threadbasic;

/**
 * Чтобы получить состояние нити,
 * можно воспользоваться методом
 * {@link Thread#getState()}.
 * За управление нитями в Java отвечает
 * планировщик задач. Он решает,
 * сколько времени отвести на
 * выполнение одной задачи. Это время
 * зависит от текущей ситуации.
 * Если задач много, то переключение
 * между нитями будет частое.
 *
 * @author Constantine on 25.02.2023
 */
public class ThreadState {

    private static void printStateIfNotTerminated(Thread thread) {
        if (thread.getState() != Thread.State.TERMINATED) {
            System.out.println("State of "
                    .concat(thread.getName())
                    .concat(" - ")
                    .concat(String.valueOf(thread.getState()))
            );
        }
    }

    private static void printState(Thread thread) {
        System.out.println("State of "
                .concat(thread.getName())
                .concat(" - ")
                .concat(String.valueOf(thread.getState()))
        );
    }
    public static void main(String[] args) {
        Thread first = new Thread(
                () -> System.out.println("DOIN SOMETHING IN ".concat(Thread.currentThread().getName()))
        );
        ThreadState.printState(first);
        first.start();
        Thread second = new Thread(
                () -> System.out.println("DOIN SOMETHING IN ".concat(Thread.currentThread().getName()))
        );
        ThreadState.printState(second);
        second.start();
        while (first.getState() != Thread.State.TERMINATED
                || second.getState() != Thread.State.TERMINATED) {
            ThreadState.printStateIfNotTerminated(first);
            ThreadState.printStateIfNotTerminated(second);
        }
        ThreadState.printState(first);
        ThreadState.printState(second);
        ThreadState.printState(Thread.currentThread());
        System.out.println("Thread "
                .concat(Thread.currentThread().getName())
                .concat(" - app is closing.."));
    }
}
