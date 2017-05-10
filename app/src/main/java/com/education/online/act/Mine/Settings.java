package com.education.online.act.Mine;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.education.online.R;
import com.education.online.act.BaseFrameAct;
import com.education.online.act.FirstPage;
import com.education.online.act.MainPage;
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
        servicenum.setOnClickListener(this);
        contactus  = (RelativeLayout) findViewById(R.id.contactus);
        servicephone= (TextView) findViewById(R.id.servicephone);
        exitlogin= (TextView) findViewById(R.id.exitlogin);
        exitlogin.setOnClickListener(this);
        downloadsetting.setOnClickListener(this);
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
                startActivity(new Intent(this, SettingDownload.class));
            break;
            case R.id.servicenum:
                Intent intent2 = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+servicephone.getText().toString().replace("-", "")));
                intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent2);
                break;
            case R.id.contactus:
            break;
            case R.id.exitlogin:
                Intent intent = new Intent(Settings.this, MainPage.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("Login", true);
                startActivity(intent);
            break;
            case R.id.aboutLayout:
                startActivity(new Intent(this, About.class));
                break;
        }
    }
}
