package com.github.yuzhian.zero.server.system.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Size;

/**
 * @author yuzhian
 * @since 2020-10-30
 */
@Data
@ApiModel(value = "LoginDTO", description = "登录请求参数")
public class LoginDTO {
    @ApiModelProperty(value = "帐号(用户名/邮箱/手机号")
    @Size(min = 4, max = 255, message = "帐号名长度在{min}-{max}之间")
    private String account;

    @ApiModelProperty(value = "密码")
    @Size(min = 6, max = 255, message = "密码长度在{min}-{max}之间")
    private String password;
}
