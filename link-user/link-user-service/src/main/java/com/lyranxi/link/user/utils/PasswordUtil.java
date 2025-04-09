package com.lyranxi.link.user.utils;

import cn.hutool.crypto.symmetric.PBKDF2;

/**
 * 密码工具类
 *
 * @author ranxi
 * @date 2025-04-07 15:38
 */
public class PasswordUtil {

    /**
     * 加密算法 PBKDF2 with HmacSha1
     */
    private static final String ENCRYPT_ALGORITHM = "PBKDF2WithHmacSHA1";
    /**
     * 加密key 长度
     */
    private static final int ENCRYPT_KEY_LENGTH = 512;

    /**
     * 加密
     *
     * @param password 明文密码
     * @param salt     盐
     * @return 加密后的密码
     */
    public static String encrypt(String password, String salt) {
        PBKDF2 pbkdf2 = new PBKDF2(ENCRYPT_ALGORITHM, ENCRYPT_KEY_LENGTH, 1024);
        return pbkdf2.encryptHex(password.toCharArray(), salt.getBytes());
    }

}
