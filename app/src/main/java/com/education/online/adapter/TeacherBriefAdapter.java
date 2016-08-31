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

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/8/30.
 */
public class TeacherBriefAdapter extends RecyclerView.Adapter <RecyclerView.ViewHolder>{

    private Activity activity;
    private LayoutInflater inflater;
    public TeacherBriefAdapter(Activity act, String jason){
        this.activity = act;
        inflater = LayoutInflater.from(activity);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh=null;
        View view=inflater.inflate(R.layout.teacher_brief, null);
        vh = new TeacherBriefAdapter.BriefHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        BriefHolder vh = (BriefHolder) holder;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public class BriefHolder extends RecyclerView.ViewHolder{
        TextView teacherIdentify, teacherMarks, teacherSelfIntro,teacherExperience;


        public BriefHolder(View itemView) {
            super(itemView);
            teacherIdentify = (TextView) itemView.findViewById(R.id.teacherIdentify);
            teacherMarks = (TextView) itemView.findViewById(R.id.teacherMarks);
            teacherSelfIntro= (TextView) itemView.findViewById(R.id.teacherSelfIntro);
            teacherExperience = (TextView) itemView.findViewById(R.id.teacherExperience);
        }
    }
}


