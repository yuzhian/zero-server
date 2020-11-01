package com.github.yuzhian.zero.server.config.shiro;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.cache.Cache;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * @author yuzhian
 * @since 2020-10-31
 */
@Slf4j
public class RedisCache<K, V> implements Cache<K, V> {
    private final RedisTemplate<K, V> shiroRedisTemplate;

    @SuppressWarnings("all")
    public RedisCache(RedisTemplate shiroRedisTemplate) {
        this.shiroRedisTemplate = shiroRedisTemplate;
    }

    @Override
    public V get(K key) {
        if (key == null) {
            return null;
        }
        V value = shiroRedisTemplate.opsForValue().get(key);
        if (log.isDebugEnabled()) {
            log.debug("redis cache get key: {}, value: {}", key, value);
        }
        return value;
    }

    @Override
    public V put(K key, V value) {
        if (key == null || value == null) {
            return null;
        }
        shiroRedisTemplate.opsForValue().set(key, value);
        if (log.isDebugEnabled()) {
            log.debug("redis cache put key: {}, value: {}", key, value);
        }
        return value;
    }

    @Override
    public V remove(K key) {
        if (log.isDebugEnabled()) {
            log.debug("redis cache remove key: {}", key);
        }

        if (key == null) {
            return null;
        }

        ValueOperations<K, V> vo = shiroRedisTemplate.opsForValue();
        V value = vo.get(key);
        shiroRedisTemplate.delete(key);
        return value;
    }

    @Override
    public void clear() {
        Objects.requireNonNull(shiroRedisTemplate.getConnectionFactory()).getConnection().flushDb();
    }

    @Override
    public int size() {
        Long len = Objects.requireNonNull(shiroRedisTemplate.getConnectionFactory()).getConnection().dbSize();
        return Objects.requireNonNull(len).intValue();
    }

    @Override
    @SuppressWarnings(value = "unchecked")
    public Set<K> keys() {
        Set<K> set = shiroRedisTemplate.keys((K) "*");
        if (CollectionUtils.isEmpty(set)) {
            return Collections.emptySet();
        }
        return set;
    }

    @Override
    public Collection<V> values() {
        Set<K> keys = keys();
        List<V> values = new ArrayList<>(keys.size());
        for (K k : keys) {
            values.add(shiroRedisTemplate.opsForValue().get(k));
        }
        return values;
    }

}