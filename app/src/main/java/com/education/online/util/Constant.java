package com.education.online.util;

import android.os.Environment;

/**
 * Created by 可爱的蘑菇 on 2016/8/14.
 */
public class Constant {

    public static final String API_Url_User="http://106.75.5.58:43320/";
    public static final String API_Url_Service="http://106.75.5.58:43330/";
    public static final String API_Url_Payment="http://106.75.5.58:44330/";


    public static final String Url_API="url_api";
    public static final String Product_code="Product_code";
    public static final String App_Channel="app_channel";
    public static final String App_Key="app_key";
    public static final String App_Secret="app_secret";
    public static final String Pic_Savepath="picSavePath";
    public static final String SearchWords="SearchWords";
    public static final String SearchSubject="SearchSubject";
    public static final String UserInfo="UserInfo";
    public static final String NickName="NickName";
    public static final String Avatar="Avatar";
    public static final String UserName="UserName";
    public static final String UserPSW="UserPSW";
    public static final String UserCode="UserCode";
    public static final String UserIdentity="user_identity";
    public static final String SavePath = Environment.getExternalStorageDirectory().getAbsolutePath()
            + "/EduOL";
    public static final String SubjectId="SubjectId";
    public static final String SubjectName="SubjectName";

    public static final String Lat="Lat";
    public static final String Lon="Lon";

    public static final String TypeTeacher="teacher";
    public static final String TypeCourse="course";

    //微信的
    public static final String WXAppId="WXAppId";
    public static final String WXAppSecret="WXAppSecret";


    public static final String API_BASE = "ih.base.";
    public static final String Addr = "Addr";

    public static final int pouseDownload = 1;
    public static final int resumeDownload = 2;
    public static final int finishDownload = 3;
    public static final int errorDownload = 4;
    public static final int updateDownload = 5;

    /** 支付宝支付业务：入参app_id */
    public static final String APPID = "";
    /** 支付宝账户登录授权业务：入参pid值 */
    public static final String PID = "";
    /** 支付宝账户登录授权业务：入参target_id值 */
    public static final String TARGET_ID = "";
    /** 商户私钥，pkcs8格式 */
    public static final String RSA_PRIVATE = "";

    public class Method{
        public static final String init=API_BASE + "sys.init";
    }
}
