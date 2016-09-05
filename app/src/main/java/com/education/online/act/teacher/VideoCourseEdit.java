package com.education.online.act.teacher;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
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
    private TextView submitCourseBtn, uploadBtn, subjectTxt, priceTxt;
    private ImageView courseImg;
    private EditText courseName, courseDesc;
    private int type=0; // 0课件， 1视频, 2直播
    private View uploadLayout, joinNumLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_course_edit);

        type=getIntent().getIntExtra("Type", 0);
        _setRightHomeGone();
        initView();
    }

    private void initView() {

        subjectTxt= (TextView) findViewById(R.id.subjectTxt);
        priceTxt= (TextView) findViewById(R.id.priceTxt);
        courseImg= (ImageView) findViewById(R.id.courseImg);
        courseName= (EditText) findViewById(R.id.courseName);
        courseDesc= (EditText) findViewById(R.id.courseDesc);
        submitCourseBtn=(TextView) findViewById(R.id.submitCourseBtn);
        submitCourseBtn.setOnClickListener(this);
        uploadBtn= (TextView) findViewById(R.id.uploadBtn);
        uploadBtn.setOnClickListener(this);
        uploadLayout= findViewById(R.id.uploadLayout);
        joinNumLayout=findViewById(R.id.joinNumLayout);
        joinNumLayout.setOnClickListener(this);
        findViewById(R.id.subjectLayout).setOnClickListener(this);
        findViewById(R.id.priceLayout).setOnClickListener(this);
        findViewById(R.id.courseImgLayout).setOnClickListener(this);

        if(type==0){
            uploadBtn.setText("上传课件");
            _setHeaderTitle("课件");
        }else if(type==1){
            uploadBtn.setText("上传视频");
            _setHeaderTitle("视频课");
        }else if(type==2) {
            uploadLayout.setVisibility(View.GONE);
            joinNumLayout.setVisibility(View.VISIBLE);
            _setHeaderTitle("开设直播课");
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.joinNumLayout:
                break;
            case R.id.subjectLayout:
                break;
            case R.id.priceLayout:
                break;
            case R.id.courseImgLayout:
                break;
            case R.id.submitCourseBtn:
                break;
            case R.id.uploadBtn:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
