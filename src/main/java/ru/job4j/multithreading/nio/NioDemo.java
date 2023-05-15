package ru.job4j.multithreading.nio;

import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * В данном классе демонстрируется чтение
 * из файла в буфер при помощи канала с
 * последующим выводом данных на консоль.
 *
 * <p>1.Метод {@link Files#newByteChannel}
 * создаёт канал.
 * 2.Буфер создаётся при помощи метода
 * {@link ByteBuffer#allocate}.
 * 3.Далее в цикле do-while читаем данные
 * в буфер, пока не будет достигнут
 * конец файла (-1) (byteChannel.read()).
 * 4.Далее проверяем, что в файле еще
 * есть данные к считыванию, а также вызываем
 * метод {@link ByteBuffer#rewind()},
 * который подготавливает буфер к считыванию
 * из него данных - устанавливает курсор в
 * нулевую позицию, так как после вызова
 * метода {@link SeekableByteChannel#read}
 * курсор будет находиться в конце буфера.
 * 5.Далее в цикле for выводим данные
 * в виде символов (char), так как метод
 * {@link ByteBuffer#get()} возвращает тип byte.
 *
 * <p>Применять Java NIO предпочтительнее,
 * когда есть очень много открытых соединений,
 * каждое из которых передаёт малое количество данных.
 */
public class NioDemo {

    public static void main(String[] args) throws Exception {
        int count;
        try (SeekableByteChannel byteChannel = Files.newByteChannel(Paths.get("data/nio.txt"))) {
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            do {
                count = byteChannel.read(buffer);
                if (count != -1) {
                    buffer.rewind();
                    for (int i = 0; i < count; i++) {
                        System.out.print((char) buffer.get());
                    }
                }
            } while (count != -1);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
