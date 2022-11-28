package ru.job4j.cash;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Класс описывает потокобезопасную модель хранилища денежных аккаунтов пользователей
 *
 * @author Artem Chernikov
 * @version 1.0
 * @since 28.11.2022
 */
@ThreadSafe
public class AccountStorage {
    /**
     * Поле хранилище аккаунтов {@link HashMap} ключ - id аккаунта, значение - сам аккаунт
     */
    @GuardedBy("this")
    private final Map<Integer, Account> accounts = new HashMap<>();

    /**
     * Метод используется для добавления аккаунта в хранилище
     *
     * @param account - аккаунт
     * @return - возвращает true, если добавление успешно и false, если иначе
     */
    public synchronized boolean add(Account account) {
        if (accounts.containsValue(account)) {
            return false;
        }
        accounts.put(account.id(), account);
        return true;
    }

    /**
     * Метод используется для обновления аккаунта под тем же ключом в хранилище
     *
     * @param account - аккаунт
     * @return - возвращает true, если обновление успешно и false, если иначе
     */
    public synchronized boolean update(Account account) {
        int id = account.id();
        return accounts.replace(id, getById(id).get(), account);
    }

    /**
     * Метод используется для удаления аккаунта в хранилище по id
     *
     * @param id - id аккаунта
     * @return - возвращает true, если удаление успешно и false, если иначе
     */
    public synchronized boolean delete(int id) {
        return accounts.remove(id, getById(id).get());
    }

    /**
     * Метод используется для поиска аккаунта по id
     *
     * @param id - id аккаунта
     * @return - возвращает объект {@link Optional<Account>}
     */
    public synchronized Optional<Account> getById(int id) {
        return Optional.ofNullable(accounts.get(id));
    }

    /**
     * Метод используется для перевода денежной суммы с одного счета на другой
     *
     * @param fromId - id аккаунта откуда совершают перевод
     * @param toId   - id аккаунта куда совершают перевод
     * @param amount - сколько денег переводят
     * @return - возвращает true, если перевод выполнен успешно и false, если иначе
     */
    public synchronized boolean transfer(int fromId, int toId, int amount) {
        Account account1 = accounts.get(fromId);
        if (account1.amount() < amount) {
            return false;
        }
        Account account2 = accounts.get(toId);
        update(new Account(fromId, account1.amount() - amount));
        update(new Account(toId, account2.amount() + amount));
        return true;
    }
}
