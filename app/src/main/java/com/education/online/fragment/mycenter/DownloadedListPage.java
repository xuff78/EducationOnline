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
import com.education.online.download.ThreadDAOImpl;
import com.education.online.download.ThreadInfo;
import com.education.online.fragment.BaseFragment;
import com.education.online.inter.CourseUpdate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/7.
 */
public class DownloadedListPage extends BaseFragment{

    private RecyclerView teacherList;
    ArrayList<CourseBean> onlineCourseBeanArrayList = new ArrayList<>();
    VideoListAdapter adapter;
    MyVideoListAdapter myVideoListAdapteradapter;
    private ThreadDAOImpl mDao;
    private List<ThreadInfo> list=new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_page, container, false);

        mDao = new ThreadDAOImpl(getActivity());
        queryDBdata();
        initView(view);
        return view;
    }

    private void queryDBdata() {
        list = mDao.getThreads(1);
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
