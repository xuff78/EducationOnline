package com.education.online.util;

import android.os.Environment;

/**
 * Created by 可爱的蘑菇 on 2016/8/14.
 */
public class Constant {

    public static final String API_Url_User="http://106.75.5.58:43320/";
    public static final String API_Url_Service="http://106.75.5.58:43330/";
    public static final String Url_API="url_api";
    public static final String Product_code="Product_code";
    public static final String App_Channel="app_channel";
    public static final String App_Key="app_key";
    public static final String App_Secret="app_secret";
    public static final String Pic_Savepath="picSavePath";
    public static final String SearchWords="SearchWords";
    public static final String UserInfo="UserInfo";
    public static final String UserName="UserName";
    public static final String SavePath = Environment.getExternalStorageDirectory().getAbsolutePath()
            + "/EduOL";

    //微信的
    public static final String WXAppId="WXAppId";
    public static final String WXAppSecret="WXAppSecret";


    public static final String API_BASE = "ih.base.";

    public class Method{
        public static final String init=API_BASE + "sys.init";
    }
}
