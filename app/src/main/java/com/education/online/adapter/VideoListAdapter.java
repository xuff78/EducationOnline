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
import com.education.online.act.VideoMainPage;
import com.education.online.act.video.VideoMain;
import com.education.online.bean.CourseBean;
import com.education.online.bean.OnlineCourseBean;
import com.education.online.util.ImageUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/8/24.
 */
public class VideoListAdapter extends RecyclerView.Adapter <RecyclerView.ViewHolder>{
    private Activity activity;
    private LayoutInflater inflater;
    private ArrayList<CourseBean> courses=new ArrayList<>();
    private ImageLoader imageLoader;

    public VideoListAdapter(Activity activity, ArrayList<CourseBean> courses){
        this.activity = activity;
        inflater = LayoutInflater.from(activity);
        this.courses=courses;
        imageLoader=ImageLoader.getInstance();
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
        vh = new itemHolder(view, pos);
        return vh;
    }

    @Override
    //绑定数据源
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        itemHolder itemHolder= (itemHolder) holder;
        CourseBean course=courses.get(position);
        imageLoader.displayImage(ImageUtil.getImageUrl(course.getImg()),itemHolder.imageView);
        itemHolder.courseName.setText(course.getCourse_name());
        itemHolder.CourseTime.setText(course.getSubject_name());
        itemHolder.CoursePrice.setText("￥"+course.getPrice());
    }

    @Override
    public int getItemCount() {
        return courses.size();
    }

    public class itemHolder extends RecyclerView.ViewHolder
    {
        ImageView imageView;
        TextView courseName, courseNumTxt, CourseTime, CoursePrice;
        public itemHolder(View v, final int position)
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
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i=new Intent(activity, VideoMainPage.class);
                    i.putExtra("course_id", courses.get(position).getCourse_id());
                    activity.startActivity(i);
                }
            });
        }
    }
}
