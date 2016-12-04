package com.education.online.adapter;

import android.app.Activity;
import android.content.Intent;
import android.provider.Settings;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.callback.AVIMConversationCallback;
import com.avoscloud.leanchatlib.controller.ChatManager;
import com.education.online.R;
import com.education.online.act.CourseMainPage;
import com.education.online.act.LiveCourseDetail;
import com.education.online.act.video.Comment;
import com.education.online.bean.CourseBean;
import com.education.online.bean.CourseDetailBean;
import com.education.online.util.ImageUtil;
import com.education.online.util.ToastUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Administrator on 2016/8/21.
 */

public class MyOnlineCourseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    ArrayList<CourseBean> onlineCourseBeanArrayList;
    private Activity activity;
    private LayoutInflater inflater;
    private ImageLoader imageLoader;
    public MyOnlineCourseAdapter(Activity activity, ArrayList<CourseBean> onlineCourseArraryList) {
        this.activity = activity;
        this.onlineCourseBeanArrayList = onlineCourseArraryList;
        inflater = LayoutInflater.from(activity);
        imageLoader = ImageLoader.getInstance();

    }

    @Override
    public int getItemViewType(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    //创建一个视图
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int pos) {
        RecyclerView.ViewHolder vh = null;
        View view = inflater.inflate(R.layout.mylive_online_items, null);
        view.setTag(pos);
        vh = new CourseItemHolder(view, pos);
        return vh;
    }

    @Override
    //绑定数据源
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        CourseBean course = onlineCourseBeanArrayList.get(position);
        //将数据源与视图布局绑定，暂时先不写
        CourseItemHolder courseItemHolder = (CourseItemHolder) holder;
        imageLoader.displayImage(ImageUtil.getImageUrl(course.getImg()), courseItemHolder.courseImage);
        courseItemHolder.courseName.setText(course.getCourse_name());
        courseItemHolder.CourseTime.setText(course.getCourseware_date()+"开始");
        courseItemHolder.enterCourse.setTag(position);
        if(course.getCount().length()>0)
        courseItemHolder.courseNum.setText("共"+course.getCount()+"节课");
        SimpleDateFormat dateData=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      long current_time= System.currentTimeMillis();
        String date_time = course.getCourseware_date();
        Date time = null;
        try {
            time=  dateData.parse(date_time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(time.getTime()-current_time>15*60*1000)
            courseItemHolder.enterCourse.setVisibility(View.GONE);

    }

    @Override
    public int getItemCount() {
        return onlineCourseBeanArrayList.size();
    }

    public class CourseItemHolder extends RecyclerView.ViewHolder {
        ImageView courseImage;
        TextView courseName;
        TextView CourseTime;
        TextView enterCourse;
        TextView courseNum;
        RelativeLayout total_layout;
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                int pos = (int) v.getTag();
                final CourseBean courseBean =onlineCourseBeanArrayList.get(pos);
                switch (v.getId()){
                    case R.id.enterCourse:
                      final  String ConversationId=courseBean.getConversationId();
                        if(ConversationId.length()>0){
                            ChatManager.getInstance().joinCoversation(ConversationId, new AVIMConversationCallback(){

                                @Override
                                public void done(AVIMException e) {
                                    if(e==null) {
                                        Intent intent = new Intent(activity, LiveCourseDetail.class);
                      //                  intent.putExtra(CourseDetailBean.Name, courseDetailBean);
                                        intent.putExtra("Name", courseBean.getCourse_name());
                                        intent.putExtra(com.avoscloud.leanchatlib.utils.Constants.CONVERSATION_ID, ConversationId);
                                        activity.startActivity(intent);
                                    }else
                                        ToastUtils.displayTextShort(activity, "加入失败请稍后重试");
                                }
                            });
                            break;
                        }else {
                            ToastUtils.displayTextShort(activity, "未找到直播室");
                        }
                    case R.id.total_layout:
                       intent.setClass(activity,CourseMainPage.class);
                        intent.putExtra("course_id",courseBean.getCourse_id());
                        activity.startActivity(intent);
                        break;
                }

            }
        };

        public CourseItemHolder(View v, final int position) {
            super(v);
            courseNum =  (TextView) v.findViewById(R.id.courseNum);
            courseImage = (ImageView) v.findViewById(R.id.CourseImage);
            courseName = (TextView) v.findViewById(R.id.CourseName);
            CourseTime = (TextView) v.findViewById(R.id.CourseTime);
            enterCourse = (TextView) v.findViewById(R.id.enterCourse);
            enterCourse.setOnClickListener(listener);
            total_layout = (RelativeLayout) v.findViewById(R.id.total_layout);
            total_layout.setOnClickListener(listener);
        }


    }
}

//            timeBeginToEnd = v.findViewById();




