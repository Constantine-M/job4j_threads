package ru.job4j.basic.sync.sharedresources.parser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Данный класс описывает сохранение
 * данных в файл.
 */
public class FileSaver implements ContentSaver {

    private static final Logger LOG = LoggerFactory.getLogger(FileSaver.class.getName());

    private final File file;

    public FileSaver(File file) {
        this.file = file;
    }

    @Override
    public void saveContent(String content) {
        try (OutputStream o = new FileOutputStream(file)) {
            for (int i = 0; i < content.length(); i += 1) {
                o.write(content.charAt(i));
            }
        } catch (IOException e) {
            LOG.error("Exception write to log. ", e);
        }
    }
}
