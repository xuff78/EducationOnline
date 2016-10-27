package com.education.online.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.education.online.R;
import com.education.online.adapter.MainAdapter;
import com.education.online.adapter.OnlineCourseAdapter;
import com.education.online.bean.CourseBean;
import com.education.online.bean.OnlineCourseBean;
import com.education.online.inter.CourseUpdate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/20.
 */

public class OnlineCoursePage extends CourseUpdate{

    private RecyclerView OnlineCoursePageRecycleList;
    private OnlineCourseAdapter adapter;
    ArrayList<CourseBean> onlineCourseBeanArrayList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.online_course_layout, container, false);

        initView(view);
        return view;
    }

    public void addCourses(List<CourseBean> courses, boolean isNew){
        if(isNew)
            onlineCourseBeanArrayList.clear();
        onlineCourseBeanArrayList.addAll(courses);
        adapter.notifyDataSetChanged();
    }

    private void initView(View v) {

        OnlineCoursePageRecycleList = (RecyclerView) v.findViewById(R.id.OnlineCoursePageRecycleList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        OnlineCoursePageRecycleList.setLayoutManager(layoutManager);
        adapter=new OnlineCourseAdapter(getActivity(), onlineCourseBeanArrayList);
        OnlineCoursePageRecycleList.setAdapter(adapter);

        OnlineCoursePageRecycleList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }
}
