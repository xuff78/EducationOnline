package com.education.online.act.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.education.online.R;
import com.education.online.act.BaseFrameAct;
import com.education.online.act.FirstPage;
import com.education.online.http.CallBack;
import com.education.online.http.HttpHandler;
import com.education.online.http.Method;
import com.education.online.util.Constant;
import com.education.online.util.SharedPreferencesUtil;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterPage3 extends BaseFrameAct {

    private Button Confirmiden;
    private ImageView RoleTeacher;
    private ImageView RoleStudent;
    private LinearLayout LayoutTeacher;
    private LinearLayout LayoutStudent;
    private Intent intent;
    private String identity;
    //private HttpHandler handler;
    private String phone;
    private String password;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_page3);
       init();

    }
    private void init(){
        Confirmiden= (Button) findViewById(R.id.ConfirmIden);
        RoleTeacher= (ImageView) findViewById(R.id.RoleTeacher);
        RoleStudent= (ImageView) findViewById(R.id.RoleStudent);
        RoleStudent.setImageResource(R.mipmap.icon_round_right);
        RoleTeacher.setImageResource(R.mipmap.icon_round);
        LayoutTeacher= (LinearLayout) findViewById(R.id.LayoutTeacher);
        LayoutStudent = (LinearLayout) findViewById(R.id.LayoutStudent);
        MyRadioBtnClickListener radioclicklistener = new MyRadioBtnClickListener();
        LayoutTeacher.setOnClickListener(radioclicklistener);
        LayoutStudent.setOnClickListener(radioclicklistener);
        intent = getIntent();
        identity = "student";
       // phone = intent.getStringExtra("phone");
       // password = intent.getStringExtra("password");
       // initHandler();

        Confirmiden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               // handler.regist(phone,password,identity);
                intent.putExtra("identity",identity);
                intent.setClass(RegisterPage3.this,CompleteDataPage.class);
                startActivity(intent);

            }
        });
    }
    /*
    private void initHandler() {
        handler = new HttpHandler(this, new CallBack(this) {
            @Override
            public void doSuccess(String method, String jsonData) throws JSONException {
                super.doSuccess(method, jsonData);

                Toast.makeText(RegisterPage3.this,"注册成功！",Toast.LENGTH_SHORT);
                //解析jason
                if(method.equals(Method.Regist)) {
                    JSONObject jsonObject = new JSONObject(jsonData);
                    String sessionid = jsonObject.getString("sessionid");
                    SharedPreferencesUtil.setString(RegisterPage3.this, Constant.UserInfo, jsonData);
                    SharedPreferencesUtil.setSessionid(RegisterPage3.this, sessionid);

                    intent.setClass(RegisterPage3.this,CompleteDataPage.class);
                    startActivity(intent);

                }
            }
        });
    }*/


    private class MyRadioBtnClickListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            switch (view.getId())
            {
                case R.id.LayoutStudent:
                    RoleStudent.setImageResource(R.mipmap.icon_round_right);
                    RoleTeacher.setImageResource(R.mipmap.icon_round);
                    identity ="student";
                    break;
                case R.id.LayoutTeacher:
                    RoleTeacher.setImageResource(R.mipmap.icon_round_right);
                    RoleStudent.setImageResource(R.mipmap.icon_round);
                    identity =  "teacher";
                    break;

            }
        }
    }
}


