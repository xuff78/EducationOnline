package com.education.online.act.order;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.education.online.R;
import com.education.online.act.BaseFrameAct;

/**
 * Created by Administrator on 2016/9/19.
 */
public class GetVeriCode extends BaseFrameAct implements View.OnClickListener {

    private TextView phoneNum,getVertiCode,nextStep;
    private EditText veriCode;
    private Timecounter timecounter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.veriphonenum);
        _setHeaderTitle("设置支付密码");
        init();

    }
    public void init(){
        phoneNum = (TextView) findViewById(R.id.phoneNum);
        getVertiCode= (TextView) findViewById(R.id.getVertiCode);
        getVertiCode.setOnClickListener(this);
        nextStep= (TextView) findViewById(R.id.nextStep);
        nextStep.setOnClickListener(this);
        veriCode= (EditText) findViewById(R.id.veriCode);
        timecounter = new Timecounter(60000,1000);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.getVertiCode:
                timecounter.start();
                break;
            case R.id.nextStep:
                startActivity(new Intent(GetVeriCode.this, SetPayPwd.class));
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
}
