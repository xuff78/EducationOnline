package com.education.online.weex;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.education.online.R;
import com.education.online.util.LogUtil;
import com.taobao.weex.IWXRenderListener;
import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.common.WXRenderStrategy;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/15.
 */

public class AdvPageWeex extends AppCompatActivity implements IWXRenderListener {


    //    private static String TEST_URL = "http://dotwe.org/raw/dist/6fe11640e8d25f2f98176e9643c08687.bundle.js";
//    private static String TEST_URL = "http://106.75.91.154:8899";
//    private String defaultUrl = "http://172.16.10.66:8080/dist/advpage.js";  //本机
    private String defaultUrl = "http://106.75.91.154:8900/advpage.js"; //测试
    private WXSDKInstance mWXSDKInstance;
    private FrameLayout mContainer;
    private Uri mUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weex_page);

        String advert_id = getIntent().getStringExtra("advert_id");
        mContainer = (FrameLayout) findViewById(R.id.container);

        mWXSDKInstance = new WXSDKInstance(this);
        mWXSDKInstance.registerRenderListener(this);
        /**
         * pageName:自定义，一个标示符号。
         * url:远程bundle JS的下载地址
         * options:初始化时传入WEEX的参数，比如 bundle JS地址
         * flag:渲染策略。WXRenderStrategy.APPEND_ASYNC:异步策略先返回外层View，其他View渲染完成后调用onRenderSuccess。WXRenderStrategy.APPEND_ONCE 所有控件渲染完后后一次性返回。
         */
        Map<String, Object> options = new HashMap<>();
        options.put("advert_id", advert_id);
        if(getIntent().hasExtra("WeexData"))
            options=strToMap(getIntent().getStringExtra("WeexData"));
        if(getIntent().hasExtra("WeexUrl"))
            defaultUrl=getIntent().getStringExtra("WeexUrl");
        options.put(WXSDKInstance.BUNDLE_URL, defaultUrl);
        mWXSDKInstance.renderByUrl("EducationOnline",defaultUrl,options,null, WXRenderStrategy.APPEND_ONCE);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mWXSDKInstance != null) {
            mWXSDKInstance.onActivityStart();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mWXSDKInstance != null) {
            mWXSDKInstance.onActivityResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mWXSDKInstance != null) {
            mWXSDKInstance.onActivityPause();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mWXSDKInstance != null) {
            mWXSDKInstance.onActivityStop();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mWXSDKInstance != null) {
            mWXSDKInstance.onActivityDestroy();
        }
    }

    @Override
    public void onViewCreated(WXSDKInstance instance, View view) {
        if (view.getParent() != null) {
            ((ViewGroup) view.getParent()).removeView(view);
        }
        mContainer.addView(view);
    }

    @Override
    public void onRenderSuccess(WXSDKInstance instance, int width, int height) {

    }

    @Override
    public void onRefreshSuccess(WXSDKInstance instance, int width, int height) {

    }

    @Override
    public void onException(WXSDKInstance instance, String errCode, String msg) {

    }

    public Map<String, Object> strToMap(String mapStr) {
        String str1 = mapStr.replaceAll("\\{|\\}", "");//singInfo是一个map  toString后的字符串。
        String str2 = str1.replaceAll(" ", "");
        String str3 = str2.replaceAll(",", "&");


        Map<String, Object> map = null;
        if ((null != str3) && (!"".equals(str3.trim())))
        {
            String[] resArray = str3.split("&");
            if (0 != resArray.length)
            {
                map = new HashMap(resArray.length);
                for (String arrayStr : resArray) {
                    if ((null != arrayStr) && (!"".equals(arrayStr.trim())))
                    {
                        int index = arrayStr.indexOf("=");
                        if (-1 != index) {
                            map.put(arrayStr.substring(0, index), arrayStr.substring(index + 1));
                        }
                    }
                }
            }
        }
        return map;
    }
}
