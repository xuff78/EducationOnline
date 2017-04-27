package com.education.online.retrofit;

import android.content.Context;

import com.education.online.http.Method;
import com.education.online.util.Constant;
import com.education.online.util.LeanSignatureUtil;

import java.util.HashMap;

import okhttp3.RequestBody;

/**
 * Created by Administrator on 2017/2/4.
 */

public class RequestStrUtil {

    public static RequestBody getRequestBody(Context mContext, String url, HashMap<String, String> paramMap){
        RequestBody body= LeanSignatureUtil.signOkhttp(mContext, url, paramMap);
        return body;
    }
}
