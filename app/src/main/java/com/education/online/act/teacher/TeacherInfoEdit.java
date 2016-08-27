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
        setContentView(R.layout.wallet_recharge);

        _setHeaderTitle("基本信息");

        initView();
    }

    private void initView() {

    }
}
