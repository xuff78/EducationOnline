package com.education.online.act.Mine;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.education.online.R;
import com.education.online.act.BaseFrameAct;
import com.education.online.bean.CourseBean;
import com.education.online.bean.FilterAll;
import com.education.online.bean.FilterInfo;
import com.education.online.bean.TeacherWithCourse;
import com.education.online.fragment.CourseVideoList;
import com.education.online.fragment.OnlineCoursePage;
import com.education.online.http.CallBack;
import com.education.online.http.HttpHandler;
import com.education.online.http.Method;
import com.education.online.inter.CourseUpdate;
import com.education.online.util.JsonUtil;
import com.education.online.view.MenuPopup;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 可爱的蘑菇 on 2016/9/17.
 */
public class MyCourseMuti extends BaseFrameAct {

    private TextView hintTxtComment, selectTypeView;
    private View hintLayout;
    private FrameLayout filterDetailLayout;
    private MenuPopup popup;
    private int type=0; //0:课程  1:收藏  2:下载
    private OnlineCoursePage onlinecoursePage = new OnlineCoursePage();
    private CourseVideoList courseVideoList = new CourseVideoList();
    private CourseVideoList coursewareList = new CourseVideoList();
    private int page=1;
    private String page_size="10";
    private CourseUpdate currentCourseFrg;
    private HttpHandler handler;
    private List<CourseBean> items=new ArrayList<>();
    private String courseType="live";

    private void initHandler() {
        handler = new HttpHandler(this, new CallBack(this) {
            @Override
            public void doSuccess(String method, String jsonData) throws JSONException {
                super.doSuccess(method, jsonData);
                String courseInfo = "";
                if(method.equals(Method.getCourseCollections)){
                    courseInfo= JsonUtil.getString(jsonData, "collection_info");}
                else if (method.equals(Method.getMyCourse)){
                     courseInfo= JsonUtil.getString(jsonData, "course_info");}
                String count=JsonUtil.getString(jsonData, "no_evaluate_count");
                if(!count.equals("0")){
                    hintTxtComment.setText("您有"+count+"个待评价课程，快去给老师评价吧");
                    findViewById(R.id.hintLayout).setVisibility(View.VISIBLE);
                }
                items = JSON.parseObject(courseInfo, new TypeReference<List<CourseBean>>() {});
                currentCourseFrg.addCourses(items, page==1?true:false);
                page++;
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_colletion);

        type=getIntent().getIntExtra("Type", 0);
        switch (type) {
            case 0:
                _setHeaderTitle("我的课程");
                onlinecoursePage.setType(0);
                courseVideoList.setType(0);
                coursewareList.setType(0);
                break;
            case 1:
                _setHeaderTitle("我的收藏");
                onlinecoursePage.setType(1);
                courseVideoList.setType(1);
                coursewareList.setType(1);
                break;
            case 2:
                _setHeaderTitle("我的下载");
                onlinecoursePage.setType(2);
                courseVideoList.setType(2);
                coursewareList.setType(2);
                break;
        }
        initHandler();
        initView();
        initFrgment();
        addListFragment(onlinecoursePage);
        if(type ==0)
        {
            handler.getMyCourse(courseType,page_size,String.valueOf(page));
        }else if (type ==1) {
            handler.getCourseCollections(courseType, page);
        }
        else if (type ==2){
          //  handler.
        }
    }

    private void initFrgment() {

        FilterAll filter=new FilterAll();
        ArrayList<FilterInfo> list=new ArrayList<>();
        filter.setList(list);
        Bundle b=new Bundle();
        b.putSerializable(FilterAll.Name, filter);
    }

    private void addListFragment(CourseUpdate page) {
        currentCourseFrg=page;
        FragmentManager fm=getSupportFragmentManager();
        FragmentTransaction ft=fm.beginTransaction();
        ft.replace(R.id.fragment_list, page);
        ft.commit();
    }

    private void initView() {
        hintLayout=findViewById(R.id.hintLayout);
        hintLayout.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                startActivity(new Intent(MyCourseMuti.this, MyOrderUser.class));
            }
        });
        hintTxtComment= (TextView) findViewById(R.id.hintTxtComment);
        filterDetailLayout= (FrameLayout) findViewById(R.id.fragment_frame);
        TextView courseTypeTxt1= (TextView) findViewById(R.id.courseTypeTxt1);
        courseTypeTxt1.setOnClickListener(typeListener);
        selectTypeView=courseTypeTxt1;
        findViewById(R.id.courseTypeTxt2).setOnClickListener(typeListener);
        findViewById(R.id.courseTypeTxt3).setOnClickListener(typeListener);
    }

    View.OnClickListener typeListener=new View.OnClickListener(){

        @Override
        public void onClick(View view) {
            TextView txt= (TextView) view;
            if(txt!=selectTypeView) {
                selectTypeView.setTextColor(getResources().getColor(R.color.hard_gray));
                txt.setTextColor(getResources().getColor(R.color.dark_orange));
                selectTypeView = txt;
                switch (view.getId()) {
                    case R.id.courseTypeTxt1:
                        courseType="live";
                        addListFragment(onlinecoursePage);
                        break;
                    case R.id.courseTypeTxt2:
                        courseType="video";
                        addListFragment(courseVideoList);
                        break;
                    case R.id.courseTypeTxt3:
                        courseType="courseware";
                        addListFragment(coursewareList);
                        break;
                }
                page=1;
                if(type ==0)
                {
                    handler.getMyCourse(courseType,page_size,String.valueOf(page));
                }else if (type ==1) {
                    handler.getCourseCollections(courseType, page);
                }
                else if (type ==2){
                    //  handler.
                }
            }
        }
    };
}
