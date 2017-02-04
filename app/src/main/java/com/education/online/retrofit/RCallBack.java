package com.education.online.retrofit;

import android.app.Activity;

import com.education.online.bean.JsonMessage;
import com.education.online.http.GlbsNet;
import com.education.online.util.DialogUtil;
import com.education.online.util.JsonUtil;
import com.education.online.util.LogUtil;

import rx.Observer;

/**
 * Created by Administrator on 2016/10/8.
 */
public class RCallBack implements Observer<String> {

    private Activity act;

    public RCallBack(Activity act){
        this.act=act;
    }

    private void oServerException(String jsonMessage) {
        DialogUtil.showInfoDailog(act, "提示", "服务器内部错误");
    }

    protected void doSuccess(String jsonData) {

    }

    protected void doFailure(JsonMessage jsonMessage) {
        DialogUtil.showInfoDailog(act, jsonMessage.getCode(), jsonMessage.getMsg());
    }

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
        DialogUtil.showInfoDailog(act, "提示", GlbsNet.HTTP_ERROR_MESSAGE);
    }

    @Override
    public void onNext(String jsonMessage) {
        LogUtil.i("resp", jsonMessage);
        JsonMessage msg= JsonUtil.getJsonMessage(jsonMessage);
        if(msg.getCode()==null){
            oServerException(jsonMessage);
        }else if(msg.getCode().equals("0"))
            doSuccess(JsonUtil.getJsonData(jsonMessage));
        else{
            doFailure(msg);
        }
    }
}
