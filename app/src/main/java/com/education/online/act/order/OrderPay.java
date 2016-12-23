package com.education.online.act.order;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alipay.sdk.app.EnvUtils;
import com.alipay.sdk.app.PayTask;
import com.education.online.R;
import com.education.online.act.BaseFrameAct;
import com.education.online.bean.AuthResult;
import com.education.online.bean.OrderDetailBean;
import com.education.online.bean.PayResult;
import com.education.online.http.CallBack;
import com.education.online.http.HttpHandler;
import com.education.online.http.Method;
import com.education.online.view.PayTypeDialog;

import java.util.Map;

/**
 * Created by 可爱的蘑菇 on 2016/8/22.
 */
public class OrderPay extends BaseFrameAct implements View.OnClickListener, PayTypeDialog.PayDialogCallBack {

    private TextView walletAmount;
    private PayTypeDialog dialog;
    private OrderDetailBean orderDetailBean=new OrderDetailBean();
    private HttpHandler handler;
    private final int SDK_PAY_FLAG=1;
    private final int SDK_AUTH_FLAG=2;

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
                        Toast.makeText(OrderPay.this, "支付成功", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(OrderPay.this, PaymentCompletePage.class);
                        i.putExtra("Order", orderDetailBean);
                        startActivity(i);
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(OrderPay.this, "支付失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
                case SDK_AUTH_FLAG: {
                    @SuppressWarnings("unchecked")
                    AuthResult authResult = new AuthResult((Map<String, String>) msg.obj, true);
                    String resultStatus = authResult.getResultStatus();

                    // 判断resultStatus 为“9000”且result_code
                    // 为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
                    if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
                        // 获取alipay_open_id，调支付时作为参数extern_token 的value
                        // 传入，则支付账户为该授权账户
                        Toast.makeText(OrderPay.this,
                                "授权成功" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT)
                                .show();
                    } else {
                        // 其他状态值则为授权失败
                        Toast.makeText(OrderPay.this,
                                "授权失败" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT).show();

                    }
                    break;
                }
                default:
                    break;
            }
        }
    };
    private String APPID="2016072800112933";
    private String RSA_PRIVATE = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAN+t4ANqVWdu0JI2" +
            "053gil1MSk3Grj+r957HSepLtyK2jObpJsDm5L7XKB4I4KcVYWA+13K4fTJVi84D" +
            "v549xF8QIav1Wvh0hTxKWVdwxSqMZbTKzZP2xjxKL5SgXKroe2amaDv3wpRRppUB" +
            "1pDXSizzGRwnDXN9B3S9xitdBuQ7AgMBAAECgYEA202/p7mWmH2mkuScYFspbcYB" +
            "x/W3HAhAFHWyO7wWhztSNId9jn4S4iVTWPc9Q7QLr/CNDhZh8Xq8QrWcJsrKftCz" +
            "ulvNh9vT/eddsMR7l4jQ8wsaMQz8Fx39OcnlGhO+jkJXJ2Q5/p1/Ip3rQO9yQuzi" +
            "0qnYV+00Au70tiGUJNECQQDxG2tUFbj83PBFEH8ogmgbr+h4s2vIPZQpex10Cm08" +
            "kB6kgPBs8kwdoAJEzVHC7CenysaIpwVOmKuRO9j5c3NZAkEA7X7fIuP3ArtcsFUo" +
            "xNXB/QqEYxn8Kx5tdxVaNUMW4fomZ94ZzYrMrWxIkt5fsd71HCy+/wf0qtdgZkqo" +
            "vWCFswJACl4FdW4hsC3H3xBgh1tkIpyjwFzmq4uKTWZP0+eG3u3Lg4NP0z9v6m4w" +
            "6shxVZJV+i8L7mBQKBsEdZA/OiqusQJALYMFobsL5/MdxRDcujO4dBRi4Fbncx/m" +
            "nE50NgbASNfWKktuqGvz4zwAAF0q+3wdfqO4ikjtAxw49gFSqs/nbwJBAOpQVyNS" +
            "V3JQaecdSFX6EfScdX8zYnwt0lCjTXYToo+T+GnZD5go62yd6TBZQFIy4ZrfMCYk" +
            "6IJjFe7vH9fOqxE=";
    private String PID="";
    private Object TARGET_ID="";

    private void initHandler() {
        handler = new HttpHandler(this, new CallBack(this) {
            @Override
            public void doSuccess(String method, final String jsonData){
                if(method.equals(Method.getPayment)){
//                    final String sign_str= JsonUtil.getString(jsonData, "sign_str");
                    EnvUtils.setEnv(EnvUtils.EnvEnum.SANDBOX);
//                    Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(APPID);
//                    String orderParam = OrderInfoUtil2_0.buildOrderParam(params);
//                    String sign = OrderInfoUtil2_0.getSign(params, RSA_PRIVATE);
//                    final String orderInfo = orderParam + "&" + sign;
                    Runnable payRunnable = new Runnable() {

                        @Override
                        public void run() {
                            PayTask alipay = new PayTask(OrderPay.this);
                            try {
                                String str=jsonData; //URLEncoder.encode(sign_str, "UTF-8");
                                Map<String, String> result = alipay.payV2(str, true);
//                                Log.i("msp", "sign_str encode: "+str);
//                                Log.i("msp", result.toString());

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
                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_pay);

        _setHeaderTitle("支付订单");
        if(getIntent().hasExtra("jsonData"))
            orderDetailBean= JSON.parseObject(getIntent().getStringExtra("jsonData"), OrderDetailBean.class);
        else if(getIntent().hasExtra("Order"))
            orderDetailBean= (OrderDetailBean) getIntent().getSerializableExtra("Order");
        initHandler();
        initView();
    }

    private void initView() {
        TextView orderId= (TextView) findViewById(R.id.orderId);
        orderId.setText(orderDetailBean.getOrder_number());
        TextView courseName= (TextView) findViewById(R.id.courseName);
        courseName.setText(orderDetailBean.getCourse_name());
        TextView coursePrice= (TextView) findViewById(R.id.coursePrice);
        coursePrice.setText("￥"+orderDetailBean.getPrice());
        walletAmount= (TextView) findViewById(R.id.walletAmount);
        TextView orderPrice= (TextView) findViewById(R.id.orderPrice);
        orderPrice.setText("总价： ￥"+orderDetailBean.getOrder_price());
        findViewById(R.id.rechargeBtn).setOnClickListener(this);
        findViewById(R.id.payBtn).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.rechargeBtn:
                Intent i=new Intent(OrderPay.this, WalletRecharge.class);
                startActivity(i);
                break;
            case R.id.payBtn:
                dialog=new PayTypeDialog(OrderPay.this, true, OrderPay.this);
                dialog.show();
                break;
        }
    }

    @Override
    public void onSelected(int payType) {
        switch (payType){
            case PayTypeDialog.WalletPay:
                break;
            case PayTypeDialog.AliPay:
                handler.getPayment(orderDetailBean.getOrder_number(), "alipay");
                break;
            case PayTypeDialog.WechatPay:
                break;
            case PayTypeDialog.UnionPay:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==0x11){
            String psw=data.getStringExtra("psw");
            handler.payWallet(orderDetailBean.getOrder_number(), psw);
        }
    }
}
