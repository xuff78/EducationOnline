package com.education.online.retrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Administrator on 2016/8/12.
 */
public interface IHandler {
    @GET("users")
    Call<String> initData();
}
