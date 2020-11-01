package com.github.yuzhian.zero.server.common.global;

/**
 * @author yuzhian
 * @since 2020-10-31
 */
public interface RegExConstants {
    /**
     * 正则: 邮箱
     */
    String EMAIL = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";
    /**
     * 正则: 手机号
     */
    String MOBILE = "^[1]\\d{10}$";

}
