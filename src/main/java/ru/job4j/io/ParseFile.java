package ru.job4j.io;

import java.io.*;
import java.util.function.Predicate;

/**
 * @author Artem Chernikov
 * @version 1.0
 * @since 25.11.2022
 */
public final class ParseFile {
    private final File file;

    public ParseFile(File file) {
        this.file = file;
    }

    /**
     * Метод используется для парсинга файла по определенному фильтру {@link Predicate<Character>}
     *
     * @param filter - фильтр
     * @return - возвращает отфильтрованный файл в виде строки
     */
    public String getContent(Predicate<Character> filter) {
        StringBuilder output = new StringBuilder();
        try (BufferedInputStream i = new BufferedInputStream(new FileInputStream(file))) {
            int data;
            while ((data = i.read()) != -1) {
                char symbol = (char) data;
                if (filter.test(symbol)) {
                    output.append(symbol);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return output.toString();
    }

    public String getContentWithUnicode() {
        return getContent(x -> true);
    }

    public String getContentWithoutUnicode() {
        return getContent(x -> x < 0x80);
    }
}
