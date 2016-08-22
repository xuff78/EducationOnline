package com.education.online.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.education.online.R;
import com.education.online.bean.OnlineCourseBean;
import com.education.online.util.ImageUtil;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/8/22.
 */
public class TeacherAdapter extends RecyclerView.Adapter <RecyclerView.ViewHolder>{
//    ArrayList<OnlineCourseBean> onlineCourseBeanArrayList;
    private Activity activity;
    private LayoutInflater inflater;


    public TeacherAdapter(Activity activity){
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
        return 6;
    }

    public class TeacherItemHolder extends RecyclerView.ViewHolder
    {
        ImageView teacherImage;
        TextView teacherName, teacherDesc, evaluation;
        LinearLayout courseLayout, moreLayout;
        View arrowIcon;
        boolean isExpand=false;

        public TeacherItemHolder(View v, final int position)
        {
            super(v);
            arrowIcon = v.findViewById(R.id.arrowIcon);
            teacherImage = (ImageView) v.findViewById(R.id.teacherImage);
            teacherName = (TextView) v.findViewById(R.id.teacherName);
            teacherDesc = (TextView) v.findViewById(R.id.teacherDesc);
            evaluation = (TextView) v.findViewById(R.id.evaluation);
            courseLayout = (LinearLayout) v.findViewById(R.id.courseLayout);
            moreLayout = (LinearLayout) v.findViewById(R.id.moreLayout);
            LinearLayout.LayoutParams llpTeacher=new LinearLayout.LayoutParams(-1, ImageUtil.dip2px(activity, 60));
            for (int i=0;i<position;i++){
                courseLayout.addView(getCourseLayout(), llpTeacher);
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
        }

        private View getCourseLayout() {
            View v=inflater.inflate(R.layout.teacher_course_item, null);
            TextView courseType= (TextView) v.findViewById(R.id.courseType);
            TextView courseName= (TextView) v.findViewById(R.id.courseName);
            TextView coursePrice= (TextView) v.findViewById(R.id.coursePrice);
            TextView buyerNum= (TextView) v.findViewById(R.id.buyerNum);
            return v;
        }

    }
}
