package com.education.online.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.education.online.R;
import com.education.online.bean.OnlineCourseBean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/8/24.
 */
public class VideoListAdapter extends RecyclerView.Adapter <RecyclerView.ViewHolder>{
    private Activity activity;
    private LayoutInflater inflater;


    public VideoListAdapter(Activity activity){
        this.activity = activity;
        inflater = LayoutInflater.from(activity);


    }

    @Override
    public int getItemViewType(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    //创建一个视图
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int pos) {
        RecyclerView.ViewHolder vh =null;
        View view=inflater.inflate(R.layout.course_online_items, null);
        vh = new CourseItemHolder(view, pos);
        return vh;
    }

    @Override
    //绑定数据源
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 6;
    }

    public class CourseItemHolder extends RecyclerView.ViewHolder
    {
        ImageView imageView;
        TextView courseName, courseNumTxt, CourseTime, CoursePrice;
        public CourseItemHolder(View v, int position)
        {
            super(v);
            imageView = (ImageView) v.findViewById(R.id.CourseImage);
            courseName = (TextView) v.findViewById(R.id.CourseName);
            courseNumTxt = (TextView) v.findViewById(R.id.courseNumTxt);
            courseNumTxt.setVisibility(View.VISIBLE);
            CourseTime = (TextView) v.findViewById(R.id.CourseTime);
            CoursePrice = (TextView) v.findViewById(R.id.CoursePrice);

            v.findViewById(R.id.NumApplicant).setVisibility(View.GONE);
            v.findViewById(R.id.ApplicantCourse).setVisibility(View.GONE);

        }


    }
}
