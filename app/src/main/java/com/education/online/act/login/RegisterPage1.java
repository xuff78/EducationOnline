package com.education.online.act.login;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.education.online.R;
import com.education.online.act.BaseFrameAct;
import com.education.online.act.order.GetVeriCode;

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
private boolean getvericode =false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_page1);

        // _setHeaderTitle("注 册");
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
                String Num = UserMobile.getText().toString();
                String vericode = ValidVeriCode.getText().toString();
                if (TextUtils.isEmpty(vericode) && TextUtils.isEmpty(Num)) {
                    ValidVeriCode.setError(getString(R.string.enter_valid_vericode));
                    UserMobile.setError(getString(R.string.enter_valid_phone));
                    UserMobile.requestFocus();
                } else if (TextUtils.isEmpty(vericode)) {
                    ValidVeriCode.setError(getString(R.string.enter_valid_vericode));
                    ValidVeriCode.requestFocus();
                } else if(!getvericode){
                    ValidVeriCode.setError("请获取验证码");
                    ValidVeriCode.requestFocus();
                }
                else if (getvericode&& phoneNum.equals(Num)) {//此处验证验证码是否正确
                    timecounter.onFinish();
                    UserMobile.setError(null);
                    ValidVeriCode.setError(null);
                    GetVertiCode.setText("获取验证码");
                    GetVertiCode.setClickable(true);
                    intent = new Intent();
                    intent.putExtra("phone", phoneNum);
                    intent.setClass(RegisterPage1.this, RegisterPage2.class);
                    startActivity(intent);
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
                    getvericode=true;
                    phoneNum = UserMobile.getText().toString();
                    timecounter.start();
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
        }

        @Override
        public void onFinish() {
            GetVertiCode.setClickable(true);
            GetVertiCode.setText("获取验证码");
            GetVertiCode.setTextColor(getResources().getColor(R.color.white));

        }
    }
}
