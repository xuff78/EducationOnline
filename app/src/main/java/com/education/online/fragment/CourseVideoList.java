package com.education.online.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.education.online.R;
import com.education.online.adapter.TeacherAdapter;
import com.education.online.adapter.VideoListAdapter;
import com.education.online.bean.CourseBean;
import com.education.online.bean.OnlineCourseBean;
import com.education.online.inter.CourseUpdate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/24.
 */
public class CourseVideoList extends CourseUpdate {

    private RecyclerView teacherList;
    ArrayList<CourseBean> onlineCourseBeanArrayList = new ArrayList<>();
    VideoListAdapter adapter;

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
        teacherList = (RecyclerView) v.findViewById(R.id.OnlineCoursePageRecycleList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        teacherList.setLayoutManager(layoutManager);
        adapter=new VideoListAdapter(getActivity(), onlineCourseBeanArrayList);
        teacherList.setAdapter(adapter);
    }
}
