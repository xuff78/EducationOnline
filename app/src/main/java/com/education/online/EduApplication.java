package com.education.online;

import android.os.StrictMode;
import android.support.multidex.MultiDexApplication;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVInstallation;
import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.PushService;
import com.avos.avoscloud.SaveCallback;
import com.avoscloud.leanchatlib.controller.ChatManager;
import com.baidu.mapapi.SDKInitializer;
import com.education.online.act.discovery.SystemMessagePage;
import com.education.online.util.AppExceptionHanlder;
import com.education.online.util.Constant;
import com.education.online.util.LogUtil;
import com.education.online.util.SharedPreferencesUtil;
import com.education.online.weex.ImageAdapter;
import com.education.online.weex.WeexUtilModule;
import com.taobao.weex.InitConfig;
import com.taobao.weex.WXSDKEngine;
import com.taobao.weex.common.WXException;


/**
 * Created by 可爱的蘑菇 on 2016/11/20.
 */
public class EduApplication extends MultiDexApplication {
    public static boolean debug = true;
    public static EduApplication ctx;

    @Override
    public void onCreate() {
        super.onCreate();
        ctx = this;
//        Utils.fixAsyncTaskBug();



        AppExceptionHanlder crashHandler = AppExceptionHanlder.getInstance();
        crashHandler.init(getApplicationContext());
        SDKInitializer.initialize(getApplicationContext());
//        SharedPreferencesUtil.setSessionid(this, SharedPreferencesUtil.FAILURE_STRING);

        String appId =  "V6AsD9B9FIFsF3nrpsJUJ5px-gzGzoHsz";
        String appKey = "RCycwUSj1KsLTg1HDTVYxRa9";

        AVOSCloud.initialize(this, appId, appKey);
        ChatManager.setDebugEnabled(true);
        ChatManager.getInstance().init(getApplicationContext());
        AVOSCloud.setLastModifyEnabled(true);
        AVOSCloud.setDebugLogEnabled(debug);

        PushService.setDefaultPushCallback(this, SystemMessagePage.class);
        PushService.subscribe(this, "public", SystemMessagePage.class);
        AVInstallation.getCurrentInstallation().saveInBackground(new SaveCallback() {
            public void done(AVException e) {
                if (e == null) {
                    // 保存成功
                    String installationId = AVInstallation.getCurrentInstallation().getInstallationId();
                    LogUtil.i("PushId", "installationId: "+installationId);
                    SharedPreferencesUtil.setString(EduApplication.this, Constant.PushmsgId, installationId);
                } else {
                    // 保存失败，输出错误信息
                    SharedPreferencesUtil.setString(EduApplication.this, Constant.PushmsgId, SharedPreferencesUtil.FAILURE_STRING);
                }
            }
        });

        if (EduApplication.debug) {
            openStrictMode();
        }

        InitConfig config = new InitConfig.Builder().setImgAdapter(new ImageAdapter()).build();
        WXSDKEngine.initialize(this, config);
        try {
            WXSDKEngine.registerModule("wxmodule", WeexUtilModule.class);
        } catch (WXException e) {
            e.printStackTrace();
        }
    }

    public void openStrictMode() {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads()
                .detectDiskWrites()
                .detectNetwork()   // or .detectAll() for all detectable problems
                .penaltyLog()
                .build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects()
                .detectLeakedClosableObjects()
                .penaltyLog()
                //.penaltyDeath()
                .build());
    }

}
