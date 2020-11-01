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
 * 帐号-角色
 * </p>
 *
 * @author yuzhian
 * @since 2020-10-30
 */
@Data
@TableName("sys_account_role")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@ApiModel(value = "SysAccountRole", description = "帐号-角色")
public class SysAccountRole implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId
    @ApiModelProperty(value = "用户角色主键")
    private String id;

    @ApiModelProperty(value = "用户主键")
    private String userId;

    @ApiModelProperty(value = "角色主键")
    private String roleId;
}
