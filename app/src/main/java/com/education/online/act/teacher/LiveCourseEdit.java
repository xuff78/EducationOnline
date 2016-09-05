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
public class LiveCourseEdit  extends BaseFrameAct implements View.OnClickListener{

    HttpHandler handler;
    private TextView currentCourse, lastCourse;
    private OnlineCoursePage onlinecoursePage = new OnlineCoursePage();
    private SelectCourseTypeDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_result_act);

        _setHeaderTitle("开设直播课");
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

    }
}
