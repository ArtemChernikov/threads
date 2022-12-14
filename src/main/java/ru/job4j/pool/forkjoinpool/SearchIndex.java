package ru.job4j.pool.forkjoinpool;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * Класс описывает модель для параллельного поиска элемента в массиве
 * с помощью {@link ForkJoinPool} и рекурсии, если элементов много (больше 10)
 * и линейного поиска (если элементов меньше 10)
 *
 * @author Artem Chernikov
 * @version 1.0
 * @since 12.12.2022
 */
public class SearchIndex<T> extends RecursiveTask<T> {
    /**
     * Поле массив
     */
    private final T[] array;
    /**
     * Поле индекс откуда начинаем поиск
     */
    private final int from;
    /**
     * Поле индекс где заканчиваем поиск
     */
    private final int to;
    /**
     * Поле искомый объект
     */
    private final T searchObject;
    /**
     * Поле констата - по размеру которого определяется,
     * когда следует начинать линейный поиск
     */
    private static final int ELEMENTS = 10;

    public SearchIndex(T[] array, int from, int to, T searchObject) {
        this.array = array;
        this.from = from;
        this.to = to;
        this.searchObject = searchObject;
    }

    /**
     * Метод используется для запуска поиска элемента в массиве
     *
     * @return - возвращает искомый элемент, в случае отсутствия такового null
     */
    public T search() {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        return forkJoinPool.invoke(new SearchIndex<T>(array, from, to, searchObject));
    }

    /**
     * Метод используется для линейного поиска элемента в массиве
     *
     * @param indexFrom - индекс начала
     * @param indexTo   - индекс конца
     * @param array     - массив
     * @param object    - искомый объект
     * @return - возвращает искомый элемент, в случае отсутствия такового null
     */
    private T lineSearch(int indexFrom, int indexTo, T[] array, T object) {
        for (int i = indexFrom; i < indexTo; i++) {
            if (array[i].equals(object)) {
                return array[i];
            }
        }
        return null;
    }

    /**
     * Метод используется для деления массива на две части, если он больше {@link SearchIndex#ELEMENTS}
     * и дальнейшего поиска искомого элемента в массиве
     *
     * @return - возвращает искомый элемент, в случае отсутствия такового null
     */
    @Override
    protected T compute() {
        if (to - from <= ELEMENTS) {
            T rsl = lineSearch(from, to, array, searchObject);
            return searchObject.equals(rsl) ? rsl : null;
        }
        int mid = (to - from) / 2;
        SearchIndex<T> first = new SearchIndex<>(array, from, to - mid, searchObject);
        SearchIndex<T> second = new SearchIndex<>(array, to - mid + 1, to, searchObject);
        first.fork();
        second.fork();
        T firstResult = first.join();
        T secondResult = second.join();
        return firstResult == null ? secondResult : firstResult;
    }
}
