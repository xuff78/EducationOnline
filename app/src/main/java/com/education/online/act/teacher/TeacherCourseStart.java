package com.education.online.act.teacher;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.education.online.R;
import com.education.online.act.BaseFrameAct;
import com.education.online.adapter.AllTypeCourseAdapter;
import com.education.online.adapter.RateAdapter;
import com.education.online.bean.CourseBean;
import com.education.online.bean.CourseFilter;
import com.education.online.bean.SubjectBean;
import com.education.online.bean.TeacherBean;
import com.education.online.fragment.OnlineCoursePage;
import com.education.online.http.CallBack;
import com.education.online.http.HttpHandler;
import com.education.online.http.Method;
import com.education.online.util.DialogUtil;
import com.education.online.util.JsonUtil;
import com.education.online.util.SharedPreferencesUtil;
import com.education.online.view.SelectCourseTypeDialog;

import org.json.JSONException;

import java.util.List;

/**
 * Created by Administrator on 2016/9/5.
 */
public class TeacherCourseStart extends BaseFrameAct implements View.OnClickListener{

    private TextView currentCourse, lastCourse;
    private SelectCourseTypeDialog dialog;
    private AllTypeCourseAdapter adapter;
    private RecyclerView recyclerList;
    private HttpHandler handler;
    private CourseFilter filter;

    private void initHandler() {
        handler = new HttpHandler(this, new CallBack(this) {
            @Override
            public void doSuccess(String method, String jsonData) throws JSONException {
                super.doSuccess(method, jsonData);
                if(method.equals(Method.getCourseList)){
                    List<CourseBean> courses=JSON.parseObject(JsonUtil.getString(jsonData, "course_info"),
                            new TypeReference<List<CourseBean>>(){});
                    adapter=new AllTypeCourseAdapter(TeacherCourseStart.this, courses);
                    recyclerList.setAdapter(adapter);
                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_course_main);

        _setHeaderTitle("课程管理");
        _setRightHomeGone();
        initView();

        initHandler();

        filter=new CourseFilter();
        filter.setCourse_type(null);
        filter.setUsercode(SharedPreferencesUtil.getUsercode(this));
        filter.setSort("sort_order");
        filter.setCourse_validate_status(null);
    }

    @Override
    protected void onResume() {
        super.onResume();
        handler.getCourseList(filter);
    }

    private void initView() {

        recyclerList=(RecyclerView)findViewById(R.id.recyclerList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerList.setLayoutManager(layoutManager);

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
                filter.setStatus("underway");
                handler.getCourseList(filter);
                break;
            case R.id.lastCourse:
                filter.setStatus("over");
                lastCourse.setTextColor(getResources().getColor(R.color.dark_orange));
                currentCourse.setTextColor(getResources().getColor(R.color.normal_gray));
                handler.getCourseList(filter);
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
                    i.setClass(TeacherCourseStart.this, VideoCourseBaseInfoEdit.class);
                    i.putExtra("Type", 1);
                    startActivity(i);
                    break;
                case 2:
                    i.setClass(TeacherCourseStart.this, CourseWareBaseInfoEdit.class);
                    i.putExtra("Type", 0);
                    startActivity(i);
                    break;
            }
            dialog.dismiss();
        }
    };
}
