package ru.job4j.basic.stop;

/**
 * 6. Прерывание блокированной нити.
 *
 * Данный класс реализует и
 * демонстрирует анимацию загрузки.
 * {@link Thread#sleep} внутри цикла
 * замедляет процесс отображения
 * символов, что позволяет их
 * видеть во время работы программы.
 *
 * {@link Thread#interrupt()}, вызванный,
 * например, в методах {@link Thread#sleep},
 * {@link Thread#join()}, {@link Thread#wait()},
 * не выставляет флаг прерывания,
 * если нить находится в режиме
 * ожидания, сна или заблокирована
 * на длительное время другим
 * схожим вызовом.
 *
 * Поэтому чтобы прервать нить,
 * в блоке catch нужно дополнительно
 * вызывать прерывание нити,
 * для того чтобы прерывания
 * действительно произошло.
 */
public class ConsoleProgress implements Runnable {

    @Override
    public void run() {
        var process = new char[] {'—', '\\', '|', '/'};
        byte i = 0;
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Thread.sleep(500);
                System.out.print("\rLoading: " + process[i++]);
                if (i == process.length) {
                    i = 0;
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread progress = new Thread(new ConsoleProgress());
        progress.start();
        Thread.sleep(5000);
        progress.interrupt();
    }
}
