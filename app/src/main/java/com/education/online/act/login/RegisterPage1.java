package com.education.online.act.login;

import android.os.Bundle;

import com.education.online.R;
import com.education.online.act.BaseFrameAct;

/**
 * Created by 可爱的蘑菇 on 2016/8/11.
 */
public class RegisterPage1 extends BaseFrameAct {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_page1);

        _setHeaderTitle("注 册");
        initView();
    }

    private void initView() {

    }
}
