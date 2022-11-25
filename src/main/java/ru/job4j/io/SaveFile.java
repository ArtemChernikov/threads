package ru.job4j.io;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author Artem Chernikov
 * @version 1.0
 * @since 25.11.2022
 */
public final class SaveFile {

    private final File file;

    public SaveFile(File file) {
        this.file = file;
    }

    /**
     * Метод используется для записи строки в файл
     *
     * @param content - строка
     */
    public void saveContent(String content) {
        try (BufferedOutputStream o = new BufferedOutputStream(new FileOutputStream(file))) {
            o.write(content.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
