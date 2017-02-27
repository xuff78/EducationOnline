package com.education.online.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.education.online.R;
import com.education.online.util.Constant;
import com.education.online.util.LogUtil;
import com.education.online.util.SharedPreferencesUtil;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

/**
 * Created by Administrator on 2016/7/26.
 */
public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    private static final String TAG = "WeXinPay";

    private IWXAPI api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        api = WXAPIFactory.createWXAPI(this, Constant.WXAppId);
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
    }

    @Override
    public void onResp(BaseResp resp) {
        LogUtil.i(TAG, "onPayFinish, errCode = " + resp.errCode+"   "+resp.errStr);
        SharedPreferencesUtil.setInt(this, Constant.WXPayResult, resp.errCode);
        finish();
    }
}
