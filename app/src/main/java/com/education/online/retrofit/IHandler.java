package com.education.online.retrofit;

import com.education.online.http.Method;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by Administrator on 2016/8/12.
 */
public interface IHandler {

    @POST(Method.Login)
    Call<String> login(@Body RequestBody signStr);

}
