package com.education.online;

import android.os.StrictMode;
import android.support.multidex.MultiDexApplication;

import com.avos.avoscloud.AVOSCloud;
import com.baidu.mapapi.SDKInitializer;
import com.education.online.util.SharedPreferencesUtil;


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


        SDKInitializer.initialize(getApplicationContext());
        SharedPreferencesUtil.setSessionid(this, SharedPreferencesUtil.FAILURE_STRING);

        String appId =  "V6AsD9B9FIFsF3nrpsJUJ5px-gzGzoHsz";
        String appKey = "RCycwUSj1KsLTg1HDTVYxRa9";

        AVOSCloud.initialize(this, appId, appKey);
        AVOSCloud.setLastModifyEnabled(true);

        AVOSCloud.setDebugLogEnabled(debug);
//        AVAnalytics.enableCrashReport(this, !debug);
        if (EduApplication.debug) {
            openStrictMode();
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
