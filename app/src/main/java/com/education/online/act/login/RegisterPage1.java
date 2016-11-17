package com.education.online.act.login;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.education.online.R;
import com.education.online.act.BaseFrameAct;
import com.education.online.act.order.GetVeriCode;

import org.json.JSONException;
import org.json.JSONObject;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.utils.SMSLog;

import static com.mob.tools.utils.R.getStringRes;

/**
 * Created by 可爱的蘑菇 on 2016/8/11.
 */
public class RegisterPage1 extends BaseFrameAct {

    private Button NextStep;
    private TextView GetVertiCode;
    private EditText UserMobile;
    private EditText ValidVertiCode;
    private Timecounter timecounter;
    private Intent intent;
    private String phoneNum;
    private String veriCode;
    private boolean getvericode = false;

    private String country = "";
    private boolean ready;
    private static String APPKEY = "15f145e9e78f6";
    // 填写从短信SDK应用后台注册得到的APPSECRET
    private static String APPSECRET = "0c1852e998a892b469a9d61d8835bb66";

    private Handler handler;
    private EventHandler eventHandler;
    private final String DEFAULT_COUNTRY_ID = "42";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_page1);

         _setHeaderTitle("注 册");
        initView();
    }

    private void initView() {

        GetVertiCode = (TextView) findViewById(R.id.GetVertiCode);
        //GetVertiCode.setBackgroundColor(Color.parseColor("#FF6600"));
        NextStep = (Button) findViewById(R.id.NextStep);
        final EditText UserMobile = (EditText) findViewById(R.id.UserMobile);
        final EditText ValidVeriCode = (EditText) findViewById(R.id.ValidVertiCode);
        timecounter = new Timecounter(60000, 1000);
        NextStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserMobile.setError(null);
                ValidVeriCode.setError(null);
                String Num = UserMobile.getText().toString().trim().replaceAll("\\s*", "");
                String vericode = ValidVeriCode.getText().toString().trim();
                if (TextUtils.isEmpty(vericode) && TextUtils.isEmpty(Num)) {
                    ValidVeriCode.setError(getString(R.string.enter_valid_vericode));
                    UserMobile.setError(getString(R.string.enter_valid_phone));
                    UserMobile.requestFocus();
                } else if (TextUtils.isEmpty(vericode)) {
                    ValidVeriCode.setError(getString(R.string.enter_valid_vericode));
                    ValidVeriCode.requestFocus();
                } else if (!getvericode) {
                    ValidVeriCode.setError("请获取验证码");
                    ValidVeriCode.requestFocus();
                } else if (getvericode && phoneNum.equals(Num)) {//此处验证验证码是否正确
                    //  timecounter.onFinish();
                    UserMobile.setError(null);
                    ValidVeriCode.setError(null);
                    SMSSDK.submitVerificationCode("86", Num, vericode);
                    // }
                } else {
                    Toast.makeText(RegisterPage1.this, R.string.faliure_wrong_vericode, Toast.LENGTH_SHORT).show();
                }
                //startActivity(new Intent(RegisterPage1.this, TeacherPage.class));

            }
        });
        GetVertiCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserMobile.setError(null);
                ValidVeriCode.setError(null);
                String Num = UserMobile.getText().toString();
                if (TextUtils.isEmpty(Num) || !isPhoneNumValid(Num)) {
                    UserMobile.setError(getString(R.string.enter_valid_phone));
                    UserMobile.requestFocus();
                } else {
                    getvericode = true;
                    phoneNum = UserMobile.getText().toString();
                    SMSSDK.getVerificationCode("+86", phoneNum, null);
                    timecounter.start();

                }
            }
        });
        initSDK();
        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                Bundle b=msg.getData();
                int event=b.getInt("event");
                final int result=b.getInt("result");
                final String data=b.getString("data");
                if (result == SMSSDK.RESULT_COMPLETE) {
                    //回调完成
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        //提交验证码成功
                        Intent intent = new Intent(RegisterPage1.this,RegisterPage2.class);
                        intent.putExtra("phone",phoneNum);
                        startActivity(intent);
                    } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        Toast.makeText(RegisterPage1.this, "获取验证码成功", Toast.LENGTH_SHORT).show();
                        //获取验证码成功
                    } else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {
                        //返回支持发送验证码的国家列表
                    }
                } else {
                    int status=-1;
                    try {
                        JSONObject object = new JSONObject(data);
                        String des = object.optString("detail");
                        status = object.optInt("status");
                        if (!TextUtils.isEmpty(des)) {
                            Toast.makeText(RegisterPage1.this, des, Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        SMSLog.getInstance().w(e);
                    }
                    // / 如果木有找到资源，默认提示
                    int resId = 0;
                    if(status >= 400) {
                        resId = getStringRes(RegisterPage1.this,
                                "smssdk_error_desc_"+status);
                    } else {
                        resId = getStringRes(RegisterPage1.this,
                                "smssdk_network_error");
                    }
                    if (resId > 0) {
                        Toast.makeText(RegisterPage1.this, resId, Toast.LENGTH_SHORT).show();
                    }
                }
                return false;
            }
        });
        eventHandler = new EventHandler() {
            public void afterEvent(final int event, final int result, final Object data) {
                //回调完成
                Message msg=new Message();
                Bundle b=new Bundle();
                b.putInt("event", event);
                b.putInt("result", result);
                if(result!=SMSSDK.RESULT_COMPLETE){
                    ((Throwable) data).printStackTrace();
                    b.putString("data", ((Throwable) data).getMessage());
                }
                msg.setData(b);
                handler.sendMessage(msg);
            }
        };

    }

    ;

    private void initSDK() {
        // 初始化短信SDK
        SMSSDK.initSDK(RegisterPage1.this, APPKEY, APPSECRET, true);
        SMSSDK.registerEventHandler(eventHandler);
        ready = true;
    }

    private boolean isPhoneNumValid(String num) {
        return (num.length() == 11);
    }

    private class Timecounter extends CountDownTimer {

        public Timecounter(long totalTime, long interval) {
            super(totalTime, interval);
        }

        @Override
        public void onTick(long totaltime) {

            GetVertiCode.setClickable(false);
            GetVertiCode.setText("重新获取验证码（" + totaltime / 1000 + ")s");
            GetVertiCode.setTextColor(getResources().getColor(R.color.white));
            GetVertiCode.setBackgroundColor(getResources().getColor(R.color.light_gray));

        }

        @Override
        public void onFinish() {
            GetVertiCode.setClickable(true);
            GetVertiCode.setText("获取验证码");
            GetVertiCode.setTextColor(getResources().getColor(R.color.white));
            GetVertiCode.setBackgroundColor(getResources().getColor(R.color.normal_orange));

        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        SMSSDK.registerEventHandler(eventHandler);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
        SMSSDK.unregisterEventHandler(eventHandler);
    }


    protected void onDestroy() {
        if (ready) {
            // 销毁回调监听接口
            SMSSDK.unregisterAllEventHandler();
        }
        super.onDestroy();
    }
}
