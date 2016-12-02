package com.education.online.act;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.avoscloud.leanchatlib.activity.ChatActivity;
import com.avoscloud.leanchatlib.utils.NotificationUtils;
import com.education.online.R;
import com.education.online.act.discovery.ChatSetting;

/**
 * Created by Administrator on 2016/12/2.
 */
public class LiveCourseDetail extends ChatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initTitle();
    }

    private void initTitle() {
        findViewById(R.id.back_home_imagebtn).setVisibility(View.GONE);
    }

    @Override
    protected void onResume() {
        NotificationUtils.cancelNotification(this);
        super.onResume();
    }
}
