package com.education.online.act.discover;

import android.os.Bundle;

import com.education.online.R;
import com.education.online.act.BaseFrameAct;

/**
 * Created by 可爱的蘑菇 on 2016/9/27.
 */
public class StudentIntroduction extends BaseFrameAct {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_introduction);

        _setHeaderGone();

        initView();
    }

    private void initView() {

    }
}
