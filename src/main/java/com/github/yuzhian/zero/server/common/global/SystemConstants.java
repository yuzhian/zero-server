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
     * shiro session 前缀
     */
    String SESSION_PREFIX = "shiro:session:";

    /**
     * shiro cache 前缀
     */
    String CACHE_PREFIX = "shiro:cache:";

    /**
     * shiro session 存活时间
     */
    long SESSION_EXPIRE = 86400L;

    /**
     * shiro cache 存活时间
     */
    long CACHE_EXPIRE = 86700L;
}
