package com.education.online.act.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.education.online.R;
import com.education.online.act.BaseFrameAct;
import com.education.online.act.FirstPage;
import com.education.online.bean.JsonMessage;
import com.education.online.http.CallBack;
import com.education.online.http.HttpHandler;
import com.education.online.http.Method;
import com.education.online.util.Constant;
import com.education.online.util.SHA;
import com.education.online.util.SharedPreferencesUtil;
import com.education.online.util.ToastUtils;

import org.json.JSONException;
import org.json.JSONObject;


import static com.mob.tools.utils.R.getStringRes;

/**
 * Created by 可爱的蘑菇 on 2016/8/11.
 */
public class ModifyPassword extends BaseFrameAct {

    private Button Complete;
    private TextView GetVertiCode, bindPhoneNum;
    private EditText Newpassword;
    private EditText ValidVertiCode;
    private Timecounter timecounter;
    private Intent intent;
    private String phoneNum;
    private String password;
    private String veriCode;
    private boolean getvericode = false;
    private HttpHandler httpHandler;


    private static String sms_type = "others";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modifypassword);

        _setHeaderTitle("修改密码");
        initView();
        initHandler();
    }

    private void initView() {
        intent = getIntent();
        GetVertiCode = (TextView) findViewById(R.id.GetVertiCode);
        //GetVertiCode.setBackgroundColor(Color.parseColor("#FF6600"));
        Complete = (Button) findViewById(R.id.Complete);
        final EditText Newpassword = (EditText) findViewById(R.id.Newpassword);
        final EditText ValidVeriCode = (EditText) findViewById(R.id.ValidVertiCode);
        timecounter = new Timecounter(60000, 1000);
        bindPhoneNum = (TextView) findViewById(R.id.bindPhoneNum);
        phoneNum = SharedPreferencesUtil.getString(this, Constant.UserName);//保存用户名在本地
        bindPhoneNum.setText(phoneNum);

        Complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Newpassword.setError(null);
                ValidVeriCode.setError(null);
                String Num = Newpassword.getText().toString().trim();
                String vericode = ValidVeriCode.getText().toString().trim();
                if (!getvericode) {
                    ValidVeriCode.setError("请获取验证码");
                    ValidVeriCode.requestFocus();
                }else {
                    if (TextUtils.isEmpty(vericode)) {
                        ValidVeriCode.setError(getString(R.string.enter_valid_vericode));
                        ValidVeriCode.requestFocus();
                    } else if (TextUtils.isEmpty(Num)) {
                        ValidVeriCode.setError("请输入有效密码");
                        ValidVeriCode.requestFocus();
                    } else {
                        Newpassword.setError(null);
                        ValidVeriCode.setError(null);
                        veriCode = ValidVeriCode.getText().toString().trim();
                        password = Newpassword.getText().toString().trim();
                        String SHApassword = SHA.getSHA(password);
                        httpHandler.ModifyPassword(SHApassword, veriCode);// }
                    }
                }
                //startActivity(new Intent(RegisterPage1.this, TeacherPage.class));

            }
        });
        GetVertiCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Newpassword.setError(null);
                ValidVeriCode.setError(null);
                String Num = Newpassword.getText().toString();
                getvericode = true;
                httpHandler.getSms(phoneNum, sms_type);
                timecounter.start();
            }
        });
    }

    ;

    private void initHandler() {
        httpHandler = new HttpHandler(ModifyPassword.this, new CallBack(ModifyPassword.this) {
            @Override
            public void doSuccess(String method, String jsonData) throws JSONException {
                super.doSuccess(method, jsonData);
                if (method.equals(Method.modifypassword)) {
                    ToastUtils.displayTextShort(ModifyPassword.this, "密码修改成功！");
                    intent.setClass(ModifyPassword.this, FirstPage.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }
                if (method.equals(Method.getSms)) {
                    ToastUtils.displayTextShort(ModifyPassword.this, "获取短信验证码成功！");
                }
            }

        });
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
}
