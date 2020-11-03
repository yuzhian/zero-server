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

    private String getSessionId(Serializable sessionId) {
        return SystemConstants.SESSION_PREFIX + sessionId.toString();
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        if (log.isDebugEnabled()) log.debug("read({})", sessionId);
        Session session = super.doReadSession(getSessionId(sessionId));
        if (Objects.nonNull(session)) {
            return session;
        }
        session = (Session) redisTemplate.opsForValue().get(getSessionId(sessionId));
        return session;
    }

    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = super.doCreate(session);
        if (log.isDebugEnabled()) log.debug("create({}, {})", session.getId(), session);
        redisTemplate.opsForValue().set(getSessionId(sessionId), session);
        return sessionId;
    }

    @Override
    protected void doUpdate(Session session) {
        super.doUpdate(session);
        if (log.isDebugEnabled()) log.debug("update({}, {})", session.getId(), session);
        String key = getSessionId(session.getId());
        if (redisTemplate.hasKey(key) != Boolean.TRUE) {
            redisTemplate.opsForValue().set(key, session);
        }
        redisTemplate.expire(key, SystemConstants.SESSION_EXPIRE, TimeUnit.SECONDS);
    }

    @Override
    protected void doDelete(Session session) {
        super.doDelete(session);
        if (log.isDebugEnabled()) log.debug("delete({}, {})", session.getId(), session);
        redisTemplate.delete(getSessionId(session.getId()));
    }

    @Override
    public Collection<Session> getActiveSessions() {
        return super.getActiveSessions();
    }
}
