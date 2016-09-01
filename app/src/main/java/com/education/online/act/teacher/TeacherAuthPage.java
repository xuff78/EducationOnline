package com.education.online.act.teacher;

import android.os.Bundle;

import com.education.online.R;
import com.education.online.act.BaseFrameAct;

/**
 * Created by Administrator on 2016/9/1.
 */
public class TeacherAuthPage extends BaseFrameAct {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_auth_menu);

        _setHeaderTitle("认证设置");

        initView();
    }

    private void initView() {

    }
}
