package com.education.online.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.TextView;

import com.education.online.R;
import com.education.online.adapter.MainAdapter;
import com.education.online.adapter.MyOnlineCourseAdapter;
import com.education.online.adapter.OnlineCourseAdapter;
import com.education.online.bean.CourseBean;
import com.education.online.bean.OnlineCourseBean;
import com.education.online.inter.CourseUpdate;
import com.education.online.inter.ListCallback;
import com.education.online.inter.LoadMoreScrollerListener;
import com.education.online.view.recylistanima.animators.LandingAnimator;
import com.education.online.view.recylistanima.animators.SlideInUpAnimator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/20.
 */

public class OnlineCoursePage extends CourseUpdate{

    private RecyclerView OnlineCoursePageRecycleList;
    private RecyclerView.Adapter adapter;
    ListCallback callback;
    public void setType(int type) {
        this.type = type;
    }

    private int type =1;

    ArrayList<CourseBean> onlineCourseBeanArrayList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.online_course_layout, container, false);

        initView(view);
        return view;
    }

    public void setListCallback(ListCallback callback){
        this.callback=callback;
    }

    public void addCourses(List<CourseBean> courses, boolean isNew){
        if(isNew) {
            onlineCourseBeanArrayList.clear();
        }
        int startPos=onlineCourseBeanArrayList.size();
        onlineCourseBeanArrayList.addAll(courses);
        if(!isNew)
            adapter.notifyItemRangeChanged(startPos, courses.size());
        else
            adapter.notifyDataSetChanged();
    }

    private void initView(View v) {

        OnlineCoursePageRecycleList = (RecyclerView) v.findViewById(R.id.OnlineCoursePageRecycleList);
        OnlineCoursePageRecycleList.setItemAnimator(new SlideInUpAnimator(new DecelerateInterpolator(1f)));
        OnlineCoursePageRecycleList.getItemAnimator().setAddDuration(500);
        OnlineCoursePageRecycleList.getItemAnimator().setRemoveDuration(500);
        OnlineCoursePageRecycleList.getItemAnimator().setMoveDuration(500);
        OnlineCoursePageRecycleList.getItemAnimator().setChangeDuration(500);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        OnlineCoursePageRecycleList.setLayoutManager(layoutManager);
        if(type==0){
            adapter = new MyOnlineCourseAdapter(getActivity(),onlineCourseBeanArrayList);
        }
        else if(type==1) {
            adapter = new OnlineCourseAdapter(getActivity(), onlineCourseBeanArrayList);
        }
        OnlineCoursePageRecycleList.setAdapter(adapter);


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
        OnlineCoursePageRecycleList.addOnScrollListener(recyclerListener);
    }
}
