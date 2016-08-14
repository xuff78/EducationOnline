package com.education.online.act.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.education.online.R;
import com.education.online.act.BaseFrameAct;

public class RegisterPage3 extends BaseFrameAct {
    private Button Confirmiden;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_page3);
        Confirmiden= (Button) findViewById(R.id.ConfirmIden);
        Confirmiden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               startActivity(new Intent(RegisterPage3.this, CompleteDataPage.class));
            }
        });
    }
}
