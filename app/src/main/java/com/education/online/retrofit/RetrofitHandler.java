package com.education.online.retrofit;

import android.content.Context;

import com.education.online.http.CallBack;
import com.education.online.http.Method;
import com.education.online.util.Constant;
import com.education.online.util.LeanSignatureUtil;

import java.util.HashMap;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Retrofit;

/**
 * Created by Administrator on 2016/10/8.
 */
public class RetrofitHandler {

    public static void login(Context mContext, String phone, String password, RCallBack cb){
        HashMap<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("phone", phone);
        paramMap.put("password", password);
        String body= LeanSignatureUtil.sign(mContext, Constant.API_Url_User+ Method.Login, paramMap);
        Retrofit retrofit=RetrofitAPIManager.getRetrofit(mContext);
        IHandler rshandler=retrofit.create(IHandler.class);
        Call<String> call = rshandler.login(RequestBody.create(null, body));
        call.enqueue(cb);
    }

}
