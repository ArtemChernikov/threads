package ru.job4j.thread;

/**
 * @author Artem Chernikov
 * @version 1.0
 * @since 15.11.2022
 */
public final class Cache {
    private static Cache cache;

    public static synchronized Cache instOf() {
        if (cache == null) {
            cache = new Cache();
        }
        return cache;
    }
}
