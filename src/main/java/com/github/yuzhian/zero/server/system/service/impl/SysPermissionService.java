package com.github.yuzhian.zero.server.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.yuzhian.zero.server.system.mapper.SysPermissionMapper;
import com.github.yuzhian.zero.server.system.model.SysPermission;
import com.github.yuzhian.zero.server.system.service.ISysPermissionService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 权限 服务实现类
 * </p>
 *
 * @author yuzhian
 * @since 2020-10-30
 */
@Service
public class SysPermissionService extends ServiceImpl<SysPermissionMapper, SysPermission> implements ISysPermissionService {

}
