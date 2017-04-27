package com.education.online.retrofit;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;

import com.education.online.http.GlbsNet;
import com.education.online.util.DialogUtil;
import com.education.online.util.LogUtil;
import com.education.online.util.ToastUtils;

import rx.Observer;
import rx.Subscriber;
import rx.Subscription;

/**
 * Created by Administrator on 2016/10/8.
 */
public abstract class RCallBack<T> extends Subscriber<HttpResult<T>> {

    private Context act;
    private Dialog dialog=null;

    public RCallBack(Context act){
        this.act=act;
    }

    public RCallBack(Activity act, Dialog dialog){
        this.dialog=dialog;
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
        if(dialog!=null)
            dialog.dismiss();
        if(e!=null)
        LogUtil.i("TestDemo", "NetError: "+e.getMessage());
        DialogUtil.showInfoDailog(act, "提示", GlbsNet.HTTP_ERROR_MESSAGE);
    }

    @Override
    public void onStart() {
        if(dialog!=null)
            dialog.show();
    }

    @Override
    public void onNext(HttpResult<T> result) {
        if(result.getReturn_code()==0)
            doSuccess(result);
        else{
            doFailure(result);
        }
    }

    @Override
    public void onCompleted() {
        if(dialog!=null)
            dialog.dismiss();
    }
}
