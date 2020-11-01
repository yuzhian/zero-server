package com.github.yuzhian.zero.server.config.shiro;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @author yuzhian
 * @since 2020-10-31
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class RedisCacheManager implements CacheManager {
    @SuppressWarnings("rawtypes")
    private final RedisTemplate redisTemplate;

    @Override
    public <K, V> Cache<K, V> getCache(String prefix) {
        log.info("RedisCacheManager.getCache:prefix: {}", prefix);
        return new RedisCache<>(redisTemplate);
    }
}
