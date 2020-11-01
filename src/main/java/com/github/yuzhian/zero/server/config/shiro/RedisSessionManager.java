package com.github.yuzhian.zero.server.config.shiro;

import com.github.yuzhian.zero.server.common.global.SystemConstants;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SessionKey;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.session.mgt.WebSessionKey;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.Serializable;

/**
 * @author yuzhian
 * @since 2020-10-31
 */
public class RedisSessionManager extends DefaultWebSessionManager {

    @Override
    protected Serializable getSessionId(ServletRequest request, ServletResponse response) {
        return WebUtils.toHttp(request).getHeader(SystemConstants.AUTHORIZATION);
    }

    /**
     * 优化单次请求需要多次访问redis的问题
     */
    @Override
    protected Session retrieveSession(SessionKey sessionKey) {
        Serializable sessionId = getSessionId(sessionKey);
        ServletRequest request = null;
        if (sessionKey instanceof WebSessionKey) {
            request = ((WebSessionKey) sessionKey).getServletRequest();
        }

        if (null != request && null != sessionId) {
            Object sessionObj = request.getAttribute(sessionId.toString());
            if (null != sessionObj) {
                return (Session) sessionObj;
            }
        }
        Session session = super.retrieveSession(sessionKey);
        if (request != null && null != sessionId) {
            request.setAttribute(sessionId.toString(), session);
        }
        return session;
    }
}
