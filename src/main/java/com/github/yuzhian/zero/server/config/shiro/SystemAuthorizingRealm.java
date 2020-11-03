package com.github.yuzhian.zero.server.config.shiro;

import com.github.yuzhian.zero.server.system.dto.AuthenticationDTO;
import com.github.yuzhian.zero.server.system.service.ISysAccountService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.context.annotation.Lazy;

import javax.annotation.Resource;

/**
 * 系统认证授权配置
 *
 * @author yuzhian
 * @since 2020-10-31
 */
@Slf4j
public class SystemAuthorizingRealm extends AuthorizingRealm {

    @Resource
    @Lazy
    private ISysAccountService accountService;

    /**
     * 认证: 检查账号密码匹配
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) {
        AuthenticationDTO account = accountService.getAuthentication(token.getPrincipal().toString());
        if (log.isDebugEnabled()) log.debug("authentication: {}", account);
        if (null == account) return null;
        return new SimpleAuthenticationInfo(
                account,
                account.getPassword(),
                ByteSource.Util.bytes(account.getSalt()),
                getName()
        );
    }

    /**
     * 授权, 角色 / 权限 信息
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        AuthenticationDTO account = (AuthenticationDTO) principals.getPrimaryPrincipal();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addRoles(account.getRoles());
        info.addStringPermissions(account.getPermissions());
        if (log.isDebugEnabled()) log.debug("authorization: {}", account);
        return info;
    }
}
