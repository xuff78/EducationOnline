package com.education.online.act.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.education.online.R;
import com.education.online.act.BaseFrameAct;
import com.education.online.act.MainPage;
import com.education.online.bean.JsonMessage;
import com.education.online.bean.LoginInfo;
import com.education.online.retrofit.HttpResult;
import com.education.online.retrofit.RCallBack;
import com.education.online.retrofit.RequestStrUtil;
import com.education.online.retrofit.RetrofitAPIManager;
import com.education.online.util.ActUtil;
import com.education.online.util.Constant;
import com.education.online.util.JsonUtil;
import com.education.online.util.SHA;
import com.education.online.util.SharedPreferencesUtil;
import com.education.online.util.StatusBarCompat;
import com.education.online.view.CircularAnim;

import java.util.Observable;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends BaseFrameAct {

    @Bind(R.id.userName)EditText userName;
    @Bind(R.id.userPsd)EditText userPsd;
    @Bind(R.id.retrievepassword)TextView retrievepassword;
    @Bind(R.id.email_sign_in_button)Button loginBtn;
    @Bind(R.id.progressBar)ProgressBar progressBar;
    private long pressTime=0;
    private Subscriber callBack;
    private Subscription subscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StatusBarCompat.fitPage(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        _setHeaderGone();
        initHandler();
        initView();
    }

    @OnClick({R.id.registerBtn, R.id.email_sign_in_button, R.id.retrievepassword})
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()){
            case R.id.registerBtn:
                intent.setClass(LoginActivity.this, RegisterPage1.class);
                intent.putExtra("type","regist");
                startActivity(intent);
                break;
            case R.id.email_sign_in_button:
                attemptLogin();
                break;
            case R.id.retrievepassword:
                intent.setClass(LoginActivity.this, RegisterPage1.class);
                intent.putExtra("type","retrievepassword");
                startActivity(intent);
                break;
        }
    }


    private void initView() {
        String loginName=SharedPreferencesUtil.getString(this, Constant.UserName);//保存用户名在本地
        String psw=SharedPreferencesUtil.getString(this, Constant.UserPSW);//保存用户名在本地
        if(!loginName.equals(SharedPreferencesUtil.FAILURE_STRING)){//有数据时自动填写
            userName.setText(loginName);
        }
        if(!psw.equals(SharedPreferencesUtil.FAILURE_STRING)){//有数据时自动填写
            userPsd.setText(psw);
        }
        userPsd.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_ACTION_DONE) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });
    }


    private void attemptLogin() {
        userName.setError(null);
        userPsd.setError(null);

        final String name = userName.getText().toString();
        String uncodepassword=userPsd.getText().toString();
        final String password = SHA.getSHA(uncodepassword);

        //String password = userPsd.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(password) || !isPasswordValid(password)) {
            userPsd.setError(getString(R.string.error_invalid_password));
            focusView = userPsd;
            cancel = true;
        }

        if (TextUtils.isEmpty(name)) {
            userName.setError(getString(R.string.error_field_required));
            focusView = userName;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            ActUtil.KeyBoardCancle(this);
            progressBar.setVisibility(View.VISIBLE);
            CircularAnim.init(800, 500, R.color.dark_orange);
            CircularAnim.hide(loginBtn).endRadius(progressBar.getHeight() / 2)
                    .go(new CircularAnim.OnAnimationEndListener() {
                        @Override
                        public void onAnimationEnd() {
                            pressTime=System.currentTimeMillis();
//                            handler.login(name, password);
                            subscription=RetrofitAPIManager.getUserRetrofit(LoginActivity.this)
//                                    .login(RequestStrUtil.getLoginStr(LoginActivity.this, name, password))
                                    .login(name, password)
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(callBack);
                        }
                    }, this);
        }
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }

    @Override
    public void onBackPressed() {
        if(getIntent().hasExtra("TimeOut")){
            Intent i=new Intent(this, MainPage.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.putExtra("Exit", true);
            startActivity(i);
            finish();
        }else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        unsubscribe();
        super.onDestroy();
    }

    protected void unsubscribe() {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }

    private void initHandler() {
        callBack = new RCallBack<LoginInfo>(this){

            @Override
            public void doSuccess(HttpResult<LoginInfo> result) {
                LoginInfo loginInfo=result.getData();
                SharedPreferencesUtil.setString(LoginActivity.this, Constant.UserIdentity, loginInfo.getUser_identity());
                SharedPreferencesUtil.setUsercode(LoginActivity.this, loginInfo.getUsercode());
                SharedPreferencesUtil.setSessionid(LoginActivity.this, loginInfo.getSessionid());//保存sessionid
                SharedPreferencesUtil.setString(LoginActivity.this, Constant.isLogin, loginInfo.getSessionid());
                SharedPreferencesUtil.setString(LoginActivity.this, Constant.Avatar, loginInfo.getAvatar());
                SharedPreferencesUtil.setString(LoginActivity.this, Constant.NickName, loginInfo.getNickname());
                SharedPreferencesUtil.setString(LoginActivity.this, Constant.UserName, userName.getText().toString());
                SharedPreferencesUtil.setString(LoginActivity.this, Constant.UserPSW, userPsd.getText().toString());
                SharedPreferencesUtil.setString(LoginActivity.this, Constant.SubjectId, loginInfo.getSubject_id());
                SharedPreferencesUtil.setString(LoginActivity.this, Constant.SubjectName, loginInfo.getSubject_name());
                SharedPreferencesUtil.setString(LoginActivity.this, Constant.UserInfo, JSON.toJSONString(loginInfo));

                ActUtil.initChatUser(LoginActivity.this, loginInfo.getAvatar(), loginInfo.getNickname());
                long left=System.currentTimeMillis()-pressTime;
                if(left>=1500)
                    left=0;
                else
                    left=1500-left;
                progressBar.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                }, left);
            }

            @Override
            protected void doFailure(HttpResult<LoginInfo> result) {
                super.doFailure(result);
                progressBar.setVisibility(View.GONE);
                CircularAnim.show(loginBtn).triggerView(loginBtn).go();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                progressBar.setVisibility(View.GONE);
                CircularAnim.show(loginBtn).triggerView(loginBtn).go();
            }
        };
    }
}

