package com.education.online.act;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.View;

import com.education.online.R;
import com.education.online.act.login.LoginActivity;
import com.education.online.act.login.RegisterPage1;
import com.education.online.util.ActUtil;
import com.education.online.util.Constant;
import com.education.online.util.ScreenUtil;
import com.education.online.util.SharedPreferencesUtil;

import io.vov.vitamio.LibsChecker;

/**
 * Created by Administrator on 2016/8/11.
 */
public class FirstPage extends BaseFrameAct implements View.OnClickListener {


    private RecyclerView recyclerList;
    private View loginBtn, registerBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTheme(R.style.AppTheme);
        setContentView(R.layout.first_page);

        _setHeaderGone();
        initView();

//        ImageLoader.getInstance().clearDiskCache();
//        ImageLoader.getInstance().clearMemoryCache();
//        SharedPreferencesUtil.setString(this, Constant.isLogin, SharedPreferencesUtil.FAILURE_STRING);
        ActUtil.initData(this);
        ScreenUtil.logScreenSize(this);

        if (!LibsChecker.checkVitamioLibs(this))
            return;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!SharedPreferencesUtil.getSessionid(this).equals(SharedPreferencesUtil.FAILURE_STRING)) {
            Intent i=new Intent(this, MainPage.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
            finish();
        }
    }

    private void initView() {
        loginBtn= findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(this);
        registerBtn= findViewById(R.id.registerBtn);
        registerBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.loginBtn:
                Intent i=new Intent(FirstPage.this, LoginActivity.class);
                if(Build.VERSION.SDK_INT>=21) {
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(FirstPage.this,
                            /*Pair.create(loginBtn, "login_btn"),*/ Pair.create(registerBtn, "reg_btn"));
                    Bundle bundle = options.toBundle();
                    startActivity(i, bundle);
                }else{
                    startActivity(i);
                }
                break;
            case R.id.registerBtn:
                Intent intent = new Intent();
                intent.setClass(FirstPage.this, RegisterPage1.class);
                intent.putExtra("type","regist");
                startActivity(intent);
                break;
        }
    }
}
