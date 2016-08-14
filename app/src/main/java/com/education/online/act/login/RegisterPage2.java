package com.education.online.act.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.education.online.R;
import com.education.online.act.BaseFrameAct;

public class RegisterPage2 extends BaseFrameAct {
    private Button Confirmbtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_page2);
        Confirmbtn = (Button) findViewById(R.id. Confirmbtn);
        Confirmbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent (RegisterPage2.this, RegisterPage3.class));
            }
        });

    }
}
