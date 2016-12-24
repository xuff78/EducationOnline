package com.education.online.act.order;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.education.online.R;
import com.education.online.act.BaseFrameAct;
import com.education.online.http.CallBack;
import com.education.online.http.HttpHandler;
import com.education.online.http.Method;
import com.education.online.util.Constant;
import com.education.online.util.SharedPreferencesUtil;
import com.education.online.util.ToastUtils;

import org.json.JSONException;

/**
 * Created by Administrator on 2016/9/19.
 */
public class GetVeriCode extends BaseFrameAct implements View.OnClickListener {

    private TextView phoneNum,getVertiCode,nextStep;
    private EditText veriCode;
    private Timecounter timecounter;
    private String phone;
    private HttpHandler handler;
    private final int success=0x10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.veriphonenum);
        _setHeaderTitle("设置支付密码");
        init();
        initHandler();


    }
    public void init(){
        phoneNum = (TextView) findViewById(R.id.phoneNum);
        getVertiCode= (TextView) findViewById(R.id.getVertiCode);
        getVertiCode.setOnClickListener(this);
        nextStep= (TextView) findViewById(R.id.nextStep);
        nextStep.setOnClickListener(this);
        veriCode= (EditText) findViewById(R.id.veriCode);
        timecounter = new Timecounter(60000,1000);
        phone = SharedPreferencesUtil.getString(this, Constant.UserName);
        phoneNum.setText(phone);



    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.getVertiCode:
                handler.getSms(phone,"others");
                timecounter.start();
                break;
            case R.id.nextStep:
                String vericode = veriCode.getText().toString();
                if (vericode.isEmpty()){
                ToastUtils.displayTextShort(GetVeriCode.this,"请输入验证码！");
            }else{
                    handler.Verify(phone,vericode);
                }
                break;
        }

    }

    private class Timecounter extends CountDownTimer{

        public Timecounter(long totalTime, long interval){
            super(totalTime,interval);
        }
        @Override
        public void onTick(long totaltime) {

            getVertiCode.setClickable(false);
            getVertiCode.setText("重新获取验证码（"+totaltime/1000+")s");
            getVertiCode.setTextColor(getResources().getColor(R.color.what_gray));
        }

        @Override
        public void onFinish() {
            getVertiCode.setClickable(true);
            getVertiCode.setText("获取验证码");
            getVertiCode.setTextColor(getResources().getColor(R.color.hard_gray));

        }
    }

    private void initHandler(){
        handler = new HttpHandler(GetVeriCode.this,new CallBack(GetVeriCode.this){
            @Override
            public void doSuccess(String method, String jsonData) throws JSONException {
                super.doSuccess(method, jsonData);
                if(method.equals(Method.getSms)){
                    ToastUtils.displayTextShort(GetVeriCode.this, "获取验证码成功！");
                }
                if(method.equals(Method.verify)){
                    Intent intent = new Intent();
                    intent.setClass(GetVeriCode.this,SetPayPwd.class);
                    startActivityForResult(intent,0x11);

                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode==0x11){
            if (resultCode == success)
            {
                ToastUtils.displayTextShort(GetVeriCode.this,"设置支付密码成功！");
                finish();
            }
        }
    }
}
