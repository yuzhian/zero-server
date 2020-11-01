package com.github.yuzhian.zero.server.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.yuzhian.zero.server.system.dto.AuthenticationDTO;
import com.github.yuzhian.zero.server.system.dto.RegisterDTO;
import com.github.yuzhian.zero.server.system.model.SysAccount;

/**
 * <p>
 * 帐号 服务类
 * </p>
 *
 * @author yuzhian
 * @since 2020-10-30
 */
public interface ISysAccountService extends IService<SysAccount> {

    AuthenticationDTO getAuthentication(String account);

    boolean register(RegisterDTO dto);
}
