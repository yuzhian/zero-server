package com.github.yuzhian.zero.server.system.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * 用户鉴权信息
 *
 * @author yuzhian
 * @since 2020-10-30
 */
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class AuthenticationDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String userId;

    private String username;

    private String email;

    private String phone;

    private String password;

    private String salt;

    private List<String> roles;

    private List<String> permissions;
}
