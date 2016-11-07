package com.education.online.act.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.education.online.R;
import com.education.online.act.BaseFrameAct;

public class RegisterPage2 extends BaseFrameAct {
    private Button Confirmbtn;
    private Intent intent;
    private EditText InitPwd, ConfirmPwd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_page2);
        Confirmbtn = (Button) findViewById(R.id. Confirmbtn);
        InitPwd = (EditText) findViewById(R.id.InitPwd);
        ConfirmPwd = (EditText) findViewById(R.id.ConfirmPwd);
        intent = getIntent();
        Confirmbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InitPwd.setError(null);
                ConfirmPwd.setError(null);

                String password = InitPwd.getText().toString();
                String confirmpassword = ConfirmPwd.getText().toString();

                if (TextUtils.isEmpty(password) || !isPasswordValid(password)) {
                    InitPwd.setError(getString(R.string.error_invalid_password));
                    InitPwd.requestFocus();
                }

               else if ( !confirmpassword.equals(password)) {
                    ConfirmPwd.setError(getString(R.string.error_password_unequal));
                    ConfirmPwd.requestFocus();

                } else {
                    intent.putExtra("password",password);
                    intent.setClass(RegisterPage2.this, RegisterPage3.class);
                    startActivity(intent);
//            RetrofitHandler.login(this, name, password, new RCallBack(this));
                }

            }
        });

    }
    private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }
}
