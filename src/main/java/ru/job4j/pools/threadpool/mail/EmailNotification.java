package ru.job4j.pool.threadpool.mail;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Класс описывает сервис для рассылки почты с помощью пула нитей {@link ExecutorService}
 *
 * @author Artem Chernikov
 * @version 1.0
 * @since 10.12.2022
 */
public class EmailNotification {
    private final ExecutorService pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    public void emailTo(User user) {
        String username = user.getUsername();
        String email = user.getEmail();
        String subject = String.format("Notification {%s} to email {%s}", username, email);
        String body = String.format("Add a new event to {%s}", username);
        pool.submit(() -> send(subject, body, email));
    }

    public void send(String subject, String body, String email) {

    }

    public void close() {
        pool.shutdown();
        while (!pool.isTerminated()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
