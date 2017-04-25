package com.education.online.act.Mine;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.EnvUtils;
import com.alipay.sdk.app.PayTask;
import com.education.online.R;
import com.education.online.act.BaseFrameAct;
import com.education.online.act.order.OrderPay;
import com.education.online.act.order.PaymentCompletePage;
import com.education.online.bean.PayResult;
import com.education.online.http.CallBack;
import com.education.online.http.HttpHandler;
import com.education.online.http.Method;
import com.education.online.util.ActUtil;
import com.education.online.util.Constant;
import com.education.online.util.JsonUtil;
import com.education.online.util.SharedPreferencesUtil;
import com.education.online.util.ToastUtils;
import com.education.online.view.PayTypeDialog;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import org.json.JSONException;

import java.util.Map;

/**
 * Created by Administrator on 2016/9/26.
 */
public class MyWalletCharge extends BaseFrameAct implements View.OnClickListener{

    private EditText rechargePrice;
    private PayTypeDialog dialog;
    private View checkedIcon=null;
    private View checkIcon2, checkIcon3, checkIcon4;
    private HttpHandler httpHandler;
    private final int SDK_PAY_FLAG=1;
    private final int SDK_AUTH_FLAG=2;
    private enum STATUS {
        UnionPay, WeChat, AliPay
    }
    private STATUS status = STATUS.WeChat;

    public void initHandler() {
        httpHandler = new HttpHandler(this, new CallBack(this) {
            @Override
            public void doSuccess(String method, final String jsonData) throws JSONException {
                super.doSuccess(method, jsonData);
                if (method.equals(Method.rechargeWallet)) {
                    if(status==STATUS.AliPay) {
                        httpHandler.getPayment(JsonUtil.getString(jsonData, "recharge_code"), "alipay");
                    }else if(status==STATUS.WeChat) {
                        httpHandler.getPayment(JsonUtil.getString(jsonData, "recharge_code"), "wechat");
                    }
                }else if(method.equals(Method.getPayment)){

                    if(status==STATUS.AliPay) {
                        EnvUtils.setEnv(EnvUtils.EnvEnum.ONLINE);
                        Runnable payRunnable = new Runnable() {

                            @Override
                            public void run() {
                                PayTask alipay = new PayTask(MyWalletCharge.this);
                                try {
                                    String str = jsonData;
                                    Map<String, String> result = alipay.payV2(str, true);

                                    Message msg = new Message();
                                    msg.what = SDK_PAY_FLAG;
                                    msg.obj = result;
                                    mHandler.sendMessage(msg);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        };
                        Thread payThread = new Thread(payRunnable);
                        payThread.start();
                    }else if(status==STATUS.WeChat) {
                        String appid = Constant.WXAppId;
                        IWXAPI wxApi = WXAPIFactory.createWXAPI(MyWalletCharge.this, appid, true);
                        wxApi.registerApp(appid);
                        if(!wxApi.isWXAppInstalled())
                        {
                            ToastUtils.displayTextShort(MyWalletCharge.this, "没有安装微信");
                            return;
                        }
                        if(!wxApi.isWXAppSupportAPI())
                        {
                            ToastUtils.displayTextShort(MyWalletCharge.this, "没有安装微信");
                            return;
                        }
                        PayReq request = new PayReq();
                        request.appId = JsonUtil.getString(jsonData, "appid");
                        request.partnerId = JsonUtil.getString(jsonData, "partnerid");
                        request.prepayId= JsonUtil.getString(jsonData, "prepayid");
                        request.packageValue = JsonUtil.getString(jsonData, "package");
                        request.nonceStr= JsonUtil.getString(jsonData, "noncestr");
                        request.timeStamp= JsonUtil.getString(jsonData, "timestamp");
                        request.sign = JsonUtil.getString(jsonData, "sign");
                        wxApi.sendReq(request);
                    }
                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wallet_charge_layout);

        _setHeaderTitle("余额充值");
        initHandler();
        initView();
    }

    private void initView() {
        checkIcon2=findViewById(R.id.checkIcon2);
        checkIcon2.setOnClickListener(this);
        checkIcon3=findViewById(R.id.checkIcon3);
        checkIcon3.setOnClickListener(this);
        checkIcon4=findViewById(R.id.checkIcon4);
        checkIcon4.setOnClickListener(this);
        checkedIcon=checkIcon3;
        rechargePrice= (EditText) findViewById(R.id.rechargePrice);
        findViewById(R.id.alipayPayLayout).setOnClickListener(this);
        findViewById(R.id.wechatPayLayout).setOnClickListener(this);
        findViewById(R.id.unionPayLayout).setOnClickListener(this);
        TextView walletAmount= (TextView) findViewById(R.id.walletAmount);
        findViewById(R.id.payBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cash=rechargePrice.getText().toString().trim();
                if(ActUtil.isCash(cash, MyWalletCharge.this)) {
                    httpHandler.rechargeWallet(cash, "1");
                }
            }
        });
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        int resultcode= SharedPreferencesUtil.getInt(this, "WXPayResult", -999);
        if(resultcode==0){
            SharedPreferencesUtil.setInt(this, "WXPayResult", -999);
            Toast.makeText(MyWalletCharge.this, "支付成功", Toast.LENGTH_SHORT).show();
            setResult(Constant.refreshData);
            finish();
        }
    }

    @Override
    public void onClick(View view) {
        if(checkedIcon!=null)
            checkedIcon.setVisibility(View.GONE);
        switch (view.getId()){
            case R.id.alipayPayLayout:
                checkedIcon=checkIcon2;
                status=STATUS.AliPay;
                break;
            case R.id.wechatPayLayout:
                checkedIcon=checkIcon3;
                status=STATUS.WeChat;
                break;
            case R.id.unionPayLayout:
                checkedIcon=checkIcon4;
                status=STATUS.UnionPay;
                break;
        }
        checkedIcon.setVisibility(View.VISIBLE);
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        Toast.makeText(MyWalletCharge.this, "支付成功", Toast.LENGTH_SHORT).show();
                        setResult(Constant.refreshData);
                        finish();
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(MyWalletCharge.this, "支付失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
            }
        }
    };
}
