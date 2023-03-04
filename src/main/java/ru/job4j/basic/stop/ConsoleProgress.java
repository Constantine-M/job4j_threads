package ru.job4j.basic.stop;

/**
 * Данный класс реализует и
 * демонстрирует анимацию загрузки.
 * {@link Thread#sleep} внутри цикла
 * замедляет процесс отображения
 * символов, что позволяет их
 * видеть во время работы программы.
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
                if (i > 3) {
                    i = 0;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
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
