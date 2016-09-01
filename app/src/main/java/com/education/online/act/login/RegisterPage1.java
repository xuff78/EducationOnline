package com.education.online.act.login;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.education.online.R;
import com.education.online.act.BaseFrameAct;

/**
 * Created by 可爱的蘑菇 on 2016/8/11.
 */
public class RegisterPage1 extends BaseFrameAct {

    private Button NextStep;
    private TextView GetVertiCode;
    private EditText UserMobile;
    private EditText ValidVertiCode;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_page1);

       // _setHeaderTitle("注 册");
        initView();
    }

    private void initView() {

        GetVertiCode = (TextView) findViewById(R.id.GetVertiCode);
        GetVertiCode.setBackgroundColor(Color.parseColor("#FF6600"));
        NextStep = (Button) findViewById(R.id.NextStep);
        EditText UserMobile = (EditText) findViewById(R.id.UserMobile);
        EditText ValidVeriCode = (EditText) findViewById(R.id.ValidVertiCode);
        NextStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               startActivity(new Intent(RegisterPage1.this,RegisterPage2.class));
                //startActivity(new Intent(RegisterPage1.this, TeacherPage.class));

            }
        });
        GetVertiCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GetVertiCode.setBackgroundColor(Color.GRAY);
            }
        });

    }
}
