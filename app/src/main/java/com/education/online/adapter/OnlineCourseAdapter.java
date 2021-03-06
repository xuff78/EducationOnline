package com.education.online.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.education.online.R;
import com.education.online.act.CourseMainPage;
import com.education.online.bean.CourseBean;
import com.education.online.bean.OnlineCourseBean;
import com.education.online.util.ActUtil;
import com.education.online.util.ImageUtil;
import com.education.online.util.SharedPreferencesUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/8/21.
 */

public class OnlineCourseAdapter extends RecyclerView.Adapter <RecyclerView.ViewHolder>{
    ArrayList<CourseBean> onlineCourseBeanArrayList;
    private Activity activity;
    private LayoutInflater inflater;
    private ImageLoader imageLoader;
    private String myUsercode="";

    public OnlineCourseAdapter(Activity activity, ArrayList<CourseBean> onlineCourseArraryList ){
        this.activity = activity;
        this.onlineCourseBeanArrayList = onlineCourseArraryList;
        inflater = LayoutInflater.from(activity);
        imageLoader=ImageLoader.getInstance();
        myUsercode= SharedPreferencesUtil.getUsercode(activity);
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
        CourseBean course = onlineCourseBeanArrayList.get(position);
        //将数据源与视图布局绑定，暂时先不写
        CourseItemHolder courseItemHolder= (CourseItemHolder) holder;
        imageLoader.displayImage(ImageUtil.getImageUrl(course.getImg()),courseItemHolder.courseImage);
        courseItemHolder.courseName.setText(course.getCourse_name());
        courseItemHolder.CourseTime.setText(course.getCourseware_date()+" 开课");
        courseItemHolder.CoursePrice.setText(ActUtil.getPrice(course.getPrice()));
        courseItemHolder.NumApplicant.setText(course.getFollow()+" 人已报名");
        if(myUsercode.equals(course.getUsercode())){
            courseItemHolder.ApplicantCourse.setText("进入课程");
        }else if(course.getIs_buy().equals("1")) {
            courseItemHolder.ApplicantCourse.setBackgroundResource(R.drawable.shape_grayline_with_corner);
            courseItemHolder.ApplicantCourse.setText("已报名");
            courseItemHolder.ApplicantCourse.setTextColor(Color.GRAY);
        }

    }

    @Override
    public int getItemCount() {
        return onlineCourseBeanArrayList.size();
    }

    public class CourseItemHolder extends RecyclerView.ViewHolder
    {
        ImageView courseImage;
        TextView courseName;
        TextView CourseTime;
        TextView CoursePrice;
        TextView NumApplicant;
        TextView ApplicantCourse;
        public CourseItemHolder(View v, final int position)
        {
            super(v);
            courseImage = (ImageView) v.findViewById(R.id.CourseImage);
            courseName = (TextView) v.findViewById(R.id.CourseName);
            CourseTime = (TextView) v.findViewById(R.id.CourseTime);
            CoursePrice = (TextView) v.findViewById(R.id.CoursePrice);
            NumApplicant = (TextView) v.findViewById(R.id.NumApplicant);
            ApplicantCourse = (TextView) v.findViewById(R.id.ApplicantCourse);
//            timeBeginToEnd = v.findViewById();
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent();
                    intent.putExtra("course_id", onlineCourseBeanArrayList.get(position).getCourse_id());
                    intent.setClass(activity, CourseMainPage.class);
                    activity.startActivity(intent);
                }
            });
        }


    }
}


