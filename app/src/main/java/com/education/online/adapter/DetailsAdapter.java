package com.education.online.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.education.online.R;

/**
 * Created by Administrator on 2016/8/25.
 */

public class DetailsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Activity act;
    private LayoutInflater listInflater;


    public DetailsAdapter(Activity act, String jason) {
        this.act = act;
        listInflater = LayoutInflater.from(act);
    }

    @Override
    public int getItemViewType(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup arg0, int pos) {
        RecyclerView.ViewHolder vh = null;
        if (pos == 0) {
            View view=listInflater.inflate(R.layout.course_detail_tap, null);
            vh = new DetailsAdapter.CourseInfoHolder(view, pos);
        }else if(pos==1){
            View view=listInflater.inflate(R.layout.teacher_introduction, null);
            vh = new DetailsAdapter.TeacherInfoHolder(view, pos);
        }else{

        }
        return vh;

    }

    @Override
    //视图与数据的绑定，留待以后实现
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int pos) {
        if (pos == 0){
            CourseInfoHolder vh = (CourseInfoHolder) holder;
        }else{
            TeacherInfoHolder vh = (TeacherInfoHolder) holder;
        }



    }

    @Override
    public int getItemCount() {
        return 2;
    }

    //教师信息项
    public class TeacherInfoHolder extends RecyclerView.ViewHolder {
        ImageView teacherPotrait;
        TextView teacherName, teacherTitles, teacherScore, teacherComments;
        View viewbottom;

        public TeacherInfoHolder(View v, int pos) {
            super(v);
            teacherPotrait = (ImageView) v.findViewById(R.id.teacherpotrait);
            teacherName = (TextView) v.findViewById(R.id.teacherName);
            teacherScore = (TextView) v.findViewById(R.id.teacherScore);
            teacherTitles= (TextView) v.findViewById(R.id.teacherTitles);
            teacherComments = (TextView) v. findViewById(R.id.teacherComments);
            viewbottom = v.findViewById(R.id.bottomview);

            viewbottom.setMinimumHeight(100);
        }
    }
    //课程信息项
    public class CourseInfoHolder extends RecyclerView.ViewHolder {

        public CourseInfoHolder(View v, int pos) {
            super(v);
        }
    }

}






