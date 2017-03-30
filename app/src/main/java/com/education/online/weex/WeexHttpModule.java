package com.education.online.weex;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;

import com.education.online.act.CourseMainPage;
import com.taobao.weex.common.WXModule;
import com.taobao.weex.common.WXModuleAnno;

/**
 * Created by Administrator on 2017/3/16.
 */

public class WeexHttpModule extends WXModule {

    @WXModuleAnno(moduleMethod = true,runOnUIThread = true)
    public void sendHttpRequest(String jsonData) {
//        try {
//            Subscription subscription = RetrofitAPIManager.getUserRetrofit(mWXSDKInstance.getContext())
//                    .login()
//                    .subscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe(callBack);
//        }catch (Exception e){}
    }
}
