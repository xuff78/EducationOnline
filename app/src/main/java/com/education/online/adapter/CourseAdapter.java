package com.education.online.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.education.online.R;
import com.education.online.util.ScreenUtil;
import com.education.online.view.RatingBar;

/**
 * Created by Administrator on 2016/8/25.
 */

public class CourseAdapter extends RecyclerView.Adapter <RecyclerView.ViewHolder>{

    private Activity act;
    private LayoutInflater listInflater;


    public CourseAdapter(Activity act, String jason) {
        this.act=act;
        listInflater= LayoutInflater.from(act);
    }

    @Override
    public int getItemViewType(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup arg0, int pos) {
        RecyclerView.ViewHolder vh=null;
        if(pos==0) {
            View view=listInflater.inflate(R.layout.course_information, null);
            vh = new CourseAdapter.CourseHolder(view, pos);
        }else if(pos==1) {
            View view=listInflater.inflate(R.layout.teacher_introduction, null);
            view.setLayoutParams(new RecyclerView.LayoutParams(-1, -2));
            vh = new CourseAdapter.TeacherInfoHolder(view,pos);
        }else if(pos==2) {
            View view=listInflater.inflate(R.layout.course_detail, null);
            view.setLayoutParams(new RecyclerView.LayoutParams(-1, -2));
            vh = new CourseDetailsHolder(view, pos);
        }else if(pos>2) {
            View view=listInflater.inflate(R.layout.comments_fragment, null);
            view.setLayoutParams(new RecyclerView.LayoutParams(-1, -2));
            vh = new CourseDetailsHolder(view, pos);
        }
        return vh;
    }

    @Override
    //视图与数据的绑定，留待以后实现
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int pos) {

        if (pos==0){
            CourseHolder vh = (CourseHolder) holder;
        }else if(pos==1) {
           TeacherInfoHolder vh =(TeacherInfoHolder) holder;
        }else if(pos==2) {
            CourseDetailsHolder vh = (CourseDetailsHolder) holder;
    }

    }

    @Override
    public int getItemCount() {
        return 5;
    }



    public class CourseHolder extends RecyclerView.ViewHolder {
        ImageView courseImg ;
        TextView coursePrice, courseTime, commentsNum, totalSerial, praisePercent, studentNum;
        public CourseHolder(View v,int pos) {
            super(v);
            courseImg = (ImageView) v.findViewById(R.id.courseImg);
            coursePrice = (TextView) v.findViewById(R.id.coursePrice);
            courseTime = (TextView) v.findViewById(R.id.courseTime);
            commentsNum = (TextView) v.findViewById(R.id.commentsNum);
            totalSerial = (TextView) v.findViewById(R.id.totalSerial);
            praisePercent = (TextView) v.findViewById(R.id.praisePercent);
            studentNum = (TextView) v.findViewById(R.id.studentNum);

        }
    }

    public class TeacherInfoHolder extends RecyclerView.ViewHolder{
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
        }
    }

    public class CourseDetailsHolder extends RecyclerView.ViewHolder{
        TextView courseDetail, coursedate, coursetime,courseArrangement;

        public CourseDetailsHolder(View v, int pos) {
            super(v);
            courseDetail= (TextView) v.findViewById(R.id.courseDetail);
            coursedate = (TextView) v.findViewById(R.id.coursedate);
            coursetime = (TextView) v.findViewById(R.id.coursetime);
            courseArrangement= (TextView) v.findViewById(R.id.courseArrangement);
        }
    }

    public class CommentsHolder extends RecyclerView.ViewHolder{
        ImageView potrait;
        TextView userName, userComments,commentDate,commentTime;
        RatingBar ratingBar;


        public CommentsHolder(View v, int pos) {
            super(v);
            potrait = (ImageView) v. findViewById(R.id.potrait);
            ratingBar= (RatingBar) v.findViewById(R.id.ratingbar);
            userComments= (TextView) v.findViewById(R.id.userComments);
            commentDate= (TextView) v.findViewById(R.id.commentDate);
            commentTime= (TextView) v.findViewById(R.id.commentTime);
        }
    }
}



