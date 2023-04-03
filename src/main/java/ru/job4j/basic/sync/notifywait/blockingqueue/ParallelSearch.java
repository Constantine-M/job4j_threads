package ru.job4j.basic.sync.notifywait.blockingqueue;

/**
 * 2. Обеспечить остановку потребителя.
 *
 * <p>Для того чтобы произвести остановку
 * нити, следует вспомнить тему, изученную
 * ранее в пакете {@link ru.job4j.basic.stop}.
 *
 * <p>Для остановки нити нам потребуется
 * флаг, встренный в Java. Это есть метод
 * {@link Thread#isInterrupted()}.
 *
 * <p>Когда производитель закончит свою
 * работу, он же отдает команду для остановки
 * потребителя.
 */
public class ParallelSearch {

    public static void main(String[] args) {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(3);
        /*Потребитель*/
        final Thread consumer = new Thread(
                () -> {
                    while (!Thread.currentThread().isInterrupted()) {
                        try {
                            System.out.println(queue.poll());
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                }
        );
        consumer.start();
        /*Производитель*/
        new Thread(
                () -> {
                    for (int index = 0; index != 3; index++) {
                        try {
                            queue.offer(index);
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                    consumer.interrupt();
                }
        ).start();
    }
}
