package com.github.yuzhian.zero.server.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.yuzhian.zero.server.system.model.SysRole;

import java.util.List;

/**
 * <p>
 * 角色 Mapper 接口
 * </p>
 *
 * @author yuzhian
 * @since 2020-10-30
 */
public interface SysRoleMapper extends BaseMapper<SysRole> {
    List<String> listRoleByUserId(String userId);
}
