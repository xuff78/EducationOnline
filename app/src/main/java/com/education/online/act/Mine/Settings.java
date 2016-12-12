package com.education.online.act.Mine;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.education.online.R;
import com.education.online.act.BaseFrameAct;
import com.education.online.act.login.ModifyPassword;

/**
 * Created by Administrator on 2016/9/13.
 */

public class Settings extends BaseFrameAct implements View.OnClickListener{


    private RelativeLayout modifypwd,privacy, downloadsetting, servicenum,contactus;
    private TextView servicephone,exitlogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editsettings);
        _setHeaderTitle("设置");
        init();
    }
    public void init(){
        modifypwd = (RelativeLayout) findViewById(R.id.modifypwd);
        modifypwd.setOnClickListener(this);
        privacy= (RelativeLayout) findViewById(R.id.privacy);
        downloadsetting = (RelativeLayout) findViewById(R.id.downloadsetting);
        servicenum = (RelativeLayout) findViewById(R.id.servicenum);
        contactus  = (RelativeLayout) findViewById(R.id.contactus);
        servicephone= (TextView) findViewById(R.id.servicephone);
        exitlogin= (TextView) findViewById(R.id.exitlogin);
        findViewById(R.id.aboutLayout).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.modifypwd:
                startActivity(new Intent(this, ModifyPassword.class));
            break;
            case R.id.privacy:
            break;
            case R.id.downloadsetting:
            break;
            case R.id.servicenum:
            break;
            case R.id.contactus:
            break;
            case R.id.exitlogin:
            break;
            case R.id.aboutLayout:
                startActivity(new Intent(this, About.class));
                break;
        }
    }
}
