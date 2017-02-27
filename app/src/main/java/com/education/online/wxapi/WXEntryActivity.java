package com.education.online.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.education.online.R;
import com.education.online.util.Constant;
import com.education.online.util.LogUtil;
import com.education.online.util.SharedPreferencesUtil;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

/**
 * Created by Administrator on 2016/7/20.
 */
public class WXEntryActivity extends Activity implements IWXAPIEventHandler {

    private IWXAPI api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);

        if(api==null){
            //wx30a9402cd2a87cf3
            api= WXAPIFactory.createWXAPI(this, Constant.WXAppId, false);
            api.registerApp(Constant.WXAppId);
        }

        api.handleIntent(getIntent(), this);
        handleIntent(getIntent());
    }

    // 微信发送请求到第三方应用时，会回调到该方法
    @Override
    public void onReq(BaseReq req) {
        switch (req.getType()) {
            case ConstantsAPI.COMMAND_GETMESSAGE_FROM_WX:
//          goToGetMsg();
                break;
            case ConstantsAPI.COMMAND_SHOWMESSAGE_FROM_WX:
//          goToShowMsg((ShowMessageFromWX.Req) req);
                break;
            default:
                break;
        }
    }

    // 第三方应用发送到微信的请求处理后的响应结果，会回调到该方法
    @Override
    public void onResp(BaseResp resp) {
//      int result = 0;
        String result="";

        LogUtil.i("WeXinShare", "onPayFinish, errCode = " + resp.errCode+"   "+resp.errStr);
        switch (resp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                String code = ((SendAuth.Resp) resp).code; //即为所需的code
                SharedPreferencesUtil.setString(WXEntryActivity.this, Constant.WXLOGIN_CODE, code);
                result = "errcode_success";
//                SharedPreferencesUtil.setString(WXEntryActivity.this, GlbsProp.WXLOGIN_CODE, resp.errCode);
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                result = "errcode_cancel";
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                result = "errcode_deny";
                break;
            default:
                result = "errcode_unknown";
                break;
        }
        finish();
    }



    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        SendAuth.Resp resp = new SendAuth.Resp(intent.getExtras());
        if (resp.errCode == BaseResp.ErrCode.ERR_OK) {
            //用户同意
        }
    }
}
