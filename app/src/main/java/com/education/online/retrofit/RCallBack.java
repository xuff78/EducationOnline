package com.education.online.retrofit;

import android.app.Activity;

import com.education.online.http.GlbsNet;
import com.education.online.util.DialogUtil;

import rx.Observer;

/**
 * Created by Administrator on 2016/10/8.
 */
public abstract class RCallBack<T> implements Observer<HttpResult<T>> {

    private Activity act;
    private boolean showDialog=true;

    public RCallBack(Activity act){
        this.act=act;
    }

    public RCallBack(Activity act, boolean showDialog){
        this.showDialog=showDialog;
        this.act=act;
    }

    private void onServerException() {
        DialogUtil.showInfoDailog(act, "提示", "服务器内部错误");
    }

    public abstract void doSuccess(HttpResult<T> result);

    protected void doFailure(HttpResult<T> result) {
        DialogUtil.showInfoDailog(act, result.getReturn_code()+"", result.getReturn_message());
    }

    @Override
    public void onError(Throwable e) {
        DialogUtil.showInfoDailog(act, "提示", GlbsNet.HTTP_ERROR_MESSAGE);
    }

    @Override
    public void onNext(HttpResult<T> result) {
        if(result.getReturn_code()<0){
            onError(null);
//            onServerException();
        }else if(result.getReturn_code()==0)
            doSuccess(result);
        else{
            doFailure(result);
        }
    }

    @Override
    public void onCompleted() {}
}
