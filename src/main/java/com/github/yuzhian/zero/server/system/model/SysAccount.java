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
 * 帐号
 * </p>
 *
 * @author yuzhian
 * @since 2020-10-30
 */
@Data
@TableName("sys_account")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@ApiModel(value = "SysAccount", description = "帐号")
public class SysAccount implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId
    @ApiModelProperty(value = "用户主键")
    private String userId;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @JsonIgnore
    @ApiModelProperty(value = "密码")
    private String password;

    @JsonIgnore
    @ApiModelProperty(value = "密码盐")
    private String salt;

    @JsonIgnore
    @TableLogic
    @ApiModelProperty(value = "删除标记(0正常 1删除)")
    private Boolean delFlag;
}
