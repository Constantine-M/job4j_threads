package ru.job4j.basic.wget;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

/**
 * Данный класс описывает консольную
 * программу, аналог wget,
 * который позволяет скачивать файлы
 * из сети с ограниченной скоростью.
 */
public class SimpleWget implements Runnable {

    /**
     * Данное поле описывает скорость
     * загрузки файла в Byte/sec.
     */
    private final String url;

    private final int speed;

    public SimpleWget(String url, int speed) {
        this.url = url;
        this.speed = speed;
    }

    @Override
    public void run() {
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream("test_phrases.txt")) {
            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            long startTime = System.currentTimeMillis();
            while (((bytesRead = in.read(dataBuffer, 0, 1024)) != -1)) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
                long timeInSec = (System.currentTimeMillis() - startTime);
                System.out.println("Time in milliseconds: ".concat(String.valueOf(timeInSec)));

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        Thread wget = new Thread(new SimpleWget(url, speed));
        wget.start();
        wget.join();
    }
}
