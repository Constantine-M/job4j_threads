package ru.job4j.basic.sync.sharedresources.parser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.function.Predicate;

/**
 * Данный класс описывает парсер
 * файла.
 *
 * Соответственно его назначение -
 * это парсинг файла. Функция
 * сохранения контента вынесена
 * в отдельный класс.
 */
public class ParseFile {

    private static final Logger LOG = LoggerFactory.getLogger(ParseFile.class.getName());

    private final File file;

    public ParseFile(File file) {
        this.file = file;
    }

    public String getContentWithoutUnicode() {
        return getContentWithFilter(data -> data < 0x80);
    }

    /**
     * В данном методе я использовал
     * заглушку для {@link Predicate},
     * чтобы метод {@link Predicate#test}
     * всегда возвращал true. При этом
     * здесь нет ограничений (фильтра).
     * @return строку с текстом.
     */
    public String getContent() {
        return getContentWithFilter(data -> true);
    }

    /**
     * Данный метод парсит файл и
     * записывает текст в строку.
     * Особенность этого метода в том,
     * что можно задать в фильтре, какой
     * текст попадет в результирующую
     * строку. Например, можно
     * с помощью фильтра исключить
     * текст в формате Unicode.
     * Поэтому, на основе этого метода,
     * мы будем писать новые с другим
     * функционалом, более конкретным.
     * @param filter фильтр.
     * @return отфильтрованный текст.
     */
    private String getContentWithFilter(Predicate<Character> filter) {
        StringBuilder output = new StringBuilder();
        int data;
        try (InputStream i = new FileInputStream(file)) {
            while ((data = i.read()) != -1) {
                if (filter.test((char) data)) {
                    output.append((char) data);
                }
            }
        } catch (IOException e) {
            LOG.error("Exception write to log. ", e);
        }
        return output.toString();
    }
}
