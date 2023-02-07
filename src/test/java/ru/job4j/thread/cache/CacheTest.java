package ru.job4j.thread.cache;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class CacheTest {
    private final Cache cache = new Cache();

    @Test
    void whenAddSuccessfully() {
        Base base = new Base(1, 0);
        assertThat(cache.add(base)).isTrue();
        base.setName("base");
        assertThat(base.getName()).isEqualTo("base");
    }

    @Test
    void whenAddBaseWithTheSameId() {
        Base base = new Base(1, 0);
        Base base1 = new Base(1, 0);
        cache.add(base);
        assertThat(cache.add(base1)).isFalse();
    }

    @Test
    void whenDeleteSuccessfully() {
        Base base = new Base(1, 0);
        cache.add(base);
        cache.delete(base);
        assertThat(cache.getValue(base.getId())).isNull();
    }

    @Test
    void whenUpdateSuccessfully() {
        Base base = new Base(1, 0);
        cache.add(base);
        assertThat(cache.update(base)).isTrue();
        assertThat(cache.getValue(base.getId()).getId()).isEqualTo(1);
    }

    @Test
    void whenUpdateAlreadyChangedVersion() {
        Base base = new Base(1, 0);
        cache.add(base);
        Base user1 = cache.getValue(1);
        user1.setName("User 1");
        Base user2 = cache.getValue(1);
        user2.setName("User 2");
        assertThatThrownBy(() -> {
            cache.update(user1);
            cache.update(user2);
        }).hasMessageMatching("Versions are not equal.");
    }
}
