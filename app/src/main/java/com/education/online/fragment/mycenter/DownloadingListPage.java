package com.education.online.fragment.mycenter;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.education.online.R;
import com.education.online.adapter.MyVideoListAdapter;
import com.education.online.adapter.VideoListAdapter;
import com.education.online.bean.CourseBean;
import com.education.online.fragment.BaseFragment;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/12/7.
 */
public class DownloadingListPage extends BaseFragment {

    private RecyclerView teacherList;
    ArrayList<CourseBean> onlineCourseBeanArrayList = new ArrayList<>();
    VideoListAdapter adapter;
    MyVideoListAdapter myVideoListAdapteradapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_page, container, false);

        queryDBdata();
        initView(view);
        return view;
    }

    private void queryDBdata() {

    }


    private void initView(View v) {
        teacherList = (RecyclerView) v.findViewById(R.id.recyclerList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        teacherList.setLayoutManager(layoutManager);

        adapter=new VideoListAdapter(getActivity(), onlineCourseBeanArrayList);
        teacherList.setAdapter(adapter);
    }
}
