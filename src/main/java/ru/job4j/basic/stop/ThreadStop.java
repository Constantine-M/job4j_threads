package ru.job4j.basic.stop;

/**
 * Данный класс описывает процесс
 * прерывания потока. В Java
 * операция прерывания полностью
 * ложится на плечи программиста.
 *
 * Метод {@link Thread#interrupt()}
 * выставляет флаг прерывания,
 * но никаких дополнительных действий
 * не совершает. Это своеобразная
 * рекомендацция, чтобы нить
 * завершала свою работу.
 *
 * Метод {@link Thread#isInterrupted()}
 * возвращает состояние флага прерывания
 * и не меняет его состояние,
 * то есть делает только проверку.
 *
 * Еще в классе {@link Thread}
 * имеется метод boolean interrupted().
 * Его отличие от isInterrupted() только
 * в том, что он сбрасывает статус
 * прерывания после проверки.
 *
 * Если запустить эту программу несколько раз,
 * то в консоли можно увидеть разное
 * количество чисел. Почему?
 * Планировщик выделяет разное время
 * для каждой нити, по этой причине
 * флаг прерывания выставляется
 * в произвольное время.
 */
public class ThreadStop {
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(
                () -> {
                    int count = 0;
                    while (!Thread.currentThread().isInterrupted()) {
                        System.out.println(count++);
                    }
                }
        );
        thread.start();
        Thread.sleep(10);
        thread.interrupt();
    }
}
