package com.github.yuzhian.zero.server.config.shiro;

import com.github.yuzhian.zero.server.common.global.SystemConstants;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.Map;

/**
 * @author yuzhian
 * @since 2020-10-31
 */
@Configuration
public class ShiroConfig {

    /**
     * 凭证匹配器: 自定义密码匹配规则, 方式 次数{@link SystemConstants#HASH_ITERATIONS})
     */
    @Bean
    public CredentialsMatcher credentialsMatcher() {
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
        matcher.setHashAlgorithmName("md5");
        matcher.setHashIterations(SystemConstants.HASH_ITERATIONS);
        return matcher;
    }

    /**
     * 权限验证: 指定凭证匹配器{@link #credentialsMatcher}
     */
    @Bean
    public AuthorizingRealm authorizingRealm(@Qualifier("credentialsMatcher") CredentialsMatcher credentialsMatcher) {
        AuthorizingRealm authorizingRealm = new SystemAuthorizingRealm();
        authorizingRealm.setCredentialsMatcher(credentialsMatcher);
        return authorizingRealm;
    }

    /**
     * 会话管理器: 配置 Session 实现{@link RedisCacheSessionDAO}
     */
    @Bean
    public SessionManager sessionManager(@Qualifier("redisCacheSessionDAO") SessionDAO sessionDAO,
                                         @Qualifier("redisCacheManager") CacheManager cacheManager) {
        DefaultWebSessionManager sessionManager = new RedisSessionManager();
        sessionManager.setSessionDAO(sessionDAO);
        sessionManager.setCacheManager(cacheManager);
        return sessionManager;
    }

    /**
     * 安全管理器
     *
     * @param authorizingRealm {@link #authorizingRealm}
     * @param sessionManager   {@link RedisSessionManager}
     */
    @Bean
    public DefaultSecurityManager securityManager(@Qualifier("authorizingRealm") AuthorizingRealm authorizingRealm,
                                                  @Qualifier("sessionManager") SessionManager sessionManager) {
        DefaultSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(authorizingRealm);
        securityManager.setSessionManager(sessionManager);
        return securityManager;
    }

    /**
     * 过滤规则
     */
    @Bean
    public ShiroFilterFactoryBean shirFilter(@Qualifier("securityManager") SecurityManager securityManager) {
        ShiroFilterFactoryBean factory = new ShiroFilterFactoryBean();
        // 管理器
        factory.setSecurityManager(securityManager);

        // 过滤器
        Map<String, Filter> filters = factory.getFilters();
        filters.put("token", new TokenFilter());
        factory.setFilters(filters);

        // // 权限配置, LinkedHashMap 保证插入顺序
        // Map<String, String> roleMap = new LinkedHashMap<>();
        // roleMap.put("/system/account/login", "anon");       // login
        // roleMap.put("/system/account/register", "anon");    // register
        // roleMap.put("/druid/**", "anon");                   // druid
        // roleMap.put("/swagger-ui/**", "anon");              // swagger
        // roleMap.put("/swagger-resources/**", "anon");
        // roleMap.put("/v3/**", "anon");
        // roleMap.put("/**", "token");
        factory.setFilterChainDefinitionMap(Map.of("/**", "anon"));
        return factory;
    }

    /**
     * 注解支持
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(
            @Qualifier("securityManager") SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }
}
