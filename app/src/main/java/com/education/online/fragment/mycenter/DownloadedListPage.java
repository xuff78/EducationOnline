package com.education.online.fragment.mycenter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.education.online.R;
import com.education.online.act.VideoMainPage;
import com.education.online.adapter.DownloadedAdapter;
import com.education.online.adapter.MyVideoListAdapter;
import com.education.online.adapter.VideoListAdapter;
import com.education.online.bean.CourseBean;
import com.education.online.download.DownloadCourseInfo;
import com.education.online.download.DownloadService;
import com.education.online.download.ThreadDAOImpl;
import com.education.online.download.ThreadInfo;
import com.education.online.fragment.BaseFragment;
import com.education.online.inter.CourseUpdate;
import com.education.online.util.FileUtil;
import com.education.online.util.ToastUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/7.
 */
public class DownloadedListPage extends BaseFragment implements View.OnClickListener{

    private RecyclerView teacherList;
    DownloadedAdapter adapter;
    MyVideoListAdapter myVideoListAdapteradapter;
    private ThreadDAOImpl mDao;
    private List<DownloadCourseInfo> list=new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_page, container, false);

        list.clear();
        queryDBdata();
        initView(view);
        return view;
    }

    private void queryDBdata() {
        mDao = new ThreadDAOImpl(getActivity());
        List<ThreadInfo> infolist = mDao.getThreads(1);

        for (int i=0;i<infolist.size();i++){
            ThreadInfo info=infolist.get(i);
            if(i==0||!infolist.get(i-1).getCourseid().equals(info.getCourseid())){
                DownloadCourseInfo downloadCourseInfo=new DownloadCourseInfo(info.getCourseid(), info.getCoursename(), info.getCourseimg(), info.getTotalfilecount());
                downloadCourseInfo.addCourse(info);
                list.add(downloadCourseInfo);
            }else{
                list.get(list.size()-1).addCourse(info);
            }
        }
    }


    private void initView(View v) {
        teacherList = (RecyclerView) v.findViewById(R.id.recyclerList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        teacherList.setLayoutManager(layoutManager);

        adapter=new DownloadedAdapter(getActivity(), list, this);
        teacherList.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        int pos= (int) view.getTag();
        DownloadCourseInfo courseInfo=list.get(pos);
        switch (view.getId()){
            case R.id.itemLayout:
                Intent intent=new Intent(getActivity(), VideoMainPage.class);
                intent.putExtra("course_id",courseInfo.getCourseId());
                startActivity(intent);
                break;
            case R.id.delBtn:
                for (ThreadInfo info:courseInfo.getCourses()) {
                    File file=new File(DownloadService.DOWNLOAD_PATH, info.getFileName());
                    if(file.exists()){
                        file.delete();
                    }
                    mDao.deleteThread(info.getUrl());
                }
                list.remove(pos);
                adapter.notifyDataSetChanged();
                ToastUtils.displayTextShort(getActivity(), "文件已删除");
                break;
        }
    }
}
