package ru.job4j.multithreading.pools.executorservice;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * В JDK входит пакет
 * {@link java.util.concurrent.ExecutorService},
 * {@link java.util.concurrent.Executors},
 * в котором уже есть готовая реализация.
 *
 * <p>Добавляем задачи в пул через
 * метод {@link java.util.concurrent.ExecutorService#submit}.
 *
 * <p>Метод {@link Executors#newFixedThreadPool(int)}
 * создает пул нитей по количеству доступных процессоров.
 *
 * <p>Метод {@link ExecutorService#shutdown()}
 * завершает работу пула, давая всем задачам в
 * очереди отработать до конца, после чего
 * закроется сам пул. После вызова shutdown()
 * пул не будет принимать новые задачи.
 */
public class ExecutorServiceDemo {
    public static void main(String[] args) {
        ExecutorService pool = Executors.newFixedThreadPool(
                Runtime.getRuntime().availableProcessors()
        );

        pool.submit(new Runnable() {
            @Override
            public void run() {
                System.out.println("Execute " + Thread.currentThread().getName());
            }
        });
        pool.submit(new Runnable() {
            @Override
            public void run() {
                System.out.println("Execute " + Thread.currentThread().getName());
            }
        });

        pool.shutdown();
        while (!pool.isTerminated()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Execute " + Thread.currentThread().getName());
    }
}
