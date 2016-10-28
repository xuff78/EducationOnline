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
import com.education.online.act.video.Comment;
import com.education.online.bean.CourseDetailBean;
import com.education.online.bean.CourseEvaluate;
import com.education.online.bean.CourseExtm;
import com.education.online.bean.CreatUserInfo;
import com.education.online.bean.EvaluateBean;
import com.education.online.bean.EvaluateListBean;
import com.education.online.util.ImageUtil;
import com.education.online.util.ScreenUtil;
import com.education.online.view.RatingBar;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/8/25.
 */

public class CourseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Activity act;
    private LayoutInflater listInflater;
    private CourseDetailBean courseDetailBean;
    private EvaluateListBean evaluateListBean;
    private ImageLoader imageLoader;


    public CourseAdapter(Activity act, CourseDetailBean courseDetailBean, EvaluateListBean evaluateListBean) {
        this.act = act;
        listInflater = LayoutInflater.from(act);
        imageLoader = ImageLoader.getInstance();
        this.courseDetailBean = courseDetailBean;
        this.evaluateListBean = evaluateListBean;
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
            View view = listInflater.inflate(R.layout.course_information, null);
            vh = new CourseAdapter.CourseHolder(view, pos);
        } else if (pos == 1) {
            View view = listInflater.inflate(R.layout.teacher_introduction, null);
            view.setLayoutParams(new RecyclerView.LayoutParams(-1, -2));
            vh = new CourseAdapter.TeacherInfoHolder(view, pos);
        } else if (pos == 2) {
            View view = listInflater.inflate(R.layout.course_detail, null);
            view.setLayoutParams(new RecyclerView.LayoutParams(-1, -2));
            vh = new CourseDetailsHolder(view, pos);
        } else if (pos > 2) {
            View view = listInflater.inflate(R.layout.comments_fragment, null);
            view.setLayoutParams(new RecyclerView.LayoutParams(-1, -2));
            vh = new CommentsHolder(view, pos);
        }
        return vh;
    }

    @Override
    //视图与数据的绑定，留待以后实现
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int pos) {

        if (pos == 0) {
            CourseHolder vh = (CourseHolder) holder;
            imageLoader.displayImage(ImageUtil.getImageUrl(courseDetailBean.getImg()), vh.courseImg);
            vh.courseName.setText(courseDetailBean.getCourse_name());
            vh.coursePrice.setText("￥" + courseDetailBean.getPrice());
            vh.studentNum.setText("班级人数" + courseDetailBean.getMax_follow() + "人，已报名" + courseDetailBean.getFollow() + "人");
            vh.praisePercent.setText(courseDetailBean.getHot() + "%好评");
            vh.totalSerial.setText("共" + courseDetailBean.getCourse_extm().size() + "次课");
            DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日");
            Date date = null;
            try {
                date = format1.parse(courseDetailBean.getCourse_extm().get(0).getCourseware_date());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            vh.courseTime.setText(formatter.format(date) + "开课");

        } else if (pos == 1) {
            TeacherInfoHolder vh = (TeacherInfoHolder) holder;
            vh.teacherName.setText(courseDetailBean.getUser_info().getUser_name());
            vh.teacherTitles.setText(courseDetailBean.getUser_info().getIntroduction());
            imageLoader.displayImage(ImageUtil.getImageUrl(courseDetailBean.getUser_info().getAvatar()), vh.teacherPotrait);
            vh.teacherScore.setText(courseDetailBean.getUser_info().getAverage() + "分");
            vh.teacherComments.setText("评论" + courseDetailBean.getUser_info().getEvaluate_count());
        } else if (pos == 2) {
            CourseDetailsHolder vh = (CourseDetailsHolder) holder;
            vh.courseDetail.setText(courseDetailBean.getIntroduction());
            vh.courseArrangement.setText(courseDetailBean.getPlan());
            vh.courseArrangement.setText(courseDetailBean.getPlan());
        } else {
            CommentsHolder vh = (CommentsHolder) holder;
            for (int i = 0; i < evaluateListBean.getEvaluateList().size(); i++) {
                vh.userName.setText(evaluateListBean.getEvaluateList().get(i).getUser_name());
                DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat formatter1 = new SimpleDateFormat("HH:mm");
                Date date = new Date();
                try {
                    date = format1.parse(evaluateListBean.getEvaluateList().get((i)).getEvaluate_date());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                vh.commentTime.setText(formatter1.format(date));
                vh.commentDate.setText(formatter.format(date));
                vh.ratingBar.setStar(Float.parseFloat(evaluateListBean.getEvaluateList().get(i).getStar()));
                imageLoader.displayImage(ImageUtil.getImageUrl(evaluateListBean.getEvaluateList().get(i).getAvatar()), vh.potrait);
            }

        }


    }

    @Override
    public int getItemCount() {
        List<EvaluateBean> list = evaluateListBean.getEvaluateList();
        return (3 + list.size());
        //return 4;
    }


    public class CourseHolder extends RecyclerView.ViewHolder {
        ImageView courseImg;
        private LinearLayout commentlayout;
        TextView coursePrice, courseTime, commentsNum, totalSerial, praisePercent, studentNum, courseName;

        public CourseHolder(View v, int pos) {
            super(v);
            commentlayout = (LinearLayout) v.findViewById(R.id.commnetLayout);
            commentlayout.setOnClickListener(listener);
            courseName = (TextView) v.findViewById(R.id.courseName);
            courseImg = (ImageView) v.findViewById(R.id.courseImg);
            coursePrice = (TextView) v.findViewById(R.id.coursePrice);
            courseTime = (TextView) v.findViewById(R.id.courseTime);
            commentsNum = (TextView) v.findViewById(R.id.commentsNum);
            totalSerial = (TextView) v.findViewById(R.id.totalSerial);
            praisePercent = (TextView) v.findViewById(R.id.praisePercent);
            studentNum = (TextView) v.findViewById(R.id.studentNum);
        }

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.commnetLayout:
                        Intent intent = new Intent();
                        intent.setClass(act, Comment.class);
                        intent.putExtra("courseImg", courseDetailBean.getImg());
                        intent.putExtra("courseName", courseDetailBean.getCourse_name());
                        intent.putExtra("courseIntroduction", courseDetailBean.getIntroduction());
                        intent.putExtra("course_id", courseDetailBean.getCourse_id());
                        act.startActivity(intent);
                        break;
                }
            }
        };
    }

    public class TeacherInfoHolder extends RecyclerView.ViewHolder {

        ImageView teacherPotrait;
        TextView teacherName, teacherTitles, teacherScore, teacherComments;
        View viewbottom;

        public TeacherInfoHolder(View v, int pos) {
            super(v);
            teacherPotrait = (ImageView) v.findViewById(R.id.teacherpotrait);
            teacherName = (TextView) v.findViewById(R.id.teacherName);
            teacherScore = (TextView) v.findViewById(R.id.teacherScore);
            teacherTitles = (TextView) v.findViewById(R.id.teacherTitles);
            teacherComments = (TextView) v.findViewById(R.id.teacherComments);
            viewbottom = v.findViewById(R.id.bottomview);
        }
    }

    public class CourseDetailsHolder extends RecyclerView.ViewHolder {

        TextView courseDetail, coursedate, coursetime, courseArrangement;

        public CourseDetailsHolder(View v, int pos) {
            super(v);

            courseDetail = (TextView) v.findViewById(R.id.courseDetail);
            coursedate = (TextView) v.findViewById(R.id.coursedate);
            coursetime = (TextView) v.findViewById(R.id.coursetime);
            courseArrangement = (TextView) v.findViewById(R.id.courseArrangement);
        }


    }

    public class CommentsHolder extends RecyclerView.ViewHolder {
        ImageView potrait;
        TextView userName, userComments, commentDate, commentTime;
        RatingBar ratingBar;

        public CommentsHolder(View v, int pos) {
            super(v);
            userName = (TextView) v.findViewById(R.id.userName);
            potrait = (ImageView) v.findViewById(R.id.potrait);
            ratingBar = (RatingBar) v.findViewById(R.id.ratingbar);
            userComments = (TextView) v.findViewById(R.id.userComments);
            commentDate = (TextView) v.findViewById(R.id.commentDate);
            commentTime = (TextView) v.findViewById(R.id.commentTime);

        }
    }
}



