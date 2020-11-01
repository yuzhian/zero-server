package com.github.yuzhian.zero.server.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.yuzhian.zero.server.common.global.ApiRuntimeException;
import com.github.yuzhian.zero.server.common.global.RegExConstants;
import com.github.yuzhian.zero.server.common.util.MD5Utils;
import com.github.yuzhian.zero.server.common.util.RegexUtils;
import com.github.yuzhian.zero.server.common.util.Sequence;
import com.github.yuzhian.zero.server.system.dto.AuthenticationDTO;
import com.github.yuzhian.zero.server.system.dto.RegisterDTO;
import com.github.yuzhian.zero.server.system.mapper.SysAccountMapper;
import com.github.yuzhian.zero.server.system.mapper.SysPermissionMapper;
import com.github.yuzhian.zero.server.system.mapper.SysRoleMapper;
import com.github.yuzhian.zero.server.system.model.SysAccount;
import com.github.yuzhian.zero.server.system.service.ISysAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 帐号 服务实现类
 * </p>
 *
 * @author yuzhian
 * @since 2020-10-30
 */
@Service
@RequiredArgsConstructor
public class SysAccountService extends ServiceImpl<SysAccountMapper, SysAccount> implements ISysAccountService {
    private final SysAccountMapper accountMapper;
    private final SysRoleMapper roleMapper;
    private final SysPermissionMapper permissionMapper;

    @Override
    public AuthenticationDTO getAuthentication(String loginName) {
        QueryWrapper<SysAccount> queryWrapper = new QueryWrapper<>();

        if (RegexUtils.isMatch(loginName, RegExConstants.EMAIL)) {
            queryWrapper.eq("email", loginName);
        } else if (RegexUtils.isMatch(loginName, RegExConstants.MOBILE)) {
            queryWrapper.eq("phone", loginName);
        } else {
            queryWrapper.eq("username", loginName);
        }
        SysAccount account = accountMapper.selectOne(queryWrapper);
        if (null == account) {
            throw new ApiRuntimeException(HttpStatus.FORBIDDEN, "账号不存在");
        }

        List<String> roles = roleMapper.listRoleByUserId(account.getUserId());
        List<String> permissions = permissionMapper.listPermissionByUserId(account.getUserId());

        AuthenticationDTO auth = new AuthenticationDTO();
        BeanUtils.copyProperties(account, auth);
        auth.setRoles(roles);
        auth.setPermissions(permissions);

        return auth;
    }

    @Override
    public boolean register(RegisterDTO dto) {
        if (this.count(new QueryWrapper<SysAccount>().eq("phone", dto.getPhone())) > 0) {
            throw new ApiRuntimeException(HttpStatus.CONFLICT, "手机号已注册");
        } else if (this.count(new QueryWrapper<SysAccount>().eq("email", dto.getEmail())) > 0) {
            throw new ApiRuntimeException(HttpStatus.CONFLICT, "邮箱已注册");
        } else if (this.count(new QueryWrapper<SysAccount>().eq("username", dto.getUsername())) > 0) {
            throw new ApiRuntimeException(HttpStatus.CONFLICT, "用户名已注册");
        }
        // 密码处理
        String[] pwdAndSalt = MD5Utils.generatePassword(dto.getPassword());
        SysAccount account = new SysAccount();
        BeanUtils.copyProperties(dto, account);
        account.setUserId(Sequence.getString());
        account.setPassword(pwdAndSalt[0]);
        account.setSalt(pwdAndSalt[1]);
        account.setDelFlag(false);
        this.save(account);
        // 未分配角色
        return true;
    }
}
