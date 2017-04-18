package com.education.online.act.Mine;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.education.online.R;
import com.education.online.act.BaseFrameAct;
import com.education.online.bean.AccountInfo;

/**
 * Created by 可爱的蘑菇 on 2017/4/18.
 */

public class CashTransfer extends BaseFrameAct implements View.OnClickListener{

    private RelativeLayout modifypwd;
    private AccountInfo info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editsettings);
        _setHeaderTitle("提现");
        info= (AccountInfo) getIntent().getSerializableExtra("AccountInfo");
        init();
    }
    public void init(){
        modifypwd = (RelativeLayout) findViewById(R.id.modifypwd);
        findViewById(R.id.aboutLayout).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.modifypwd:
                break;
        }
    }
}
