package com.github.yuzhian.zero.server.common.util;

import com.github.yuzhian.zero.server.common.global.SystemConstants;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Md5Hash;

/**
 * 配合 shiro 框架做 MD5 加密
 *
 * @author yuzhian
 * @since 2020-10-31
 */
public class MD5Utils {
    /**
     * @param plaintext 明文
     * @return 第一个是密文 第二个是盐值
     */
    public static String[] generatePassword(String plaintext) {
        String salt = new SecureRandomNumberGenerator().nextBytes().toHex(); //生成盐值
        String cipherText = new Md5Hash(plaintext, salt, SystemConstants.HASH_ITERATIONS).toString(); //生成的密文
        return new String[]{cipherText, salt};
    }

    /**
     * @param plaintext 明文
     * @param salt      盐值
     * @return 密文
     */
    public static String encryptPassword(String plaintext, String salt) {
        if (salt == null) {
            salt = "";
        }
        return new Md5Hash(plaintext, salt, SystemConstants.HASH_ITERATIONS).toString(); //生成的密文
    }

}
