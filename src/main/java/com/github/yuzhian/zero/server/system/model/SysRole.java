package com.github.yuzhian.zero.server.system.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 角色
 * </p>
 *
 * @author yuzhian
 * @since 2020-10-30
 */
@Data
@TableName("sys_role")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@ApiModel(value = "SysRole", description = "角色")
public class SysRole implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId
    @ApiModelProperty(value = "角色主键")
    private String roleId;

    @ApiModelProperty(value = "角色名称")
    private String role;

    @ApiModelProperty(value = "角色介绍")
    private String description;

    @JsonIgnore
    @TableLogic
    @ApiModelProperty(value = "删除标记(0正常 1删除)")
    private Boolean delFlag;
}
