package com.github.yuzhian.zero.server.config.shiro;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.http.HttpStatus;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author yuzhian
 * @since 2020-10-31
 */
@Slf4j
public class TokenFilter extends BasicHttpAuthenticationFilter {

    /**
     * 访问控制, 判断是否允许通过
     *
     * @param request  servletRequest
     * @param response servletResponse
     * @param o        mappedValue
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object o) {
        Subject subject = getSubject(request, response);
        // return null != subject && subject.isAuthenticated();
        return null == subject || subject.isAuthenticated();
    }

    /**
     * 访问拒绝时处理
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) {
        HttpServletResponse httpServletResponse = WebUtils.toHttp(response);
        httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
        try {
            httpServletResponse.setContentType("text/plain; charset=utf-8");
            httpServletResponse.getWriter().write("令牌失效");
        } catch (IOException e) {
            log.error("token filter on access denied fail", e);
        }
        return false;
    }
}
