package ru.job4j.cash;

/**
 * @author Artem Chernikov
 * @version 1.0
 * @since 06.12.2022
 */
public class OptimisticException extends RuntimeException {
    public OptimisticException(String message) {
        super(message);
    }
}
