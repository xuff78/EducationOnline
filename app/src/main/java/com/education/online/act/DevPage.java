package com.education.online.act;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.education.online.R;

/**
 * Created by Administrator on 2017/3/2.
 */

public class DevPage extends BaseFrameAct{


    private RecyclerView recyclerList;
    private View loginBtn, registerBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dev_layout);

        _setHeaderTitle("功能开发中");
    }
}
