package com.education.online.act;

import android.app.Application;

import com.baidu.mapapi.SDKInitializer;
import com.education.online.util.ActUtil;

/**
 * Created by Administrator on 2016/9/29.
 */
public class EduApplication extends Application {
    //    public BDLocationService locationService;
    @Override
    public void onCreate() {
        super.onCreate();
        //注意该方法要再setContentView方法之前实现
        SDKInitializer.initialize(getApplicationContext());
        // 初始化参数依次为 this, AppId, AppKey
//        AVOSCloud.initialize(this, "0yg9vTAmHqYg9PSLnwhGMbyR", "MVG6f0AWlXSQr9BVs7g0301Y");
//        ChatManager.setDebugEnabled(true);
//        ChatManager.getInstance().init(this);
//        ActUtil.initImageLoader(getApplicationContext());
    }
}
