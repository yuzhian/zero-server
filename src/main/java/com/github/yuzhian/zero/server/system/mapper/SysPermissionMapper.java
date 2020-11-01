package com.github.yuzhian.zero.server.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.yuzhian.zero.server.system.model.SysPermission;

import java.util.List;

/**
 * <p>
 * 权限 Mapper 接口
 * </p>
 *
 * @author yuzhian
 * @since 2020-10-30
 */
public interface SysPermissionMapper extends BaseMapper<SysPermission> {
    List<String> listPermissionByUserId(String userId);
}
