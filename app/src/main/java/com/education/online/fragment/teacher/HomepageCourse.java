package com.education.online.fragment.teacher;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.education.online.R;
import com.education.online.adapter.TeacherImgAdapter;
import com.education.online.bean.VideoImgItem;
import com.education.online.fragment.BaseFragment;
import com.education.online.inter.AdapterCallback;
import com.education.online.util.ImageUtil;
import com.education.online.view.draglist.DragSortListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/31.
 */
public class HomepageCourse extends BaseFragment {

    private DragSortListView dragList;
    private ArrayList<VideoImgItem> items=new ArrayList<>();
    private DragListAdapter adapter;
    private boolean edit=false;
    private DragSortListView.DropListener onDrop =
            new DragSortListView.DropListener() {
                @Override
                public void drop(int from, int to) {
                    if (from != to) {
                        VideoImgItem item = adapter.getItem(from);
                        adapter.remove(item);
                        adapter.insert(item, to);
                        dragList.moveCheckState(from, to);
                    }
                }
            };

    private DragSortListView.RemoveListener onRemove =
            new DragSortListView.RemoveListener() {
                @Override
                public void remove(int which) {
                    VideoImgItem item = adapter.getItem(which);
                    adapter.remove(item);
                    dragList.removeCheckState(which);
                }
            };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.draglist_layout, container, false);

        items.clear();
        VideoImgItem item=new VideoImgItem();
        items.add(item);
        items.add(item);
        items.add(item);
        items.add(item);
        items.add(item);
        initView(view);
        edit=false;
        return view;
    }

    public void setEdit(){
        edit=!edit;
        adapter.notifyDataSetChanged();
    }

    private void initView(View v) {
        dragList = (DragSortListView) v.findViewById(R.id.dragList);
        adapter=new DragListAdapter(items, getActivity());
        dragList.setAdapter(adapter);
        dragList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(!edit){
                    setEdit();
                }
                return false;
            }
        });
    }

    private class DragListAdapter extends ArrayAdapter<VideoImgItem> {

        public DragListAdapter(List<VideoImgItem> artists, Activity act) {
            super(act, R.layout.course_homepage_item, R.id.CourseName, artists);
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            View v = super.getView(position, convertView, parent);

            TextView CourseName = (TextView) v.findViewById(R.id.CourseName);
            ImageView drag_handle=(ImageView)v.findViewById(R.id.drag_handle);
            ImageView delBtn=(ImageView)v.findViewById(R.id.delBtn);
            if(edit){
                drag_handle.setVisibility(View.VISIBLE);
                delBtn.setVisibility(View.VISIBLE);
            }else{
                drag_handle.setVisibility(View.GONE);
                delBtn.setVisibility(View.GONE);
            }
            return v;
        }

    }
}
