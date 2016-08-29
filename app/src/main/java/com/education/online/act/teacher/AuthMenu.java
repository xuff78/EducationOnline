package com.education.online.act.teacher;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.education.online.R;
import com.education.online.act.BaseFrameAct;

/**
 * Created by Administrator on 2016/8/29.
 */
public class AuthMenu extends BaseFrameAct {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_auth_menu);

        _setHeaderTitle("生效成为老师");

        initView();
    }

    private void initView() {
        findViewById(R.id.baseInfoLayout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AuthMenu.this, TeacherInfoEdit.class));
            }
        });
        findViewById(R.id.degreeLayout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(AuthMenu.this, TeacherInfoEdit));
            }
        });
    }
}
