package com.github.yuzhian.zero.server.system.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 角色-权限
 * </p>
 *
 * @author yuzhian
 * @since 2020-10-30
 */
@Data
@TableName("sys_role_permission")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@ApiModel(value = "SysRolePermission", description = "角色-权限")
public class SysRolePermission implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId
    @ApiModelProperty(value = "角色权限主键")
    private String id;

    @ApiModelProperty(value = "角色主键")
    private String roleId;

    @ApiModelProperty(value = "权限主键")
    private String permissionId;
}
