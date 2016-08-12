package com.education.online.util;

import android.util.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by Administrator on 2016/8/12.
 */
public class HmacSha {
    private static final String UTF8 = "UTF-8";
    private static final String EMPTY_STRING = "";
    private static final String CARRIAGE_RETURN = "\r\n";
    private static final String HMAC_SHA1 = "HmacSHA1";

    public HmacSha() {
    }

    public static String getSignature(String baseString, String appSecret) throws Exception {
        return doSign(baseString, appSecret);
    }

    private static String doSign(String toSign, String keyString) throws Exception {
        SecretKeySpec key = new SecretKeySpec(keyString.getBytes("UTF-8"), "HmacSHA1");
        Mac mac = Mac.getInstance("HmacSHA1");
        mac.init(key);
        byte[] bytes = mac.doFinal(toSign.getBytes("UTF-8"));
        return (new String(Base64.encode(bytes, Base64.DEFAULT))).replace("\r\n", "");
    }
}
