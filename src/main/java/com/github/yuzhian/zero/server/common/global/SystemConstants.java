package com.github.yuzhian.zero.server.common.global;

/**
 * @author yuzhian
 * @since 2020-10-31
 */
public interface SystemConstants {
    /**
     * 访问令牌(请求头中携带用于验证帐号信息)
     */
    String AUTHORIZATION = "token";
    /**
     * HASH 散列次数
     */
    int HASH_ITERATIONS = 2;
    /**
     * redis 存储 shiro session 前缀命名规则
     */
    String SESSION_PREFIX = "sessionX:";
    /**
     * redis 存储 shiro 过期时间, 秒
     */
    long SESSION_EXPIRE = 86400L;
}
