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
import com.education.online.act.MainPage;
import com.education.online.util.StatusBarCompat;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends BaseFrameAct {

    private EditText userName;
    private EditText userPsd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //What is StatusBarCompat?
        StatusBarCompat.fitPage(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        _setHeaderGone();
        initView();
    }

    private void initView() {
        userName = (EditText) findViewById(R.id.userName);
        userPsd = (EditText) findViewById(R.id.userPsd);
        userPsd.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });
        findViewById(R.id.registerBtn).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterPage1.class));
            }
        });
    }


    private void attemptLogin() {


        startActivity(new Intent(LoginActivity.this, MainPage.class));
        // Reset errors.
        /*userName.setError(null);
        userPsd.setError(null);

        String email = userName.getText().toString();
        String password = userPsd.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(password) || !isPasswordValid(password)) {
            userPsd.setError(getString(R.string.error_invalid_password));
            focusView = userPsd;
            cancel = true;
        }

        if (TextUtils.isEmpty(email)) {
            userName.setError(getString(R.string.error_field_required));
            focusView = userName;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
//            do sth
        }*/
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }

}

