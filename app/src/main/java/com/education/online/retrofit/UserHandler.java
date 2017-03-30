package com.education.online.retrofit;


import com.education.online.bean.LoginInfo;
import com.education.online.http.Method;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by Administrator on 2016/8/12.
 */
public interface UserHandler {

    @POST(Method.Login)
    Observable<HttpResult<LoginInfo>> login(@Body RequestBody signStr);

    @FormUrlEncoded
    @POST(Method.Login)
    Observable<HttpResult<LoginInfo>> login(@Field("phone") String phone, @Field("password") String password,
                                            @Field("leancloud_id") String leancloud_id);

}
