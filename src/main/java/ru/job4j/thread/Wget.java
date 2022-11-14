package ru.job4j.thread;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

public class Wget implements Runnable {
    private final String url;
    private final int speed;

    public Wget(String url, int speed) {
        this.url = url;
        this.speed = speed;
    }

    @Override
    public void run() {
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream("pom_tmp.xml")) {
            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            long bytesWrite = 0;
            long start = System.currentTimeMillis();
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                bytesWrite += bytesRead;
                System.out.print("\r Loading... " + bytesWrite + " bytes");
                if (bytesWrite >= speed) {
                    long deltaTime = System.currentTimeMillis() - start;
                    System.out.println(deltaTime);
                    if (deltaTime < 1000) {
                        Thread.sleep(10000 - deltaTime);
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
    }

    public static void main(String[] args) throws InterruptedException {
        validateArgs(args);
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        Thread get = new Thread(new Wget(url, speed));
        get.start();
        get.join();
    }
}
