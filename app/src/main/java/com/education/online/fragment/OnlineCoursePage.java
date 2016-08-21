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
import com.education.online.bean.OnlineCourseBean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/8/20.
 */

public class OnlineCoursePage extends BaseFragment {
    private TextView Today;
    private TextView Day2, Day3, Day4, Day5, Day6, Day7;
    private RecyclerView OnlineCoursePageRecycleList;
    ArrayList<OnlineCourseBean> onlineCourseBeanArrayList = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.online_course_layout, container, false);

        initView(view);
        return view;
    }


    private void initView(View v) {
        onlineCourseBeanArrayList.clear();
        OnlineCourseBean courseBean = new OnlineCourseBean();
        onlineCourseBeanArrayList.add(courseBean);
        onlineCourseBeanArrayList.add(courseBean);
        onlineCourseBeanArrayList.add(courseBean);
        onlineCourseBeanArrayList.add(courseBean);

        OnlineCoursePageRecycleList = (RecyclerView) v.findViewById(R.id.OnlineCoursePageRecycleList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        OnlineCoursePageRecycleList.setLayoutManager(layoutManager);
        OnlineCoursePageRecycleList.setAdapter(new  OnlineCourseAdapter(getActivity(),onlineCourseBeanArrayList));
        Today = (TextView) v.findViewById(R.id.Today);
        Day2 = (TextView) v.findViewById(R.id.Day2);
        Day3 = (TextView) v.findViewById(R.id.Day3);
        Day4 = (TextView) v.findViewById(R.id.Day4);
        Day5 = (TextView) v.findViewById(R.id.Day5);
        Day6 = (TextView) v.findViewById(R.id.Day6);
        Day7 = (TextView) v.findViewById(R.id.Day7);
    }
}
