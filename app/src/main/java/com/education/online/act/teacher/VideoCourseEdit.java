package com.education.online.act.teacher;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.education.online.R;
import com.education.online.act.BaseFrameAct;
import com.education.online.fragment.OnlineCoursePage;
import com.education.online.http.HttpHandler;
import com.education.online.view.SelectCourseTypeDialog;

/**
 * Created by Administrator on 2016/9/5.
 */
public class VideoCourseEdit  extends BaseFrameAct implements View.OnClickListener{

    HttpHandler handler;
    private TextView submitCourseBtn;
    private OnlineCoursePage onlinecoursePage = new OnlineCoursePage();
    private SelectCourseTypeDialog dialog;
    private int type=0; // 0课件， 1视频

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_course_edit);

        _setRightHomeGone();
        initView();
    }

    private void initView() {

        submitCourseBtn= (TextView) findViewById(R.id.submitCourseBtn);
        submitCourseBtn.setOnClickListener(this);

        if(type==0){
            _setHeaderTitle("课件");
        }else if(type==1){
            _setHeaderTitle("视频课");
        }
    }

    @Override
    public void onClick(View view) {

    }
}
