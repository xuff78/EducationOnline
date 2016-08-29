package com.education.online.act.teacher;

import android.os.Bundle;

import com.education.online.R;
import com.education.online.act.BaseFrameAct;

/**
 * Created by Administrator on 2016/8/29.
 */
public class AuthMenu extends BaseFrameAct {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_base_info_edit);

        _setHeaderTitle("生效成为老师");

        initView();
    }

    private void initView() {

    }
}
