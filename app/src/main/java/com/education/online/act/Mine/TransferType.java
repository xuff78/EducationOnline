package com.education.online.act.Mine;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.education.online.R;
import com.education.online.act.BaseFrameAct;
import com.education.online.bean.AccountInfo;

/**
 * Created by 可爱的蘑菇 on 2017/4/18.
 */

public class TransferType  extends BaseFrameAct implements View.OnClickListener{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_transfer_type);
        _setHeaderTitle("选择提现方式");
        init();
    }
    public void init(){
        findViewById(R.id.alipayPayLayout).setOnClickListener(this);
        findViewById(R.id.wechatPayLayout).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.alipayPayLayout:
                break;
            case R.id.wechatPayLayout:
                break;
        }
    }
}
