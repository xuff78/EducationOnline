package com.education.online.fragment.mycenter;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.education.online.R;
import com.education.online.adapter.DownloadingAdapter;
import com.education.online.download.DownloadService;
import com.education.online.download.ThreadDAOImpl;
import com.education.online.download.ThreadInfo;
import com.education.online.fragment.BaseFragment;
import com.education.online.util.ToastUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/7.
 */
public class DownloadingListPage extends BaseFragment implements View.OnClickListener{

    private RecyclerView teacherList;
    private DownloadingAdapter adapter;
    private ThreadDAOImpl mDao;
    private List<ThreadInfo> list=new ArrayList<>();
    private DownloadService.DownloadBinder binder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_page, container, false);

        list.clear();
        if(mDao==null)
            mDao = new ThreadDAOImpl(getActivity());
        queryDBdata();
        initView(view);
        return view;
    }

    private void queryDBdata() {
        list = mDao.getThreads(0);
    }


    private void initView(View v) {
        teacherList = (RecyclerView) v.findViewById(R.id.recyclerList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        teacherList.setLayoutManager(layoutManager);

        adapter=new DownloadingAdapter(getActivity(), list, this, binder);
        teacherList.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        int pos= (int) view.getTag();
        ThreadInfo courseInfo=list.get(pos);
        switch (view.getId()){
            case R.id.itemLayout: //只是借了id做完结删除事件的判断
                list.remove(pos);
                adapter.notifyDataSetChanged();
                break;
            case R.id.delBtn:
                File file=new File(DownloadService.DOWNLOAD_PATH, courseInfo.getFileName());
                if(file.exists()){
                    file.delete();
                }
                binder.removeDownload(courseInfo);
                mDao.deleteThread(courseInfo.getUrl());
                list.remove(pos);
                adapter.notifyDataSetChanged();
                ToastUtils.displayTextShort(getActivity(), "文件已删除");
                break;
        }
    }

    public void setBinder(DownloadService.DownloadBinder binder) {
        this.binder = binder;
    }
}
