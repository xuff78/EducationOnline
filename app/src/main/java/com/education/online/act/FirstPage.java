package com.education.online.act;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import com.education.online.R;
import com.education.online.util.StatusBarCompat;

/**
 * Created by Administrator on 2016/8/11.
 */
public class FirstPage extends BaseFrameAct {

    private EditText userName;
    private EditText userPsd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StatusBarCompat.fitPage(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        _setHeaderGone();
        initView();
    }

    private void initView() {

    }
}
