package com.education.online.retrofit;

import android.content.Context;

import com.education.online.util.SharedPreferencesUtil;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;

/**
 * Created by Administrator on 2016/8/12.
 */
public class RetrofitAPIManager {

    public static Retrofit getRetrofit(Context con) {
        String url=SharedPreferencesUtil.getString(con, "");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .client(genericClient())
                .build();
        return retrofit;
    }

    private static OkHttpClient mOkHttpClient =null;

    public static OkHttpClient genericClient() {
        if(mOkHttpClient==null) {
            mOkHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(new Interceptor() {
                        @Override
                        public Response intercept(Chain chain) throws IOException {
                            Request request = chain.request()
                                    .newBuilder()
                                    .addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
                                    .addHeader("Accept-Encoding", "gzip, deflate")
                                    .addHeader("Connection", "keep-alive")
                                    .addHeader("Accept", "*/*")
                                    .addHeader("Cookie", "add cookies here")
                                    .build();
                            return chain.proceed(request);
                        }
                    })
                    .build();
        }
        return mOkHttpClient;
    }
}
