package com.education.online.act;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.education.online.R;
import com.education.online.act.login.LoginActivity;
import com.education.online.act.login.RegisterPage1;
import com.education.online.http.CallBack;
import com.education.online.http.HttpHandler;
import com.education.online.retrofit.IHandler;
import com.education.online.retrofit.RCallBack;
import com.education.online.retrofit.RetrofitAPIManager;
import com.education.online.retrofit.RetrofitHandler;
import com.education.online.util.ActUtil;
import com.education.online.util.LogUtil;
import com.education.online.util.ScreenUtil;
import com.education.online.util.SharedPreferencesUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Administrator on 2016/8/11.
 */
public class FirstPage extends BaseFrameAct implements View.OnClickListener {


    private RecyclerView recyclerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_page);

        _setHeaderGone();
        initView();

        ActUtil.initData(this);
        ScreenUtil.logScreenSize(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!SharedPreferencesUtil.getSessionid(this).equals(SharedPreferencesUtil.FAILURE_STRING)) {
            startActivity(new Intent(this, MainPage.class));
            finish();
        }
    }

    private void initView() {
        findViewById(R.id.loginBtn).setOnClickListener(this);
        findViewById(R.id.registerBtn).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.loginBtn:

//                String url="mqqwpa://im/chat?chat_type=wpa&uin=215337591";
//                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                startActivity(new Intent(FirstPage.this, LoginActivity.class));
                break;
            case R.id.registerBtn:
                startActivity(new Intent(FirstPage.this, RegisterPage1.class));
                break;
        }
    }
}
