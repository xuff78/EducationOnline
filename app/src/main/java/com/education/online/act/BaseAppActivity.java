package com.education.online.act;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.avoscloud.leanchatlib.controller.ChatManager;
import com.education.online.util.ActUtil;
import com.education.online.util.ImageUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by Administrator on 2017/2/3.
 */
public abstract class BaseAppActivity extends AppCompatActivity {

    protected ImageLoader loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ActUtil.isLogin(this)&& ChatManager.getInstance().getSelfId() == null) {
            Intent intent = new Intent(this, MainPage.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("Login", true);
            startActivity(intent);
        }else
            loader=ImageUtil.initImageLoader(this);
    }
}
