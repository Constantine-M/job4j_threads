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

    /**
     * Данный метод парсит файл и
     * записывает текст в строку.
     * Особенность этого метода в том,
     * что можно задать в фильтре, какой
     * текст попадет в результирующую
     * строку. Например, можно
     * с помощью фильтра исключить
     * текст в формате Unicode.
     * @param filter фильтр.
     * @return отфильтрованный текст.
     */
    public String getContent(Predicate<Character> filter) {
        String output = "";
        int data;
        try (InputStream i = new FileInputStream(file)) {
            while ((data = i.read()) > 0) {
                if (filter.test((char) data)) {
                    output += (char) data;
                }
            }
        } catch (IOException e) {
            LOG.error("Exception write to log. ", e);
        }
        return output;
    }
}
