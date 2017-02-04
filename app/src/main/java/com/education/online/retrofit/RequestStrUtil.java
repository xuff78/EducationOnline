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

    public static RequestBody getLoginStr(Context mContext, String phone, String password){
        HashMap<String, String> paramMap = new HashMap<>();
        paramMap.put("phone", phone);
        paramMap.put("password", password);
        String body= LeanSignatureUtil.sign(mContext, Constant.API_Url_User+ Method.Login, paramMap);
        return RequestBody.create(null, body);
    }
}
