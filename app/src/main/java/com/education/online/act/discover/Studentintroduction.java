package com.education.online.act.discover;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.education.online.R;
import com.education.online.act.BaseFrameAct;

/**
 * Created by 可爱的蘑菇 on 2016/9/27.
 */
public class StudentIntroduction extends BaseFrameAct implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_introduction);

        _setHeaderGone();

        initView();
    }

    private void initView() {
        findViewById(R.id.myQrcode).setOnClickListener(this);
        findViewById(R.id.toChatBtn).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.toChatBtn:
                startActivity(new Intent(StudentIntroduction.this, ChatPage.class));
                break;
            case R.id.myQrcode:
                break;
        }
    }
}
