package com.education.online.act.teacher;

import android.os.Bundle;

import com.education.online.R;
import com.education.online.act.BaseFrameAct;

/**
 * Created by 可爱的蘑菇 on 2016/8/26.
 */
public class TeacherInfoEdit extends BaseFrameAct{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_base_info_edit);

        _setHeaderTitle("个人资料");

        initView();
    }

    private void initView() {

    }
}
