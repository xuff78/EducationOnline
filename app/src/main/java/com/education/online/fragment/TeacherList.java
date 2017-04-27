package com.education.online.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.education.online.R;
import com.education.online.adapter.OnlineCourseAdapter;
import com.education.online.adapter.TeacherAdapter;
import com.education.online.bean.CourseBean;
import com.education.online.bean.OnlineCourseBean;
import com.education.online.bean.TeacherWithCourse;
import com.education.online.inter.CourseUpdate;
import com.education.online.inter.ListCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/22.
 */
public class TeacherList extends CourseUpdate {

    private RecyclerView teacherList;
    private TeacherAdapter adapter;
    ListCallback callback;
    private List<TeacherWithCourse> onlineCourseBeanArrayList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_page, container, false);

        initView(view);
        return view;
    }

    public void setListCallback(ListCallback callback){
        this.callback=callback;
    }

    public void addTeacherCourses(List<TeacherWithCourse> courses, boolean isNew){
        if(isNew)
            onlineCourseBeanArrayList.clear();
        onlineCourseBeanArrayList.addAll(courses);
        if(isNew) {
            adapter=new TeacherAdapter(getActivity(), onlineCourseBeanArrayList);
            teacherList.setAdapter(adapter);
        }else
            adapter.notifyDataSetChanged();
    }

    private void initView(View v) {
        teacherList = (RecyclerView) v.findViewById(R.id.recyclerList);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        teacherList.setLayoutManager(layoutManager);
        adapter=new TeacherAdapter(getActivity(), onlineCourseBeanArrayList);
        teacherList.setAdapter(adapter);



        RecyclerView.OnScrollListener recyclerListener=new RecyclerView.OnScrollListener() {

            int lastVisibleItem=0;

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = layoutManager.findLastVisibleItemPosition();
//            LogUtil.i("test", "listScrollY: "+listScrollY);
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(adapter!=null)
                    if (newState == RecyclerView.SCROLL_STATE_IDLE
                            && lastVisibleItem + 1 == adapter.getItemCount()) {
                        if(callback!=null)
                            callback.requestNextpage();
                    }
            }
        };
        teacherList.addOnScrollListener(recyclerListener);
    }
}
