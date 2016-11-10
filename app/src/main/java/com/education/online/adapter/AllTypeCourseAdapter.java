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
import com.education.online.act.CourseMainPage;
import com.education.online.act.VideoMainPage;
import com.education.online.bean.CourseBean;
import com.education.online.util.ActUtil;
import com.education.online.util.ImageUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/3.
 */
public class AllTypeCourseAdapter extends RecyclerView.Adapter <RecyclerView.ViewHolder>{

    private Activity act;
    private LayoutInflater listInflater;
    private List<CourseBean> courseList=new ArrayList<>();
    private ImageLoader loader=ImageLoader.getInstance();

    public AllTypeCourseAdapter(Activity act, List<CourseBean> courseList) {
        this.act=act;
        this.courseList=courseList;
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
        View view=listInflater.inflate(R.layout.course_homepage_item, null);
        vh = new AllTypeCourseHolder(view, pos);
        return vh;
    }

    @Override
    //视图与数据的绑定，留待以后实现
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int pos) {
        AllTypeCourseHolder  vh = ( AllTypeCourseHolder ) holder;
    }

    @Override
    public int getItemCount() {
        return courseList.size();
    }



    public class AllTypeCourseHolder extends RecyclerView.ViewHolder{

        public AllTypeCourseHolder(View v, final int pos) {
            super(v);
            final CourseBean course=courseList.get(pos);

            TextView courseType = (TextView) v.findViewById(R.id.courseType);
            ActUtil.getCourseTypeTxt(course.getCourse_type(), courseType);
            TextView CourseName = (TextView) v.findViewById(R.id.CourseName);
            ImageView CourseImage=(ImageView)v.findViewById(R.id.CourseImage);
            loader.displayImage(ImageUtil.getImageUrl(course.getImg()),CourseImage);
            TextView CourseTime = (TextView) v.findViewById(R.id.CourseTime);
            CourseTime.setText(course.getUser_name());
            TextView CoursePrice = (TextView) v.findViewById(R.id.CoursePrice);
            CoursePrice.setText("￥"+course.getPrice());
            TextView NumApplicant = (TextView) v.findViewById(R.id.NumApplicant);
            NumApplicant.setText(course.getFollow()+"人已报名");
            CourseName.setText(CourseName.getText().toString()+course.getSubject_name());
            TextView courseNumTxt= (TextView) v.findViewById(R.id.courseNumTxt);
            courseNumTxt.setText("共"+course.getCourse_count()+"节课");
            TextView statusTxt= (TextView) v.findViewById(R.id.statusTxt);
            statusTxt.setText(ActUtil.getCourseStatusTxt(course.getStatus()));
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent();
                    String course_id = course.getCourse_id();
                    intent.putExtra("course_id", course_id);
                    if(course_id=="3")
                    intent.setClass(act, CourseMainPage.class);
                    else
                    intent.setClass(act, VideoMainPage.class);
                    act.startActivity(intent);
                }
            });
        }
    }
}
