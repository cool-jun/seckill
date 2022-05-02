package com.hj.seckill.utils;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Component;

@Component
public class MD5Util {
    private static final String salt = "1a2b3c4d";

    public static String md5(String str) {
        return DigestUtils.md5Hex(str);
    }

    public static String inputPasswordToBackendPassword(String inputPassword) {
        String str = salt.charAt(0) + salt.charAt(2) + inputPassword + salt.charAt(5);
        return md5(str);
    }

    public static String backendPasswordToDBPassword(String inputPassword, String salt) {
        String str = salt.charAt(0) + salt.charAt(2) + inputPassword + salt.charAt(5);
        return md5(str);
    }

    public static String inputPasswordToDBPassword(String inputPassword, String salt) {
        String backendPassword = inputPasswordToBackendPassword(inputPassword);
        String dbPassword = backendPasswordToDBPassword(backendPassword, salt);
        return dbPassword;
    }

    public static void main(String[] args) {
        //72100a46bad3df5e9a23ab6119c137f1
        System.out.println(inputPasswordToBackendPassword("123456"));
        //4267594346f8f1ef0d518d33126948d9
        System.out.println(backendPasswordToDBPassword("72100a46bad3df5e9a23ab6119c137f1", "1a2b3c4d"));
        //4267594346f8f1ef0d518d33126948d9
        System.out.println(inputPasswordToDBPassword("123456", "1a2b3c4d"));
    }
}
