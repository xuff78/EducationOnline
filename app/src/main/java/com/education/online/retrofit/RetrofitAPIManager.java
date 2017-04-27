package com.education.online.retrofit;

import android.app.Activity;
import android.content.Context;

import com.education.online.retrofit.Converter.FastJsonConverterFactory;
import com.education.online.retrofit.Converter.StringConverterFactory;
import com.education.online.util.Constant;
import com.education.online.util.LogUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

/**
 * Created by Administrator on 2016/8/12.
 */
public class RetrofitAPIManager {

    private static Converter.Factory strConverterFactory = StringConverterFactory.create();
    private static Converter.Factory jsonConverterFactory = FastJsonConverterFactory.create();
    private static CallAdapter.Factory rxJavaCallAdapterFactory = RxJavaCallAdapterFactory.create();

    private static UserHandler userHandler;

    public static UserHandler getUserRetrofit(Context con) {
        if(userHandler==null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constant.API_Url_User)
                    .client(genericClient(con, Constant.API_Url_User))
                    .addConverterFactory(jsonConverterFactory)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .build();
            userHandler = retrofit.create(UserHandler.class);
        }
        return userHandler;
    }

    public static UserHandler getUserRetrofit(String url) {
        if(userHandler==null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(jsonConverterFactory)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .build();
            userHandler = retrofit.create(UserHandler.class);
        }
        return userHandler;
    }

    private static OkHttpClient mOkHttpClient =null;

    private static OkHttpClient genericClient(final Context con, final String API_Url_User) {
        if(mOkHttpClient==null) {
            mOkHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(20, TimeUnit.SECONDS)//设置超时时间
                    .addInterceptor(new Interceptor() {
                        @Override
                        public Response intercept(Chain chain) throws IOException {
                            Request request = chain.request();
                            Request.Builder requestBuilder = request.newBuilder()
                                    .addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
                                    .addHeader("Connection", "keep-alive")
                                    .addHeader("Accept", "*/*")
                                    .addHeader("Cookie", "balabala");

                            //加基础参数和签名
                            if(request.body() instanceof FormBody) {
                                FormBody oldFormBody = (FormBody) request.body();
                                Map<String, String> params=new HashMap<>();
                                for (int i = 0; i < oldFormBody.size(); i++) {
                                    params.put(oldFormBody.encodedName(i), oldFormBody.encodedValue(i));
                                }
                                FormBody.Builder newFormBody=LeanSignatureUtil.sign(con, API_Url_User, params);
                                requestBuilder.method(request.method(), newFormBody.build());

                                Request newRequest = requestBuilder.build();
                                return chain.proceed(newRequest);
                            }else{
                                return chain.proceed(request);
                            }
                        }
                    })
                    .build();
        }
        return mOkHttpClient;
    }
}
