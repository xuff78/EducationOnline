package com.education.online.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.education.online.R;
import com.education.online.act.teacher.TeacherInformationPage;
import com.education.online.bean.CourseDetailBean;
import com.education.online.bean.CreatUserInfo;
import com.education.online.bean.EvaluateListBean;
import com.education.online.util.ActUtil;
import com.education.online.util.Constant;
import com.education.online.util.ImageUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by Administrator on 2016/8/25.
 */

public class DetailsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Activity act;
    private LayoutInflater listInflater;
    private CourseDetailBean courseDetailBean;
    private ImageLoader imageLoader;


    public DetailsAdapter(Activity act, CourseDetailBean courseDetailBean) {
        this.act = act;
        this.courseDetailBean = courseDetailBean;
        listInflater = LayoutInflater.from(act);
        imageLoader=ImageLoader.getInstance();
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
      if (pos == 0) {
            CourseInfoHolder vh = (CourseInfoHolder) holder;
            vh.courseName.setText(courseDetailBean.getCourse_name());
            vh.courseBrief.setText(courseDetailBean.getIntroduction());
            vh.coursePrice.setText(courseDetailBean.getPrice());
          vh.courseoldprice.setText(courseDetailBean.getOriginal_price());
        }   else if (pos == 1) {
          TeacherInfoHolder vh = (TeacherInfoHolder) holder;
          vh.teacherName.setText(courseDetailBean.getUser_info().getUser_name());
          vh.teacherTitles.setText(courseDetailBean.getUser_info().getIntroduction());
          if(courseDetailBean.getUser_info().getAvatar().length()!=0) {
              imageLoader.displayImage(ImageUtil.getImageUrl(courseDetailBean.getUser_info().getAvatar()), vh.teacherPotrait);
          }
          vh.teacherScore.setText(courseDetailBean.getUser_info().getAverage() + "分");
          vh.teacherComments.setText("评论" + courseDetailBean.getUser_info().getEvaluate_count());
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
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent();
                    intent.setClass(act, TeacherInformationPage.class);
                    CreatUserInfo teacher=courseDetailBean.getUser_info();
                    intent.putExtra("Avatar", teacher.getAvatar());
                    intent.putExtra("Name", teacher.getUser_name());
                    intent.putExtra(Constant.UserCode, courseDetailBean.getUsercode());
                    ActUtil.startAnimActivity(act, intent, view.findViewById(R.id.teacherpotrait), "headIcon");
                }
            });
        }
    }
    //课程信息项
    public class CourseInfoHolder extends RecyclerView.ViewHolder {

        TextView courseName,coursePrice,courseBrief,courseoldprice;

        public CourseInfoHolder(View v, int pos) {
            super(v);
            courseoldprice=(TextView) v.findViewById(R.id.courseoldprice);
            courseName= (TextView) v.findViewById(R.id.courseName);
            coursePrice= (TextView) v.findViewById(R.id.coursePrice);
            courseBrief= (TextView) v.findViewById(R.id.courseBrief);
        }
    }

}






