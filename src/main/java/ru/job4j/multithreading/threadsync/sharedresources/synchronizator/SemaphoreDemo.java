package ru.job4j.multithreading.threadsync.sharedresources.synchronizator;

import java.util.concurrent.Semaphore;

/**
 * Данный класс демонстрирует
 * принцип работы одного из
 * синхронизаторов - {@link Semaphore}.
 *
 * <p>Это тип синхронизатора со
 * счетчиком (количеством разрешений
 * для входа). При создании объекта
 * {@link Semaphore} в конструкторе
 * задается изначальное количество
 * разрешений. Запрашивать можно 1 и
 * более разрешений сразу.
 *
 * <p>Mutex - это семафор с 1 разрешением.
 *
 * <p>С помощью команды
 * {@link Semaphore#acquire()}
 * запрашивается разрешение на
 * выполнение своей работы
 * (или получения доступа к объекту).
 *
 * <p>После выполнения нитью своей
 * работы, разрешение возвращается
 * обратно в семафор с помощью
 * команды {@link Semaphore#release()}.
 */
public class SemaphoreDemo {

    public static void main(String[] args) throws InterruptedException {
        Semaphore sem = new Semaphore(0);
        Runnable task = () -> {
            try {
                sem.acquire();
                System.out.println("Нить выполнила задачу");
                sem.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
        new Thread(task).start();
        Thread.sleep(3000);
        sem.release(1);
    }
}
