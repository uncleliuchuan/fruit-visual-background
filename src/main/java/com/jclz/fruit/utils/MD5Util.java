package com.jclz.fruit.utils;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

public class MD5Util {

    /**
     * 对密码按照密码，盐进行加密
     *
     * @param password 密码
     * @param salt     盐
     * @return
     */
    public static String encryptPassword(String password, String salt) {
        return MD5Util.hash(password, salt);
    }

    private static String hash(String password, String salt) {
        String hashAlgorithmName = "md5";
        int hashIterations = 2;
        ByteSource credentialsSalt = ByteSource.Util.bytes(salt);//账号
        return new SimpleHash(hashAlgorithmName, password, credentialsSalt, hashIterations).toHex();
    }

}
