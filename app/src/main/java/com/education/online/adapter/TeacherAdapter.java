package com.education.online.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.education.online.R;
import com.education.online.act.CourseMainPage;
import com.education.online.act.VideoMainPage;
import com.education.online.act.teacher.TeacherHomePage;
import com.education.online.act.teacher.TeacherInformationPage;
import com.education.online.bean.CourseBean;
import com.education.online.bean.OnlineCourseBean;
import com.education.online.bean.TeacherWithCourse;
import com.education.online.util.Constant;
import com.education.online.util.ImageUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/22.
 */
public class TeacherAdapter extends RecyclerView.Adapter <RecyclerView.ViewHolder>{
//    ArrayList<OnlineCourseBean> onlineCourseBeanArrayList;
    private Activity activity;
    private LayoutInflater inflater;
    private List<TeacherWithCourse> teachers=new ArrayList<>();
    private ImageLoader loader=ImageLoader.getInstance();

    public TeacherAdapter(Activity activity, List<TeacherWithCourse> teachers){
        this.activity = activity;
        inflater = LayoutInflater.from(activity);
        this.teachers=teachers;
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
        View view=inflater.inflate(R.layout.teachter_list_item, null);
        vh = new TeacherItemHolder(view, pos);
        return vh;
    }

    @Override
    //绑定数据源
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        //将数据源与视图布局绑定，暂时先不写
    }

    @Override
    public int getItemCount() {
        return teachers.size();
    }

    public class TeacherItemHolder extends RecyclerView.ViewHolder
    {
        ImageView teacherImage;
        TextView teacherName, teacherDesc, evaluation, NumApplicant;
        LinearLayout courseLayout, moreLayout;
        View arrowIcon;
        boolean isExpand=false;

        public TeacherItemHolder(View v, final int position)
        {
            super(v);
            TeacherWithCourse teacher=teachers.get(position);
            arrowIcon = v.findViewById(R.id.arrowIcon);
            teacherImage = (ImageView) v.findViewById(R.id.teacherImage);
            teacherName = (TextView) v.findViewById(R.id.teacherName);
            teacherDesc = (TextView) v.findViewById(R.id.teacherDesc);
            evaluation = (TextView) v.findViewById(R.id.evaluation);
            NumApplicant = (TextView) v.findViewById(R.id.NumApplicant);
            courseLayout = (LinearLayout) v.findViewById(R.id.courseLayout);
            moreLayout = (LinearLayout) v.findViewById(R.id.moreLayout);
            loader.displayImage(ImageUtil.getImageUrl(teacher.getAvatar()), teacherImage);
            teacherName.setText(teacher.getUser_name());
            teacherDesc.setText(teacher.getAbout_teacher());
            evaluation.setText(teacher.getAverage()+"分");
            NumApplicant.setText("评价: "+teacher.getEvaluate_count());
            LinearLayout.LayoutParams llpTeacher=new LinearLayout.LayoutParams(-1, ImageUtil.dip2px(activity, 60));
            for (int i=0;i<teacher.getCourse_detail().size();i++){
                courseLayout.addView(getCourseLayout(teacher.getCourse_detail().get(i)), llpTeacher);
            }
            if(position>2) {
                LinearLayout.LayoutParams llpCourse=new LinearLayout.LayoutParams(-1, ImageUtil.dip2px(activity, 60)*2);
                courseLayout.setLayoutParams(llpCourse);
                moreLayout.setVisibility(View.VISIBLE);
                moreLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(isExpand){
                            arrowIcon.setBackgroundResource(R.mipmap.arrow_down_gray);
                            LinearLayout.LayoutParams llp=new LinearLayout.LayoutParams(-1, ImageUtil.dip2px(activity, 60)*2);
                            courseLayout.setLayoutParams(llp);
                            isExpand=false;
                        }else{
                            arrowIcon.setBackgroundResource(R.mipmap.arrow_down_gray);
                            LinearLayout.LayoutParams llp=new LinearLayout.LayoutParams(-1, ImageUtil.dip2px(activity, 60)*position);
                            courseLayout.setLayoutParams(llp);
                            isExpand=true;
                        }
                    }
                });
            }
            View teacherLayout=v.findViewById(R.id.CourseImageLayout);
            teacherLayout.setTag(teacher);
            teacherLayout.setOnClickListener(listener);
        }

        private View getCourseLayout(CourseBean courseBean) {
            View v=inflater.inflate(R.layout.teacher_course_item, null);
            TextView courseType= (TextView) v.findViewById(R.id.courseType);
            switch (Integer.valueOf(courseBean.getCourse_type())){
                case 1:
                    courseType.setText("课件");
                    break;
                case 2:
                    courseType.setText("视频");
                    break;
                case 3:
                    courseType.setText("直播课");
                    break;
            }
            TextView courseName= (TextView) v.findViewById(R.id.courseName);
            courseName.setText(courseBean.getCourse_name());
            TextView coursePrice= (TextView) v.findViewById(R.id.coursePrice);
            coursePrice.setText(courseBean.getPrice());
            TextView buyerNum= (TextView) v.findViewById(R.id.buyerNum);
            buyerNum.setText(courseBean.getFollow()+"人在学习");
            v.setTag(courseBean);
            v.setOnClickListener(listener);
            return v;
        }

        View.OnClickListener listener=new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(view.getId()==R.id.CourseImageLayout) {
                    TeacherWithCourse teacher = (TeacherWithCourse) view.getTag();
                    Intent i=new Intent(activity, TeacherInformationPage.class);
                    i.putExtra(Constant.UserCode, teacher.getUsercode());
                    activity.startActivity(i);
                }else {
                    CourseBean course = (CourseBean) view.getTag();
                    Intent i = new Intent();
                    switch (Integer.valueOf(course.getCourse_type())) {
                        case 1:
                        case 2:
                            i.setClass(activity, VideoMainPage.class);
                            break;
                        case 3:
                            i.setClass(activity, CourseMainPage.class);
                            break;
                    }
                    i.putExtra("course_id", course.getCourse_id());
                    activity.startActivity(i);
                }
            }
        };

    }
}
