package com.education.online.act;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.education.online.R;
import com.education.online.act.login.LoginActivity;
import com.education.online.act.login.RegisterPage1;
import com.education.online.http.CallBack;
import com.education.online.http.HttpHandler;
import com.education.online.util.ActUtil;
import com.education.online.util.ScreenUtil;
import com.education.online.util.StatusBarCompat;

/**
 * Created by Administrator on 2016/8/11.
 */
public class FirstPage extends BaseFrameAct implements View.OnClickListener{

    HttpHandler handler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StatusBarCompat.fitPage(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_page);

        _setHeaderGone();
        initHandler();
        initView();

        ActUtil.initData(this);
        ScreenUtil.logScreenSize(this);
        handler.init(ScreenUtil.getWidth(this)+"x"+ScreenUtil.getHeight(this));
    }

    private void initHandler() {
        handler=new HttpHandler(this, new CallBack(this){
            @Override
            public void doSuccess(String method, String jsonData) {
                super.doSuccess(method, jsonData);
            }
        });
    }

    private void initView() {
        findViewById(R.id.loginBtn).setOnClickListener(this);
        findViewById(R.id.registerBtn).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.loginBtn:
                startActivity(new Intent(FirstPage.this, LoginActivity.class));
                break;
            case R.id.registerBtn:
                startActivity(new Intent(FirstPage.this, RegisterPage1.class));
                break;
        }
    }
}
