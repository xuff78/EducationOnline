package com.education.online.act.teacher;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.education.online.R;
import com.education.online.act.BaseFrameAct;
import com.education.online.http.HttpHandler;
import com.education.online.inter.WhellCallback;
import com.education.online.view.WheelAddressSelectorDialog;

/**
 * Created by Administrator on 2016/9/6.
 */
public class CourseTimeSet extends BaseFrameAct implements View.OnClickListener, WhellCallback {

    HttpHandler handler;
    private TextView submitCourseBtn, uploadBtn, subjectTxt, priceTxt, joinNum;
    private EditText timesetDesc;
    private LinearLayout courseLayout;
    private WheelAddressSelectorDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_timeset);

        _setHeaderTitle("教学计划");
        _setRightHomeGone();
        initView();
    }

    private void initView() {
        timesetDesc= (EditText) findViewById(R.id.timesetDesc);
        courseLayout= (LinearLayout) findViewById(R.id.courseLayout);
        findViewById(R.id.addOneTimeLayout).setOnClickListener(this);
        findViewById(R.id.addMutiTimeLayout).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.addOneTimeLayout:
                dialog=new WheelAddressSelectorDialog(CourseTimeSet.this, CourseTimeSet.this);
                dialog.show();
                break;
            case R.id.addMutiTimeLayout:
                startActivityForResult(new Intent(CourseTimeSet.this, CourseMemberEdit.class), 0x10);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==0x11){

        }
    }

    @Override
    public void onFinish() {

    }
}
