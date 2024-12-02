package org.green.career.config;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Config {

    public static String md5Util(String pw) {
        try{
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(pw.getBytes());
            byte[] digest = md.digest();
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02x", b & 0xff));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
