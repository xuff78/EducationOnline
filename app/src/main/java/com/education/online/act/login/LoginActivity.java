package com.education.online.act.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.education.online.R;
import com.education.online.act.BaseFrameAct;
import com.education.online.act.FirstPage;
import com.education.online.act.MainPage;
import com.education.online.http.CallBack;
import com.education.online.http.HttpHandler;
import com.education.online.retrofit.RCallBack;
import com.education.online.retrofit.RetrofitHandler;
import com.education.online.util.Constant;
import com.education.online.util.JsonUtil;
import com.education.online.util.SHA;
import com.education.online.util.SharedPreferencesUtil;
import com.education.online.util.StatusBarCompat;

import org.json.JSONException;

import java.security.NoSuchAlgorithmException;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends BaseFrameAct {

    private EditText userName;
    private EditText userPsd;
    HttpHandler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //What is StatusBarCompat?
        StatusBarCompat.fitPage(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initHandler();
        _setHeaderGone();
        initView();
    }

    private void initView() {
        userName = (EditText) findViewById(R.id.userName);
        userPsd = (EditText) findViewById(R.id.userPsd);
        String loginName=SharedPreferencesUtil.getString(this, Constant.UserName);//保存用户名在本地
        if(!loginName.equals(SharedPreferencesUtil.FAILURE_STRING)){//有数据时自动填写
            userName.setText(loginName);
        }
        userPsd.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_ACTION_DONE) {
                    try {
                        attemptLogin();
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    }
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    attemptLogin();
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
            }
        });
        findViewById(R.id.registerBtn).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterPage1.class));
            }
        });
    }


    private void attemptLogin() throws NoSuchAlgorithmException {


//        startActivity(new Intent(LoginActivity.this, MainPage.class));
        // Reset errors.
        userName.setError(null);
        userPsd.setError(null);

        String name = userName.getText().toString();
        String uncodepassword=userPsd.getText().toString();
        String password = SHA.getSHA(uncodepassword);

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
            handler.login(name, password);
//            RetrofitHandler.login(this, name, password, new RCallBack(this));
        }
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }

    private void initHandler() {
        handler = new HttpHandler(this, new CallBack(this) {
            @Override
            public void doSuccess(String method, String jsonData) throws JSONException {
                super.doSuccess(method, jsonData);
                String sessionid = JsonUtil.getString(jsonData, "sessionid");
                String usercode = JsonUtil.getString(jsonData, "usercode");
                String user_identity = JsonUtil.getString(jsonData, "user_identity");
                SharedPreferencesUtil.setString(LoginActivity.this, Constant.UserIdentity, user_identity);
                SharedPreferencesUtil.setUsercode(LoginActivity.this, usercode);
                SharedPreferencesUtil.setSessionid(LoginActivity.this, sessionid);//保存sessionid
                SharedPreferencesUtil.setString(LoginActivity.this, Constant.UserInfo, jsonData);
                SharedPreferencesUtil.setString(LoginActivity.this, Constant.UserName, userName.getText().toString());

                finish();
            }
        });
    }

}

