package com.education.online.weex;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;

import com.education.online.act.CourseMainPage;
import com.education.online.act.login.LoginActivity;
import com.education.online.http.Method;
import com.education.online.retrofit.RetrofitAPIManager;
import com.education.online.util.Constant;
import com.education.online.util.SharedPreferencesUtil;
import com.taobao.weex.common.WXModule;
import com.taobao.weex.common.WXModuleAnno;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/3/15.
 */

public class WeexUtilModule extends WXModule {

    private static final String WEEX_CATEGORY = "com.taobao.android.intent.category.WEEX";

    @WXModuleAnno(moduleMethod = true,runOnUIThread = true)
    public void openURL(String url) {
        if (TextUtils.isEmpty(url)) {
            return;
        }
        String scheme = Uri.parse(url).getScheme();
        StringBuilder builder = new StringBuilder();
        if (TextUtils.equals("http",scheme) || TextUtils.equals("https",scheme) || TextUtils.equals("file",scheme)) {
            builder.append(url);
        } else {
            builder.append("http:");
            builder.append(url);
        }
        Uri uri = Uri.parse(builder.toString());
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.addCategory(WEEX_CATEGORY);
        mWXSDKInstance.getContext().startActivity(intent);
    }

    @WXModuleAnno(runOnUIThread = true)
    public void goCoursePage(String msg) {
        if(mWXSDKInstance.getContext() instanceof Activity) {
            Intent intent = new Intent(mWXSDKInstance.getContext(), CourseMainPage.class);
            intent.putExtra("url","weex/modules/send.js");
            mWXSDKInstance.getContext().startActivity(intent);
        }
    }

    @WXModuleAnno(moduleMethod = true,runOnUIThread = true)
    public void closePage() {
        if(mWXSDKInstance.getContext() instanceof Activity) {
            ((Activity) mWXSDKInstance.getContext()).finish();
        }
    }
}
