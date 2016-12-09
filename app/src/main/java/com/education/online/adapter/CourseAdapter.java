package com.education.online.adapter;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.education.online.R;
import com.education.online.act.teacher.TeacherInformationPage;
import com.education.online.act.video.Comment;
import com.education.online.bean.CourseDetailBean;
import com.education.online.bean.CourseEvaluate;
import com.education.online.bean.CourseExtm;
import com.education.online.bean.CreatUserInfo;
import com.education.online.bean.EvaluateBean;
import com.education.online.bean.EvaluateListBean;
import com.education.online.util.ActUtil;
import com.education.online.util.Constant;
import com.education.online.util.ImageUtil;
import com.education.online.util.ScreenUtil;
import com.education.online.view.RatingBar;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/8/25.
 */

public class CourseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Activity act;
    private LayoutInflater listInflater;
    private CourseDetailBean courseDetailBean;
    private ImageLoader imageLoader;
    private ImageView teacherIcon;
    private List<EvaluateBean> evaluateList;
    private String loadingHint = "";

    public CourseAdapter(Activity act, CourseDetailBean courseDetailBean, List<EvaluateBean> evaluateList) {
        this.act = act;
        listInflater = LayoutInflater.from(act);
        imageLoader = ImageLoader.getInstance();
        this.courseDetailBean = courseDetailBean;
        this.evaluateList = evaluateList;
    }

    @Override
    public int getItemViewType(int position) {
        // TODO Auto-generated method stub
        return getItemCount() - 1 > position ? position : -1;
    }

    public void setLoadingHint(String hint) {
        loadingHint = hint;
        notifyDataSetChanged();
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
        } else if (pos == -1) {
            View view = listInflater.inflate(R.layout.footer_layout, null);
            view.setLayoutParams(new RecyclerView.LayoutParams(-1, ImageUtil.dip2px(act, 45)));
            vh = new FooterViewHolder(view);
        }
        return vh;
    }

    @Override
    //视图与数据的绑定，留待以后实现
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int pos) {

        if (holder instanceof FooterViewHolder) {
            FooterViewHolder fvh = (FooterViewHolder) holder;
            fvh.footerHint.setText(loadingHint);
            if(evaluateList.size()==0)
            {
                fvh.footerHint.setText("暂无评价");
            }
        } else if (pos == 0) {
            CourseHolder vh = (CourseHolder) holder;
            imageLoader.displayImage(ImageUtil.getImageUrl(courseDetailBean.getImg()), vh.courseImg);
            vh.courseName.setText(courseDetailBean.getCourse_name());
            vh.courseoldprice.setText(courseDetailBean.getOriginal_price());
            vh.courseoldprice.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG);
            vh.coursePrice.setText(ActUtil.getPrice(courseDetailBean.getPrice()));
            vh.studentNum.setText("班级人数" + courseDetailBean.getMax_follow() + "人，已报名" + courseDetailBean.getFollow() + "人");
            vh.praisePercent.setText((int) (Float.valueOf(courseDetailBean.getUser_info().getAverage()) / 5 * 100) + "%好评");
            vh.totalSerial.setText("共" + courseDetailBean.getCourse_extm().size() + "次课");
            if (courseDetailBean.getCourse_extm().size() > 0) {
                String datestring = courseDetailBean.getCourse_extm().get(0).getCourseware_date();
                vh.courseTime.setText(ActUtil.getDataTime(datestring) + "开课");
            }

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
            if(courseDetailBean.getPlan().length()>0)
                vh.coursedate.setText(courseDetailBean.getPlan()+"\n\n");

            String temp = "";
            String begindate = "";
            String enddate ="";
            String tail = "\n";
            SimpleDateFormat dateData = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat timeData = new SimpleDateFormat("HH:mm");
            SimpleDateFormat dayDate = new SimpleDateFormat("yyyy年MM月dd日");
            Calendar cal = Calendar.getInstance();

            try {
                for (int i = 0; i < courseDetailBean.getCourse_extm().size(); i++) {

                    CourseExtm courseExtm = courseDetailBean.getCourse_extm().get(i);
                    String datestring = courseExtm.getCourseware_date();
                    Date date1 = dateData.parse(datestring);
                    cal.setTime(date1);
                    String startdate = dayDate.format(date1);
                    String starttime = timeData.format(date1);
                    String endtime = "";
                    if(i==0){
                        begindate = dayDate.format(date1);
                    }
                    if(i==courseDetailBean.getCourse_extm().size()-1){
                        enddate= dayDate.format(date1);
                    }
                    if(courseExtm.getTime_len().length()>0) {
                        cal.add(Calendar.MINUTE, Integer.parseInt(courseExtm.getTime_len()));
                        endtime = timeData.format(cal.getTime());
                    }
                    temp = temp + startdate + starttime + "~" + endtime + ActUtil.getCourseStateTxt(courseExtm.getState()) + tail;
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
            vh.courseArrangement.setText(temp);
            vh.coursedate.setText(begindate+"~"+enddate);


        } else if (pos > 2) {
            CommentsHolder vh = (CommentsHolder) holder;
            EvaluateBean evaluateBean = evaluateList.get(pos - 3);
            vh.userName.setText(evaluateBean.getUser_name());
            vh.userComments.setText(evaluateBean.getInfo());
            vh.commentDate.setText(ActUtil.getDataTime(evaluateBean.getEvaluate_date()));
            vh.ratingBar.setStar(Float.parseFloat(evaluateBean.getStar()));
            imageLoader.displayImage(ImageUtil.getImageUrl(evaluateBean.getAvatar()), vh.potrait);
        }
    }

    @Override
    public int getItemCount() {
        return 3 + evaluateList.size() + 1;
    }


    public class CourseHolder extends RecyclerView.ViewHolder {
        ImageView courseImg;
        private LinearLayout commentlayout;
        TextView coursePrice, courseTime, commentsNum, totalSerial, praisePercent, studentNum, courseName,courseoldprice;

        public CourseHolder(View v, int pos) {
            super(v);
            commentlayout = (LinearLayout) v.findViewById(R.id.commnetLayout);
            commentlayout.setOnClickListener(listener);
            courseoldprice = (TextView) v.findViewById(R.id.courseoldprice);
            courseName = (TextView) v.findViewById(R.id.courseName);
            courseImg = (ImageView) v.findViewById(R.id.courseImg);
            coursePrice = (TextView) v.findViewById(R.id.coursePrice);
            courseTime = (TextView) v.findViewById(R.id.courseTime);
            commentsNum = (TextView) v.findViewById(R.id.commentsNum);
            totalSerial = (TextView) v.findViewById(R.id.totalSerial);
            praisePercent = (TextView) v.findViewById(R.id.praisePercent);
            studentNum = (TextView) v.findViewById(R.id.studentNum);
        }
    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            switch (v.getId()) {
                case R.id.commnetLayout:
                    intent.setClass(act, Comment.class);
                    intent.putExtra("courseImg", courseDetailBean.getImg());
                    intent.putExtra("courseName", courseDetailBean.getCourse_name());
                    intent.putExtra("courseIntroduction", courseDetailBean.getIntroduction());
                    intent.putExtra("course_id", courseDetailBean.getCourse_id());
                    act.startActivity(intent);
                    break;
                case R.id.teacherLayout:
                    intent.setClass(act, TeacherInformationPage.class);
                    CreatUserInfo teacher = courseDetailBean.getUser_info();
                    intent.putExtra("Avatar", teacher.getAvatar());
                    intent.putExtra("Name", teacher.getUser_name());
                    intent.putExtra(Constant.UserCode, courseDetailBean.getUsercode());
                    ActUtil.startAnimActivity(act, intent, v.findViewById(R.id.teacherpotrait), "headIcon");
//                    act.startActivity(intent);
                    break;
            }
        }
    };

    public class TeacherInfoHolder extends RecyclerView.ViewHolder {

        ImageView teacherPotrait;
        TextView teacherName, teacherTitles, teacherScore, teacherComments;
        View viewbottom;

        public TeacherInfoHolder(View v, int pos) {
            super(v);
            teacherPotrait = (ImageView) v.findViewById(R.id.teacherpotrait);
            teacherIcon = teacherPotrait;
            teacherName = (TextView) v.findViewById(R.id.teacherName);
            teacherScore = (TextView) v.findViewById(R.id.teacherScore);
            teacherTitles = (TextView) v.findViewById(R.id.teacherTitles);
            teacherComments = (TextView) v.findViewById(R.id.teacherComments);
            viewbottom = v.findViewById(R.id.bottomview);
            v.findViewById(R.id.teacherLayout).setOnClickListener(listener);
        }
    }

    public class CourseDetailsHolder extends RecyclerView.ViewHolder {

        TextView courseDetail, coursedate, coursetime, courseArrangement;

        public CourseDetailsHolder(View v, int pos) {
            super(v);

            courseDetail = (TextView) v.findViewById(R.id.courseDetail);
            coursedate = (TextView) v.findViewById(R.id.coursedate);
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
            ratingBar.setmClickable(false);
            userComments = (TextView) v.findViewById(R.id.userComments);
            commentDate = (TextView) v.findViewById(R.id.commentDate);
            commentTime = (TextView) v.findViewById(R.id.commentTime);

        }
    }
}



