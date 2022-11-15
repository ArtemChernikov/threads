package ru.job4j.thread;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

/**
 * Класс описывает модель программы для загрузки файлов в интернете с ограничением скорости
 * В параметрах запуска указываются - URL страницы в интернете, максимальная скорость скачивания файла
 * и путь к файлу для записи данных
 *
 * @author Artem Chernikov
 * @version 1.0
 * @since 14.11.22
 */
public class Wget implements Runnable {
    /**
     * Поле URL страницы в интернете
     */
    private final String url;
    /**
     * Поле максимальная скорость скачивания
     */
    private final int speed;
    /**
     * Поле путь к файлу для записи данных
     */
    private final String filePath;

    public Wget(String url, int speed, String filePath) {
        this.url = url;
        this.speed = speed;
        this.filePath = filePath;
    }

    /**
     * Метод используется для загрузки данных с ограничением скорости скачивания
     */
    @Override
    public void run() {
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(filePath)) {
            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            long bytesWrite = 0;
            long start = System.currentTimeMillis();
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                bytesWrite += bytesRead;
                if (bytesWrite >= speed) {
                    long deltaTime = System.currentTimeMillis() - start;
                    if (deltaTime < 1000) {
                        Thread.sleep(1000 - deltaTime);
                    }
                    bytesWrite = 0;
                    start = System.currentTimeMillis();
                }
                fileOutputStream.write(dataBuffer, 0, bytesRead);
            }
        } catch (IOException | InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Метод используется для валидации входных параметров запуска
     *
     * @param args - входные параметры запуска
     */
    public static void validateArgs(String[] args) {
        if (args.length < 2) {
            throw new IllegalArgumentException("Укажите в парметрах запуска URL и ограничение скорости!");
        }
        if (!args[0].startsWith("https")) {
            throw new IllegalArgumentException("Укажите корректный URL!");
        }
        if (Integer.parseInt(args[1]) <= 0) {
            throw new IllegalArgumentException("Укажите корректное ограничение скорости в байтах в секунду!");
        }
        if (!args[2].matches(".+\\..+")) {
            throw new IllegalArgumentException("Укажите корректный файл для записи данных!");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        validateArgs(args);
        Thread get = new Thread(new Wget(args[0], Integer.parseInt(args[1]), args[2]));
        get.start();
        get.join();
    }
}
