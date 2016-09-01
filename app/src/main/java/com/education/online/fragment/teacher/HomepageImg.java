package com.education.online.fragment.teacher;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.education.online.R;
import com.education.online.adapter.TeacherImgAdapter;
import com.education.online.bean.VideoImgItem;
import com.education.online.fragment.BaseFragment;
import com.education.online.inter.AdapterCallback;
import com.education.online.util.ImageUtil;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/8/30.
 */
public class HomepageImg extends BaseFragment {

    private RecyclerView teacherList;
    private ArrayList<VideoImgItem> items=new ArrayList<>();
    private TeacherImgAdapter adapter;
    private boolean edit=false;
    private AdapterCallback callback=new AdapterCallback() {
        @Override
        public void onClick(View v, int i) {

        }

        @Override
        public void additem() {
            VideoImgItem item=new VideoImgItem();
            items.add(item);
            adapter.notifyDataSetChanged();
        }

        @Override
        public void delitem(View v, int i) {
            items.remove(i);
            adapter.notifyDataSetChanged();
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_page, container, false);

        items.clear();
        VideoImgItem item=new VideoImgItem();
        items.add(item);
        items.add(item);
        items.add(item);
        items.add(item);
        items.add(item);
        initView(view);
        int padding= ImageUtil.dip2px(getActivity(), 10);
        view.setPadding(padding*2,0,padding,0);
        edit=false;
        return view;
    }

    public void setEdit(){
        edit=!edit;
        adapter.setEdit(edit);
    }


    private void initView(View v) {
        teacherList = (RecyclerView) v.findViewById(R.id.recyclerList);
        GridLayoutManager layoutManager = new GridLayoutManager (getActivity(),4);
        teacherList.setLayoutManager(layoutManager);
        adapter=new TeacherImgAdapter(getActivity(), items, false, callback);
        teacherList.setAdapter(adapter);
    }

}
