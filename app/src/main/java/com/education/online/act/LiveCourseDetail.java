package com.education.online.act;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.MediaController;
import android.widget.RelativeLayout;

import com.avoscloud.leanchatlib.activity.ChatActivity;
import com.avoscloud.leanchatlib.utils.NotificationUtils;
import com.education.online.R;
import com.education.online.bean.CourseDetailBean;
import com.education.online.util.ScreenUtil;
import com.upyun.upplayer.widget.UpVideoView;

/**
 * Created by Administrator on 2016/12/2.
 */
public class LiveCourseDetail extends ChatActivity {

    UpVideoView videoView;
    String LiveCourseUrl="rtmp://pull.live.iqkui.com/live/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initTitle();
    }

    private void initTitle() {
        CourseDetailBean bean= (CourseDetailBean) getIntent().getSerializableExtra(CourseDetailBean.Name);
        findViewById(R.id.back_home_imagebtn).setVisibility(View.GONE);
        videoView=new UpVideoView(this);
        int width=ScreenUtil.getWidth(this);
        RelativeLayout.LayoutParams rlp=new RelativeLayout.LayoutParams(-1, width*9/16);
        videoLayout.addView(videoView, rlp);


        videoView.setVideoPath(LiveCourseUrl+bean.getCourse_id());
        MediaController controller=new MediaController(this);
        videoView.setMediaController(controller);

        videoView.start();
        controller.show();
    }

    @Override
    protected void onResume() {
        NotificationUtils.cancelNotification(this);
        super.onResume();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
        }
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onBackPressed() {

        if (videoView.isFullState()) {
            //退出全屏
            videoView.exitFullScreen(this);
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onStop() {
        super.onStop();
        videoView.release(true);
    }
}
