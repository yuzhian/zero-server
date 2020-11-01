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
 * 权限
 * </p>
 *
 * @author yuzhian
 * @since 2020-10-30
 */
@Data
@TableName("sys_permission")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@ApiModel(value = "SysPermission", description = "权限")
public class SysPermission implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId
    @ApiModelProperty(value = "权限主键")
    private String permissionId;

    @JsonIgnore
    @ApiModelProperty(value = "父权限主键")
    private String parentId;

    @ApiModelProperty(value = "权限名称")
    private String permission;

    @ApiModelProperty(value = "权限备注")
    private String description;

    @JsonIgnore
    @TableLogic
    @ApiModelProperty(value = "删除标记(0正常 1删除)")
    private Boolean delFlag;


}
