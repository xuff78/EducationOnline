package com.education.online.act.teacher;

import android.content.Intent;
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
public class TeacherCourseStart extends BaseFrameAct implements View.OnClickListener{

    HttpHandler handler;
    private TextView currentCourse, lastCourse;
    private OnlineCoursePage onlinecoursePage = new OnlineCoursePage();
    private SelectCourseTypeDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_course_main);

        _setHeaderTitle("课程管理");
        _setRightHomeGone();
        initView();
        openFragment(R.id.fragment_list, onlinecoursePage);
    }

    private void initView() {

        currentCourse= (TextView) findViewById(R.id.currentCourse);
        currentCourse.setOnClickListener(this);
        lastCourse= (TextView) findViewById(R.id.lastCourse);
        lastCourse.setOnClickListener(this);
        findViewById(R.id.filterLayout).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.filterLayout:
                dialog=new SelectCourseTypeDialog(TeacherCourseStart.this, listener);
                dialog.show();
                break;
            case R.id.currentCourse:
                currentCourse.setTextColor(getResources().getColor(R.color.dark_orange));
                lastCourse.setTextColor(getResources().getColor(R.color.normal_gray));
                break;
            case R.id.lastCourse:
                lastCourse.setTextColor(getResources().getColor(R.color.dark_orange));
                currentCourse.setTextColor(getResources().getColor(R.color.normal_gray));
                break;
        }
    }

    View.OnClickListener listener=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent i=getIntent();
            switch (((int)view.getTag())){
                case 0:
                    i.setClass(TeacherCourseStart.this, CourseBaseInfoEdit.class);
                    i.putExtra("Type", 2);
                    startActivity(i);
                    break;
                case 1:
                    i.setClass(TeacherCourseStart.this, CourseBaseInfoEdit.class);
                    i.putExtra("Type", 1);
                    startActivity(i);
                    break;
                case 2:
                    i.setClass(TeacherCourseStart.this, CourseBaseInfoEdit.class);
                    i.putExtra("Type", 0);
                    startActivity(i);
                    break;
            }
            dialog.dismiss();
        }
    };
}
