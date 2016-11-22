package com.education.online;

import android.app.Application;
import android.os.StrictMode;
import android.support.multidex.MultiDexApplication;

import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.im.v2.AVIMMessageManager;
import com.baidu.mapapi.SDKInitializer;
import com.education.online.leanchat.AddRequest;
import com.education.online.leanchat.LeanchatUser;
import com.education.online.leanchat.PushManager;
import com.education.online.leanchat.UpdateInfo;
import com.education.online.util.SharedPreferencesUtil;

import cn.leancloud.chatkit.LCChatKit;

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

        LeanchatUser.alwaysUseSubUserClass(LeanchatUser.class);

        AVObject.registerSubclass(AddRequest.class);
        AVObject.registerSubclass(UpdateInfo.class);

        // 节省流量
        AVOSCloud.setLastModifyEnabled(true);

//        AVIMMessageManager.registerAVIMMessageType(LCIMRedPacketMessage.class);
//        AVIMMessageManager.registerAVIMMessageType(LCIMRedPacketAckMessage.class);
//        LCChatKit.getInstance().setProfileProvider(new LeanchatUserProvider());
        LCChatKit.getInstance().init(this, appId, appKey);

//        // 初始化红包操作
//        RedPacket.getInstance().initContext(ctx, RPConstant.AUTH_METHOD_SIGN);
//        //控制红包SDK中Log输出
//        RedPacket.getInstance().setDebugMode(false);

        PushManager.getInstance().init(ctx);
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
