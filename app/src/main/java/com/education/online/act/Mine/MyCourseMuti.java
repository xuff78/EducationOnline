package com.education.online.act.Mine;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.education.online.R;
import com.education.online.act.BaseFrameAct;
import com.education.online.bean.FilterAll;
import com.education.online.bean.FilterInfo;
import com.education.online.fragment.CourseVideoList;
import com.education.online.fragment.OnlineCoursePage;
import com.education.online.view.MenuPopup;

import java.util.ArrayList;

/**
 * Created by 可爱的蘑菇 on 2016/9/17.
 */
public class MyCourseMuti extends BaseFrameAct {

    private TextView typeTxt, selectTypeView;
    private FrameLayout filterDetailLayout;
    private MenuPopup popup;
    private int type=0; //0:课程  1:收藏  2:下载
    private OnlineCoursePage onlinecoursePage = new OnlineCoursePage();
    private CourseVideoList courseVideoList = new CourseVideoList();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_colletion);

        type=getIntent().getIntExtra("Type", 0);
        switch (type) {
            case 0:
                _setHeaderTitle("我的课程");
                break;
            case 1:
                _setHeaderTitle("我的收藏");
                break;
            case 2:
                _setHeaderTitle("我的下载");
                break;
        }
        initView();
        initFrgment();
        addListFragment(onlinecoursePage);
    }

    private void initFrgment() {

        FilterAll filter=new FilterAll();
        ArrayList<FilterInfo> list=new ArrayList<>();
        filter.setList(list);
        Bundle b=new Bundle();
        b.putSerializable(FilterAll.Name, filter);
    }

    private void addListFragment(Fragment page) {
        FragmentManager fm=getSupportFragmentManager();
        FragmentTransaction ft=fm.beginTransaction();
        ft.replace(R.id.fragment_list, page);
        ft.commit();
    }

    private void initView() {
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
                        addListFragment(onlinecoursePage);
                        break;
                    case R.id.courseTypeTxt2:
                        addListFragment(courseVideoList);
                        break;
                    case R.id.courseTypeTxt3:
                        addListFragment(courseVideoList);
                        break;
                }
            }
        }
    };
}
