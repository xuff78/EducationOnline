package com.education.online.retrofit;

import android.app.Activity;

import com.education.online.bean.JsonMessage;
import com.education.online.http.GlbsNet;
import com.education.online.util.DialogUtil;
import com.education.online.util.JsonUtil;
import com.education.online.util.LogUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2016/10/8.
 */
public class RCallBack implements Callback<String> {

    private Activity act;

    public RCallBack(Activity act){
        this.act=act;
    }

    @Override
    public void onResponse(Call<String> call, Response<String> response) {
        String jsonMessage=response.body();
        LogUtil.i("resp", response.body());
        JsonMessage msg= JsonUtil.getJsonMessage(jsonMessage);
        if(msg.getCode()==null){
            oServerException(jsonMessage);
        }else if(msg.getCode().equals("0"))
            doSuccess(JsonUtil.getJsonData(jsonMessage));
        else{
            doFailure(msg);
        }
    }

    private void oServerException(String jsonMessage) {
        DialogUtil.showInfoDailog(act, "提示", "服务器内部错误");
    }

    private void doSuccess(String jsonData) {

    }

    private void doFailure(JsonMessage jsonMessage) {
        DialogUtil.showInfoDailog(act, jsonMessage.getCode(), jsonMessage.getMsg());
    }


    @Override
    public void onFailure(Call<String> call, Throwable t) {
        DialogUtil.showInfoDailog(act, "提示", GlbsNet.HTTP_ERROR_MESSAGE);
    }
}
