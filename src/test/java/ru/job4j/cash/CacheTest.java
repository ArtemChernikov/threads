package ru.job4j.cash;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CacheTest {

    @Test
    void whenAddSuccess() {
        Cache cache = new Cache();
        Base base = new Base(1, 1);
        base.setName("firstBase");
        assertThat(cache.add(base)).isTrue();
    }

    @Test
    void whenAddNotSuccess() {
        Cache cache = new Cache();
        Base base = new Base(1, 1);
        base.setName("firstBase");
        cache.add(base);
        assertThat(cache.add(base)).isFalse();
    }

    @Test
    void whenDeleteSuccess() {
        Cache cache = new Cache();
        Base base = new Base(1, 1);
        cache.add(base);
        assertThat(cache.delete(new Base(1, 1))).isTrue();
    }

    @Test
    void whenDeleteNotSuccess() {
        Cache cache = new Cache();
        Base base = new Base(1, 1);
        cache.add(base);
        assertThat(cache.delete(new Base(1, 3))).isFalse();
    }

    @Test
    void whenUpdateSuccess() {
        Cache cache = new Cache();
        Base base = new Base(1, 1);
        Base update = new Base(1, 1);
        base.setName("oldName");
        update.setName("newName");
        cache.add(base);
        assertThat(cache.update(update)).isTrue();
    }

    @Test
    void whenUpdateNotSuccess() {
        Cache cache = new Cache();
        Base base = new Base(1, 1);
        Base update = new Base(2, 1);
        base.setName("oldName");
        update.setName("newName");
        cache.add(base);
        assertThat(cache.update(update)).isFalse();
    }

    @Test
    void whenUpdateNotSuccessBecauseDifferentVersions() {
        Cache cache = new Cache();
        Base base = new Base(1, 1);
        Base update = new Base(1, 2);
        base.setName("oldName");
        update.setName("newName");
        cache.add(base);
        Assertions.assertThrows(OptimisticException.class, () -> cache.update(update));
    }
}