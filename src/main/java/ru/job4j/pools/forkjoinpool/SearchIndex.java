package ru.job4j.pools.forkjoinpool;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * Класс описывает модель для параллельного поиска индекса элемента в массиве
 * с помощью {@link ForkJoinPool} и рекурсии, если элементов много (больше 10)
 * и линейного поиска (если элементов меньше 10)
 *
 * @author Artem Chernikov
 * @version 1.1
 * @since 15.12.2022
 */
public class SearchIndex<T> extends RecursiveTask<Integer> {
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
     * @return - возвращает индекс искомого элемента, в случае отсутствия такового -1
     */
    public static <T> Integer search(T[] array, T object) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        return forkJoinPool.invoke(new SearchIndex<>(array, 0, array.length, object));
    }

    /**
     * Метод используется для линейного поиска элемента в массиве
     *
     * @return - возвращает индекс искомого элемента, в случае отсутствия такового -1
     */
    private Integer lineSearch() {
        for (int i = from; i < to; i++) {
            if (array[i].equals(searchObject)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Метод используется для деления массива на две части, если он больше
     * {@link SearchIndex#ELEMENTS} и дальнейшего поиска искомого элемента в двух новых массивах
     *
     * @return - возвращает индекс искомого элемента, в случае отсутствия такового -1
     */
    @Override
    protected Integer compute() {
        if (to - from <= ELEMENTS) {
            return lineSearch();
        }
        int mid = (to - from) / 2;
        SearchIndex<T> first = new SearchIndex<>(array, from, to - mid, searchObject);
        SearchIndex<T> second = new SearchIndex<>(array, to - mid + 1, to, searchObject);
        first.fork();
        second.fork();
        Integer firstResult = first.join();
        Integer secondResult = second.join();
        return Math.max(firstResult, secondResult);
    }
}
