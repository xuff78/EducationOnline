package com.education.online.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.education.online.R;
import com.education.online.bean.VideoImgItem;
import com.education.online.inter.AdapterCallback;
import com.education.online.util.ImageUtil;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/8/30.
 */
public class TeacherImgAdapter extends RecyclerView.Adapter <RecyclerView.ViewHolder>{

    private Activity activity;
    private LayoutInflater inflater;
    private boolean isVideo=false;
    private ArrayList<VideoImgItem> items=null;
    private AdapterCallback cb;
    private boolean editable=false;

    public TeacherImgAdapter(Activity activity, ArrayList<VideoImgItem> items, boolean isVideo, AdapterCallback cb){
        this.activity = activity;
        inflater = LayoutInflater.from(activity);
        this.items=items;
        this.isVideo=isVideo;
        this.cb=cb;
    }

    @Override
    public int getItemViewType(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public void setEdit(boolean edit){
        editable=edit;
        notifyDataSetChanged();
    }

    @Override
    //创建一个视图
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int pos) {
        RecyclerView.ViewHolder vh =null;
        if(pos==items.size()){
            View view = inflater.inflate(R.layout.add_item, null);
            vh = new AddHolder(view);
        }else {
            View view = inflater.inflate(R.layout.img_or_video_item, null);
            vh = new ImgHolder(view, pos);
        }
        return vh;
    }

    @Override
    //绑定数据源
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(position!=items.size()) {
            ImgHolder vh = (ImgHolder) holder;
            if (editable) {
                vh.delBtn.setVisibility(View.VISIBLE);
            } else
                vh.delBtn.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return items.size()+1;
    }

    public class ImgHolder extends RecyclerView.ViewHolder
    {
        ImageView teacherImg, imgMask, delBtn;
        public ImgHolder(View v, final int position)
        {
            super(v);
            teacherImg = (ImageView) v.findViewById(R.id.teacherImg);
            imgMask = (ImageView) v.findViewById(R.id.imgMask);
            delBtn = (ImageView) v.findViewById(R.id.delBtn);
            if(isVideo)
                imgMask.setVisibility(View.VISIBLE);
            delBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    cb.onClick(view, position);
                }
            });
        }
    }

    public class AddHolder extends RecyclerView.ViewHolder
    {
        public AddHolder(View v)
        {
            super(v);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }
    }
}
