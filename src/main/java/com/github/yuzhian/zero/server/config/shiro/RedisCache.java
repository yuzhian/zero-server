package com.github.yuzhian.zero.server.config.shiro;

import com.github.yuzhian.zero.server.common.global.SystemConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.cache.Cache;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.CollectionUtils;

import java.time.Duration;
import java.util.*;

/**
 * @author yuzhian
 * @since 2020-10-31
 */
@Slf4j
public class RedisCache<K, V> implements Cache<K, V> {
    private final RedisTemplate<K, V> redisTemplate;

    @SuppressWarnings("all")
    public RedisCache(RedisTemplate shiroRedisTemplate) {
        this.redisTemplate = shiroRedisTemplate;
    }

    @SuppressWarnings("unchecked")
    private K getCacheKey(K key) {
        return (K) (SystemConstants.CACHE_PREFIX + key);
    }

    @Override
    public V get(K key) {
        if (log.isDebugEnabled()) log.debug("get({})", key);
        if (key == null) return null;
        return redisTemplate.opsForValue().get(getCacheKey(key));
    }

    @Override
    public V put(K key, V value) {
        if (log.isDebugEnabled()) log.debug("put({}, {})", key, value);
        if (key == null || value == null) {
            return null;
        }
        redisTemplate.opsForValue().set(getCacheKey(key), value, Duration.ofSeconds(SystemConstants.CACHE_EXPIRE));
        return value;
    }

    @Override
    public V remove(K key) {
        if (log.isDebugEnabled()) log.debug("remove({})", key);
        if (key == null) return null;

        ValueOperations<K, V> vo = redisTemplate.opsForValue();
        V value = vo.get(getCacheKey(key));
        redisTemplate.delete(getCacheKey(key));
        return value;
    }

    @Override
    public void clear() {
        Objects.requireNonNull(redisTemplate.getConnectionFactory()).getConnection().flushDb();
    }

    @Override
    public int size() {
        Long len = Objects.requireNonNull(redisTemplate.getConnectionFactory()).getConnection().dbSize();
        return Objects.requireNonNull(len).intValue();
    }

    @Override
    @SuppressWarnings(value = "unchecked")
    public Set<K> keys() {
        Set<K> set = redisTemplate.keys((K) "*");
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
            values.add(redisTemplate.opsForValue().get(k));
        }
        return values;
    }
}
