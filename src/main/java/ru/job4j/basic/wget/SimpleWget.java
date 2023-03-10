package ru.job4j.basic.wget;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.stream.Collectors;

/**
 * Данный класс описывает консольную
 * программу, аналог wget,
 * который позволяет скачивать файлы
 * из сети с ограниченной скоростью.
 */
public class SimpleWget implements Runnable {

    /**
     * Данное поле описывает ссылку.
     * По ссылке мы будем скачивать
     * файл и на основе ссылки
     */
    private final String url;

    /**
     * Данное поле описывает скорость
     * загрузки файла в Byte/sec.
     */
    private final int speed;

    public SimpleWget(String url, int speed) {
        this.url = url;
        this.speed = speed;
        validate(url, speed);
    }

    @Override
    public void run() {
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(resultFileName(url))) {
            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            int downloadData = 0;
            long startCycleTime = System.currentTimeMillis();
            while (((bytesRead = in.read(dataBuffer, 0, 1024)) != -1)) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
                downloadData += bytesRead;
                try {
                    if (downloadData >= speed) {
                        long cycleTime = System.currentTimeMillis() - startCycleTime;
                        if (cycleTime < 1000) {
                            Thread.sleep(1000 - cycleTime);
                        }
                        startCycleTime = System.currentTimeMillis();
                        downloadData = 0;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Данный метод формирует название
     * файла, используя ссылку, по которой
     * мы его скачиваем.
     *
     * Пример ссылки:
     * <a href="https://proof.ovh.net/files/10Mb.dat">TestCase</a>
     *
     * @param url ссылка на файл.
     * @return название файла, которое будет
     * сформировано при скачивании.
     */
    private String resultFileName(String url) {
        LinkedList<String> result = Arrays.stream(url.split("/"))
                .collect(Collectors.toCollection(LinkedList::new));
        return "test_".concat(result.getLast());
    }

    private void validate(String url, int speed) {
        if (speed <= 0) {
            throw new IllegalArgumentException("Speed must be above 0");
        }
        isValidUrl(url);
    }

    /**
     * Данный метод проверяет ссылку
     * на валидность - метод
     * {@link URL#toURI()} делает
     * проверку более полной на мой взгляд.
     * Он проверяет совместимость.
     * Если оставить блок "new URL(url)",
     * то будет проверяться только протокол
     * и строка на null.
     * @param url проверяемая ссылка.
     * @return true, если ссылка валидная.
     */
    private boolean isValidUrl(String url) {
        try {
            new URL(url).toURI();
            return true;
        } catch (MalformedURLException e) {
            System.out.println("URL is not valid! Please, check the URL. Exception text: " + e);
            return false;
        } catch (URISyntaxException e) {
            System.out.println("URL is not valid! Please, check the URL. Exception text: " + e);
            return false;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        Thread wget = new Thread(new SimpleWget(url, speed));
        long start = System.currentTimeMillis();
        wget.start();
        wget.join();
        System.out.println("Total time is: "
                .concat(String.valueOf(System.currentTimeMillis() - start)));
    }
}
