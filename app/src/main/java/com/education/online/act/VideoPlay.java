package com.education.online.act;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.education.online.R;
import com.upyun.upplayer.widget.MediaController;
import com.upyun.upplayer.widget.UpVideoView;

public class VideoPlay extends Activity {
    ////////////I am a marker

    String path = "";
//    String path = "rtmp://rtmptest.b0.upaiyun.com/live/default4demo33596ad21e01c659489973d38c4d2c56d9mic";
//    String path = "http://rtmptest.b0.upaiyun.com/live/default4demo33596ad21e01c659489973d38c4d2c56d9mic.m3u8";
    //String path = "rtmp://live.hkstv.hk.lxdns.com/live/hks";
//    String path = "rtmp://testlivesdk.b0.upaiyun.com/live/myapp11";
//    String path = "rtmp://testlivesdk.b0.upaiyun.com/live/upyunab";
//    String path = "rtmp://testlivesdk.b0.upaiyun.com/live/myapp11";
//    String path = "rtmp://www.zhibo.58youxian.cn/uplive/test111";
//    String path = "rtmp://115.231.100.126/live/upyunab";
//    String path = "/mnt/sdcard/test.mp3";
//    String path = "rtmp://testlivesdk.b0.upaiyun.com/live/upyunaa";
//    String path = "rtmp://testlivesdk.b0.upaiyun.com/live/test131";

    private static final String TAG = VideoPlay.class.getSimpleName();
    RelativeLayout.LayoutParams mVideoParams;

    UpVideoView upVideoView;
    private EditText mPathEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        mPathEt = (EditText) findViewById(R.id.editText);
        Intent intent = getIntent();
        if(null!=(intent.getStringExtra("Url")))
        path = intent.getStringExtra("Url");

        if(null!=(intent.getStringExtra("Uri"))) {
            path = intent.getStringExtra("Uri");
        }

        upVideoView = (UpVideoView) findViewById(R.id.uvv_vido);

        upVideoView.setBufferSize(10 * 1024 * 1024);
        //设置背景图片
//        upVideoView.setImage(R.drawable.dog);

        //设置播放地址
        upVideoView.setVideoPath(path);
        MediaController controller=new MediaController(this);
        upVideoView.setMediaController(controller);

        //开始播放
        upVideoView.start();
        controller.show();
    }

    public void toggle(View view) {

        if (upVideoView.isPlaying()) {

            //暂停播放
            upVideoView.pause();

        } else {

            //开始播放
            upVideoView.start();
        }
    }

    public void refresh(View view) {
        path = mPathEt.getText().toString();
        upVideoView.setVideoPath(path);

        // 重新开始播放器
        upVideoView.resume();
        upVideoView.start();
    }

    //全屏播放
    public void fullScreen(View view) {
        upVideoView.fullScreen(this);
    }

    private void fullScreen() {
        if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(metrics.widthPixels, metrics.heightPixels);
        mVideoParams = (RelativeLayout.LayoutParams) upVideoView.getLayoutParams();
        upVideoView.setLayoutParams(params);
        upVideoView.getTrackInfo();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // do something
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            // do something
        }
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onBackPressed() {

        if (upVideoView.isFullState()) {
            //退出全屏
            upVideoView.exitFullScreen(this);
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onStop() {
        super.onStop();
        upVideoView.stopPlayback();
    }
}
