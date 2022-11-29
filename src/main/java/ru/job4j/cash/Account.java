package ru.job4j.cash;

/**
 * Класс описывает модель аккаунта со свойствами id и баланс пользователя
 *
 * @author Artem Chernikov
 * @version 1.0
 * @since 28.11.2022
 */
public record Account(int id, int amount) {
}
