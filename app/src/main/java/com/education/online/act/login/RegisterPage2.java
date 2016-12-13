package com.education.online.act.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.education.online.R;
import com.education.online.act.BaseFrameAct;
import com.education.online.act.FirstPage;
import com.education.online.http.CallBack;
import com.education.online.http.HttpHandler;
import com.education.online.util.SHA;
import com.education.online.util.ToastUtils;

import org.json.JSONException;

public class RegisterPage2 extends BaseFrameAct {
    private Button Confirmbtn;
    private Intent intent;
    private EditText InitPwd, ConfirmPwd;
    private String type;
    private String verify_code;
    private String phone;
    private HttpHandler httpHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_page2);

        Confirmbtn = (Button) findViewById(R.id.Confirmbtn);
        InitPwd = (EditText) findViewById(R.id.InitPwd);
        ConfirmPwd = (EditText) findViewById(R.id.ConfirmPwd);
        intent = getIntent();
        type = intent.getStringExtra("type");
        if (type.equals("regist")) {
            _setHeaderTitle("注册");
        } else if (type.equals("retrievepassword")) {
            _setHeaderTitle("找回密码");
        }
        verify_code = intent.getStringExtra("verify_code");
        phone = intent.getStringExtra("phone");
        initHandler();
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
                } else if (!confirmpassword.equals(password)) {
                    ConfirmPwd.setError(getString(R.string.error_password_unequal));
                    ConfirmPwd.requestFocus();

                } else {
                    if (type.equals("regist")) {
                        intent.putExtra("password", password);
                        intent.setClass(RegisterPage2.this, RegisterPage3.class);
                        startActivity(intent);
                    } else if (type.equals("retrievepassword")) {
                        String shapassword = SHA.getSHA(password);
                        httpHandler.RetrievePassword(phone, shapassword, verify_code);
                    }
//            RetrofitHandler.login(this, name, password, new RCallBack(this));
                }

            }
        });

    }

    private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }

    private void initHandler() {
        httpHandler = new HttpHandler(RegisterPage2.this, new CallBack(RegisterPage2.this) {
            @Override
            public void doSuccess(String method, String jsonData) throws JSONException {
                super.doSuccess(method, jsonData);
                ToastUtils.displayTextShort(RegisterPage2.this, "重置密码成功！");
                intent.setClass(RegisterPage2.this, FirstPage.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }
}
