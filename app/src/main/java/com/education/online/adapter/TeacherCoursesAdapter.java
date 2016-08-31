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
 * Created by Administrator on 2016/8/30.
 */
public class TeacherCoursesAdapter extends RecyclerView.Adapter <RecyclerView.ViewHolder>{

    private Activity activity;
    private LayoutInflater inflater;
    public TeacherCoursesAdapter(Activity act, String jason){
        this.activity = act;
        inflater = LayoutInflater.from(activity);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh=null;
        View view=inflater.inflate(R.layout.teachter_course_item, null);
        vh = new TeacherCoursesAdapter.CourseHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        CourseHolder vh = (CourseHolder) holder;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public class CourseHolder extends RecyclerView.ViewHolder{
        ImageView teacherImage;
        TextView courseName, coursePrice, totallength;


        

        public CourseHolder(View itemView) {
            super(itemView);
            teacherImage = (ImageView) itemView.findViewById(R.id.teacherImage);
            courseName = (TextView) itemView.findViewById(R.id.courseName);
            coursePrice= (TextView) itemView.findViewById(R.id.coursePrice);
            totallength = (TextView) itemView.findViewById(R.id.totallength);

        }
    }
}


