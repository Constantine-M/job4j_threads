package ru.job4j.basic.sync.sharedresources.parser;

/**
 * Данный интерфейс описывает
 * сохранение контента.
 *
 * Контент можно будет сохранять
 * куда угодно (в память, в файл,
 * в БД и т.д.).
 */
public interface ContentSaver {

    void saveContent(String content);
}
