package com.education.online.util;

import android.app.Activity;
import android.content.Context;

/**
 * Created by Administrator on 2016/8/12.
 */
public class ActUtil {

    public static void closeSoftPan(Activity activity) {


    }

    public static void initData(Context con) {
        SharedPreferencesUtil.setString(con, "url_api",
//					"https://cafe.smallpay.com/platform/api");
                "http://182.48.115.38:8088/platform/api");
        SharedPreferencesUtil.setString(con, "Produce_code",
                "1100");
        SharedPreferencesUtil.setString(con, "Produce_code_order",
                "1075");
        SharedPreferencesUtil.setString(con, "Produce_code_cbsticket",
                "1006");
        SharedPreferencesUtil.setString(con, "app_channel",
                "10002");
        SharedPreferencesUtil.setString(con, "Produce_code_phone",
                "3071");
        SharedPreferencesUtil.setString(con, "phone_channel_code",
                "1008");
        SharedPreferencesUtil.setString(con, "app_key",
                "610163");
        SharedPreferencesUtil.setString(con, "WXAppId",
                "wxc1fdc3e0a84ff533");
        SharedPreferencesUtil.setString(con, "WXAppSecret",
                "94a1f4de4f5011e6b0ee525400b263eb");
        SharedPreferencesUtil.setString(con, "app_secret",
                "vxGNKUiPyj1mcA8fg0bt6ZnLFCp4Y9zB");
    }
}
