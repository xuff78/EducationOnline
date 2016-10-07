package com.education.online.act.discovery;

import android.os.Bundle;

import com.education.online.R;
import com.education.online.act.BaseFrameAct;

/**
 * Created by Administrator on 2016/9/29.
 */
public class SignEveryday extends BaseFrameAct {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_everyday);

        _setHeaderTitle("每日签到");

        initView();
    }

    private void initView() {
        //https://github.com/kyleduo/SwitchButton
    }
}
