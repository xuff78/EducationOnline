package com.education.online.retrofit;

import android.content.Context;

import com.education.online.util.Constant;
import com.education.online.util.SharedPreferencesUtil;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by Administrator on 2016/8/12.
 */
public class RetrofitAPIManager {

    private static Converter.Factory strConverterFactory = StringConverterFactory.create();
    private static CallAdapter.Factory rxJavaCallAdapterFactory = RxJavaCallAdapterFactory.create();

    private static UserHandler userHandler;

    public static UserHandler getUserRetrofit() {
        if(userHandler==null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constant.API_Url_User)
                    .client(genericClient())
                    .addConverterFactory(strConverterFactory)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .build();
            userHandler = retrofit.create(UserHandler.class);
        }
        return userHandler;
    }

    private static OkHttpClient mOkHttpClient =null;

    private static OkHttpClient genericClient() {
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
