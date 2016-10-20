package com.education.online.util;

/**
 * Created by Great Gao on 2016/10/19.
 */
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
/**
 * 对外提供getSHA(String str)方法
 * @author randyjia
 *
 */
public class SHA {
    public static String getSHA(String val) throws NoSuchAlgorithmException{
        MessageDigest md5 = MessageDigest.getInstance("SHA-1");
        md5.update(val.getBytes());
        byte[] m = md5.digest();//加密
        return getString(m);
    }

    private static String getString(byte[] b){
        StringBuffer sb = new StringBuffer();
        for(int i = 0; i < b.length; i ++){
            sb.append(b[i]);
        }
        return sb.toString();
    }
}