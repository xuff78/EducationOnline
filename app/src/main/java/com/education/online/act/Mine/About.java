package com.education.online.act.Mine;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.education.online.R;
import com.education.online.act.BaseFrameAct;

/**
 * Created by Administrator on 2016/12/9.
 */
public class About extends BaseFrameAct{


    private RelativeLayout modifypwd, privacy, downloadsetting, servicenum, contactus;
    private TextView servicephone, exitlogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_layout);
        _setHeaderTitle("关于我们  向学");
        init();
    }

    public void init() {
        modifypwd = (RelativeLayout) findViewById(R.id.modifypwd);
        privacy = (RelativeLayout) findViewById(R.id.privacy);
        downloadsetting = (RelativeLayout) findViewById(R.id.downloadsetting);
        servicenum = (RelativeLayout) findViewById(R.id.servicenum);
        contactus = (RelativeLayout) findViewById(R.id.contactus);
        servicephone = (TextView) findViewById(R.id.servicephone);
        exitlogin = (TextView) findViewById(R.id.exitlogin);
    }
}
