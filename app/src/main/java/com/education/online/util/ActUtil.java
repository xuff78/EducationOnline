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
        SharedPreferencesUtil.setString(con, Constant.Url_API,
//					"https://cafe.smallpay.com/platform/api");
                "http://182.48.115.38:8088/platform/api");
        SharedPreferencesUtil.setString(con, Constant.Product_code,
                "1100");
        SharedPreferencesUtil.setString(con, Constant.App_Channel,
                "10002");
        SharedPreferencesUtil.setString(con, Constant.App_Key,
                "600025");
        SharedPreferencesUtil.setString(con, Constant.WXAppId,
                "wxc1fdc3e0a84ff533");
        SharedPreferencesUtil.setString(con, Constant.WXAppSecret,
                "94a1f4de4f5011e6b0ee525400b263eb");
        SharedPreferencesUtil.setString(con, Constant.App_Secret,
                "9af39ddG3jfld3B5J0mE8jd6hHi3k8al");
        SharedPreferencesUtil.setString(con, Constant.Pic_Savepath,
                Constant.SavePath);

    }
}
