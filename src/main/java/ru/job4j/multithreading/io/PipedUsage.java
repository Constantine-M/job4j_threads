package ru.job4j.multithreading.io;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

/**
 * В данном классе описывается запись
 * строки в созданный коннектор (пайп)
 * в отдельной нити, а потом производится
 * чтение этой строки из коннектора
 * в другой нити.
 *
 * <p>1.Создаются объекты классов
 * {@link PipedInputStream} и
 * {@link PipedOutputStream}.
 * 2.Соединение ввода и вывода производится
 * с помощью метода
 * {@link PipedInputStream#connect(PipedOutputStream)}.
 * Порядок соединения элементов не важен.
 * 3.В нити firstThread мы записываем строку.
 * В метод write мы передаём строку,
 * превращённую в байты.
 * 4.Далее в нити secondThread считываем
 * строку. Считывание происходит побайтово,
 * в выводе каждый байт приводится к типу
 * char для превращения байтов в символы.
 * 5.Открытые потоки закрываются с помощью
 * метода {@link PipedInputStream#close()} /
 * {@link PipedOutputStream#close()}
 * в каждой из нитей после выполнения нитью
 * всей полезной работы.
 */
public class PipedUsage {

    public static void main(String[] args) throws IOException {

        final PipedInputStream in = new PipedInputStream();
        final PipedOutputStream out = new PipedOutputStream();

        Thread firstThread = new Thread(() -> {
            try {
                out.write("Job4j".getBytes());
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        Thread secondThread = new Thread(() -> {
            try {
                int ch;
                while ((ch = in.read()) != -1) {
                    System.out.print((char) ch);
                }
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        in.connect(out);
        firstThread.start();
        secondThread.start();
    }
}
