package com.github.yuzhian.zero.server.config.shiro;

import com.github.yuzhian.zero.server.common.global.SystemConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Collection;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author yuzhian
 * @since 2020-10-31
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class RedisCacheSessionDAO extends EnterpriseCacheSessionDAO {
    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = super.doCreate(session);
        redisTemplate.opsForValue().set(SystemConstants.SESSION_PREFIX + sessionId.toString(), session);
        if (log.isDebugEnabled()) {
            log.debug("session create, key: {}, value: {}", sessionId, session);
        }
        return sessionId;
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        Session session = super.doReadSession(SystemConstants.SESSION_PREFIX + sessionId.toString());
        if (Objects.nonNull(session)) {
            if (log.isDebugEnabled()) {
                log.debug("session read by super, key: {}, value: {}", sessionId, session);
            }
            return session;
        }
        session = (Session) redisTemplate.opsForValue().get(SystemConstants.SESSION_PREFIX + sessionId.toString());
        if (log.isDebugEnabled()) {
            log.debug("session read by redis, key: {}, value: {}", sessionId, session);
        }
        return session;
    }

    @Override
    protected void doUpdate(Session session) {
        super.doUpdate(session);
        String key = SystemConstants.SESSION_PREFIX + session.getId().toString();
        if (redisTemplate.hasKey(key) != Boolean.TRUE) {
            redisTemplate.opsForValue().set(key, session);
        }
        redisTemplate.expire(key, SystemConstants.SESSION_EXPIRE, TimeUnit.SECONDS);
        if (log.isDebugEnabled()) {
            log.debug("session update, key: {}, value: {}", session.getId(), session);
        }
    }

    @Override
    protected void doDelete(Session session) {
        super.doDelete(session);
        redisTemplate.delete(SystemConstants.SESSION_PREFIX + session.getId().toString());
        if (log.isDebugEnabled()) {
            log.debug("session delete, key: {}", session.getId());
        }
    }

    @Override
    public Collection<Session> getActiveSessions() {
        Collection<Session> sessions = super.getActiveSessions();
        if (log.isDebugEnabled()) {
            log.debug("session active size: {}", sessions.size());
        }
        return sessions;
    }

}