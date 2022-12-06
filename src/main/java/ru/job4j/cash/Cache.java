package ru.job4j.cash;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Класс описывает модель неблокирующего (потоки работают параллельно)
 * кеша на {@link ConcurrentHashMap}
 *
 * @author Artem Chernikov
 * @version 1.0
 * @since 06.12.2022
 */
public class Cache {
    /**
     * Поле память кеша
     */
    private final Map<Integer, Base> memory = new ConcurrentHashMap<>();

    /**
     * Метод используется для добавления элемента в кэш, добавление атомарно
     *
     * @param model - добавляемый элемент
     * @return - возвращает boolean
     */
    public boolean add(Base model) {
        return memory.putIfAbsent(model.getId(), model) == null;
    }

    /**
     * Метод используется для обновления элемента в кэше,
     * но только если совпадают версии элементов,
     * после обновления версия элемента увеличивается,
     * обновление атомарно
     *
     * @param model - элемент на который обновляют
     * @return - возвращает boolean
     */
    public boolean update(Base model) {
        return memory.computeIfPresent(model.getId(), (key, value) -> {
                    if (model.getVersion() != value.getVersion()) {
                        throw new OptimisticException("Версии отличаются!");
                    }
                    int oldVersion = value.getVersion();
                    Base newVersion = new Base(model.getId(), ++oldVersion);
                    newVersion.setName(model.getName());
                    value = newVersion;
                    return value;
                }
        ) != null;
    }

    /**
     * Метод используется для удаления элемента в кэше,
     * удаление атомарно
     *
     * @param model - элемент на который обновляют
     * @return - возвращает boolean
     */
    public boolean delete(Base model) {
        return memory.remove(model.getId(), model);
    }
}
