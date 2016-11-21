package com.education.online.adapter;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.education.online.R;
import com.education.online.act.VideoPlay;
import com.education.online.act.ViewerActivity;
import com.education.online.bean.CourseBean;
import com.education.online.bean.EvaluateBean;
import com.education.online.bean.TeacherBean;
import com.education.online.util.ActUtil;
import com.education.online.util.ImageUtil;
import com.education.online.util.ScreenUtil;
import com.education.online.util.VideoThumbnailLoader;
import com.education.online.view.RatingBar;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static java.security.AccessController.getContext;

/**
 * Created by Gaozhiqun on 2016/8/25.
 */

public class TeacherMainAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Activity act;
    private LayoutInflater inflater;
    private int itemWidth = 0, itemHeight = 0, imgHeight = 0;
    private int padding10 = 0;
    private int ItemType = 1;
    private TeacherBean teacherBean;
    private ImageLoader imageLoader;
    private List<EvaluateBean> evaluations=new ArrayList<>();
    private String totalcomment="";
    private EvaluateCallback callback;
    private float average=0f;

    public void setItemType(int itemType) {
        ItemType = itemType;
        notifyDataSetChanged();
    }

    public int getItemType() {
        return ItemType;
    }

    public TeacherMainAdapter(Activity act, TeacherBean teacherBean, List<EvaluateBean> evaluations, EvaluateCallback callback) {
        this.act = act;
        this.callback=callback;
        this.teacherBean=teacherBean;
        this.evaluations=evaluations;
        inflater = LayoutInflater.from(act);
        padding10 = ImageUtil.dip2px(act, 5);
        itemWidth = (ScreenUtil.getWidth(act) - 2 * padding10) / 4; //小图片和文字的形成点击区域的边长
        imgHeight = itemWidth - 2 * padding10; //小图片的边长
        imageLoader=ImageLoader.getInstance();
    }

    @Override
    public int getItemViewType(int position) {
        int i = getItemType();
//        if (position == 0) {
//            return 1;
//        }else
        if(position ==0){
            if (i==1) {
                return 2;
            } else if(i==2) {
                return 3;
            }else if (i==3) {
                return 4;
            }else if (i==4){
                return 5;
            }
        } else if (position>0){
            if (i==2){
                return 3;
            }else if (i==4){
                if(getItemCount()-1==position)
                    return -1;
                else
                    return 6;
            }
        }
        return 2;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup arg0, int viewType) {
        RecyclerView.ViewHolder vh = null;
        View view;
        switch (viewType) {
//            case 1:
//                view = inflater.inflate(R.layout.teacher_mainpart, null);//主要部分
//                vh = new MainHolder(view);
//                break;
            case 2:
                view = inflater.inflate(R.layout.teacher_brief, null);//简介
                vh = new BriefHolder(view);
                break;
            case 3:
                view = inflater.inflate(R.layout.teachter_course_item, null);//课程项
                vh = new CourseHolder(view);
                break;
            case 4:
                LinearLayout viewalbum = new LinearLayout(act);
                viewalbum.setOrientation(LinearLayout.VERTICAL);
                viewalbum.setLayoutParams(new RecyclerView.LayoutParams(-1, -2));//相册
                vh = new AlbumHolder(viewalbum, viewType);
                break;
            case 5:
                view = inflater.inflate(R.layout.totalcomments_layout, null);//评论头
                vh = new TotalCommentsHolder(view, viewType);
                break;
            case 6:
                view = inflater.inflate(R.layout.comments_fragment, null);//评论们
                view.setLayoutParams(new RecyclerView.LayoutParams(-1, -2));
                vh = new CommentsHolder(view, viewType);
                break;
            case -1:
                TextView footer= new TextView(act);
                footer.setLayoutParams(new RecyclerView.LayoutParams(-1, ImageUtil.dip2px(act, 40)));
                footer.setGravity(Gravity.CENTER);
                footer.setTextColor(act.getResources().getColor(R.color.normal_gray));
                footer.setTextSize(14);
                footer.setText("全部加载");
                vh = new FooterViewHolder(footer);
                break;
        }
        return vh;
    }

    @Override
    //视图与数据的绑定，留待以后实现
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int pos) {
        int i = getItemType();
//        if (pos == 0) {
//            MainHolder vh = (MainHolder) holder;
//            imageLoader.displayImage(ImageUtil.getImageUrl(teacherBean.getAvatar()), vh.teacherpotrait);
//            if(teacherBean.getGender().equals("1")){
//                vh.teacherSexual.setText("男");
//            }else if(teacherBean.getGender().equals("0")){
//                vh.teacherSexual.setText("女");
//            }
//            vh.teacherName.setText(teacherBean.getName());
//            vh.teacherTitles.setText(teacherBean.getSpecialty());
//            vh.teachingExperience.setText(teacherBean.getWork_time()+"年教龄");
//            if(teacherBean.getIs_validate().equals("1"))
//                vh.identityConfirmed.setText("已认证");
//            else
//                vh.identityConfirmed.setText("未认证");
//            vh.fansNum.setText(teacherBean.getAttention_count());
//            vh.studentNum.setText(teacherBean.getStudent_count());
//            vh.praisePercent.setText(teacherBean.getGood_evaluate_ratio());
//        } else
        if (pos == 0){
            if(i==1){
                BriefHolder vh =(BriefHolder) holder;
                String identityDetail="";
//            if(teacherBean.getIs_ext_validate().equals("1"))
//                identityDetail+="用户资料认证 ";
                if(teacherBean.getIs_id_validate().equals("1"))
                    identityDetail+="身份认证 ";
                if(teacherBean.getIs_tc_validate().equals("1"))
                    identityDetail+="教师认证 ";
                if(teacherBean.getIs_edu_bg_validate().equals("1"))
                    identityDetail+="学历认证 ";
                if(teacherBean.getIs_specialty_validate().equals("1"))
                    identityDetail+="专业资格认证 ";
                if(teacherBean.getIs_unit_validate().equals("1"))
                    identityDetail+="单位认证 ";
                vh.teacherIdentify.setText(identityDetail);
                vh.teacherMarks.setText(teacherBean.getTags());
                vh.teacherSelfIntro.setText(teacherBean.getIntroduction());
                vh.teacherExperience.setText(teacherBean.getExperience());
            }else if(i==2){
                CourseHolder vh = (CourseHolder) holder;
                CourseBean courseBean=teacherBean.getCourse_info().get(0);
                imageLoader.displayImage(ImageUtil.getImageUrl(courseBean.getImg()), vh.teacherImage);
                vh.coursePrice.setText(ActUtil.getPrice(courseBean.getPrice()));
                vh.courseName.setText(courseBean.getCourse_name());
                vh.totallength.setText("共"+courseBean.getCount()+"节");
                vh.totallength.setVisibility(View.VISIBLE);
                vh.followNum.setText(courseBean.getFollow()+"人报名");
            }else if (i==3){
                AlbumHolder vh = (AlbumHolder) holder;
            }else if (i==4){
                TotalCommentsHolder vh = (TotalCommentsHolder) holder;
                vh.ratingbar.setStar(average);
                vh.averageScore.setText(average+"分");
                vh.totalcomments.setText("共有"+totalcomment+"条评论");
                if(evaluations.size()==0)
                    callback.requestData();
            }
        }else if (pos > 0){
            if (i==2) {
                CourseHolder vh = (CourseHolder) holder;
                CourseBean courseBean=teacherBean.getCourse_info().get(pos-1);
                imageLoader.displayImage(ImageUtil.getImageUrl(courseBean.getImg()), vh.teacherImage);
                vh.coursePrice.setText(ActUtil.getPrice(courseBean.getPrice()));
                vh.courseName.setText(courseBean.getCourse_name());
                vh.totallength.setText("共"+courseBean.getCount()+"节");
                vh.totallength.setVisibility(View.VISIBLE);
                vh.followNum.setText(courseBean.getFollow()+"人报名");
            }else if (i==4){
                if(pos<getItemCount()-1) {
                    CommentsHolder vh = (CommentsHolder) holder;
                    EvaluateBean evaluateBean = evaluations.get(pos - 1);
                    vh.ratingbar.setStar(Float.valueOf(evaluateBean.getStar()));
                    vh.commentDate.setText(evaluateBean.getEvaluate_date());
                    imageLoader.displayImage(ImageUtil.getImageUrl(evaluateBean.getAvatar()), vh.potrait);
                    vh.userName.setText(evaluateBean.getUser_name());
                    vh.userComments.setText(evaluateBean.getInfo());
                }
            }
        }

    }

    @Override
    public int getItemCount() {
        int i = getItemType();
        switch (i) {
            case 1:
                return 1;
            case 2:
                return teacherBean.getCourse_info().size();
            case 3:
                return 1;
            case 4:
                return 2+evaluations.size();
        }
        return 2;
    }

    public void setAverage(String average) {
        this.average = Float.valueOf(average);
    }


    /////////////////////////mainholder////////////////////
    public class MainHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView teacherpotrait;
        private TextView teacherSexual, teacherName, teacherTitles, teachingExperience, identityConfirmed, fansNum, studentNum, praisePercent;
        private LinearLayout brief, subjects, photoalbum, teachercomments;
        private TextView textbrief, textsubjects, textphotoalbum, textteachercomments;
        private View viewbrief, viewsubjects, viewphotoalbum, viewteachercomments;
        private View lastSelectedview;
        private int lastSelectedPosition;

        public MainHolder(View v) {
            super(v);
            teacherpotrait = (ImageView) v.findViewById(R.id.teacherpotrait);
            teacherSexual = (TextView) v.findViewById(R.id.teacherSexual);
            teacherName = (TextView) v.findViewById(R.id.teacherName);
            teacherTitles = (TextView) v.findViewById(R.id.teacherTitles);
            teachingExperience = (TextView) v.findViewById(R.id.teachingExperience);
            identityConfirmed = (TextView) v.findViewById(R.id.identityConfirmed);

            fansNum = (TextView) v.findViewById(R.id.fansNum);
            studentNum = (TextView) v.findViewById(R.id.studentNum);
            praisePercent = (TextView) v.findViewById(R.id.praisePercent);

            brief = (LinearLayout) v.findViewById(R.id.brief);
            brief.setOnClickListener(this);
            subjects = (LinearLayout) v.findViewById(R.id.subjects);
            subjects.setOnClickListener(this);
            photoalbum = (LinearLayout) v.findViewById(R.id.photoalbum);
            photoalbum.setOnClickListener(this);
            teachercomments = (LinearLayout) v.findViewById(R.id.teachercomments);
            teachercomments.setOnClickListener(this);

            textbrief = (TextView) v.findViewById(R.id.textbrief);
            textsubjects = (TextView) v.findViewById(R.id.textsubjects);
            textphotoalbum = (TextView) v.findViewById(R.id.textphotoalbum);
            textteachercomments = (TextView) v.findViewById(R.id.textteachercomments);

            viewbrief = v.findViewById(R.id.viewbrief);
            viewsubjects = v.findViewById(R.id.viewsubjects);
            viewphotoalbum = v.findViewById(R.id.viewphotoalbum);
            viewteachercomments = v.findViewById(R.id.viewteachercomments);

        }

        @Override
        public void onClick(View view) {
            if (view != lastSelectedview) {
                setStatusFalse(lastSelectedPosition);
                switch (view.getId()) {
                    case R.id.brief:
                        lastSelectedview = brief;
                        lastSelectedPosition = 0;
                        textbrief.setTextColor(textbrief.getResources().getColor(R.color.dark_orange));
                        viewbrief.setVisibility(View.VISIBLE);
                        setItemType(1);
                        getItemCount();
                        break;
                    case R.id.subjects:
                        lastSelectedview = subjects;
                        lastSelectedPosition = 1;
                        textsubjects.setTextColor(textbrief.getResources().getColor(R.color.dark_orange));
                        viewsubjects.setVisibility(View.VISIBLE);
                        setItemType(2);
                        getItemCount();
                        break;
                    case R.id.photoalbum:

                        lastSelectedview = photoalbum;
                        lastSelectedPosition = 2;
                        textphotoalbum.setTextColor(textbrief.getResources().getColor(R.color.dark_orange));
                        viewphotoalbum.setVisibility(View.VISIBLE);
                        setItemType(3);
                        getItemCount();
                        break;
                    case R.id.teachercomments:
                        lastSelectedview = teachercomments;
                        lastSelectedPosition = 3;
                        textteachercomments.setTextColor(textteachercomments.getResources().getColor(R.color.dark_orange));
                        viewteachercomments.setVisibility(View.VISIBLE);
                        setItemType(4);
                        getItemCount();
                        break;
                }
            }
        }

        public void setStatusFalse(int pos) {
            switch (pos) {
                case 0:
                    textbrief.setTextColor(textbrief.getResources().getColor(R.color.light_gray));
                    viewbrief.setVisibility(View.INVISIBLE);
                    break;
                case 1:
                    textsubjects.setTextColor(textsubjects.getResources().getColor(R.color.light_gray));
                    viewsubjects.setVisibility(View.INVISIBLE);
                    break;
                case 2:
                    textphotoalbum.setTextColor(textphotoalbum.getResources().getColor(R.color.light_gray));
                    viewphotoalbum.setVisibility(View.INVISIBLE);
                    break;
                case 3:
                    textteachercomments.setTextColor(textteachercomments.getResources().getColor(R.color.light_gray));
                    viewteachercomments.setVisibility(View.INVISIBLE);
                    break;
            }
        }
    }

    ////////////////////////page1holder

    public class BriefHolder extends RecyclerView.ViewHolder {
        TextView teacherIdentify, teacherMarks, teacherSelfIntro, teacherExperience;


        public BriefHolder(View itemView) {
            super(itemView);
            teacherIdentify = (TextView) itemView.findViewById(R.id.teacherIdentify);
            teacherMarks = (TextView) itemView.findViewById(R.id.teacherMarks);
            teacherSelfIntro = (TextView) itemView.findViewById(R.id.teacherSelfIntro);
            teacherExperience = (TextView) itemView.findViewById(R.id.teacherExperience);
        }
    }


////////////////////////////////////////////page2holder

    public class CourseHolder extends RecyclerView.ViewHolder {
        ImageView teacherImage;
        TextView courseName, coursePrice, totallength, followNum;


        public CourseHolder(View itemView) {
            super(itemView);
            teacherImage = (ImageView) itemView.findViewById(R.id.teacherImage);
            courseName = (TextView) itemView.findViewById(R.id.courseName);
            coursePrice = (TextView) itemView.findViewById(R.id.coursePrice);
            totallength = (TextView) itemView.findViewById(R.id.totallength);
            followNum = (TextView) itemView.findViewById(R.id.followNum);
        }
    }

    ////////////////////////////////////////page3holder
    public class AlbumHolder extends RecyclerView.ViewHolder {

        LinearLayout itemsLayout;


        public AlbumHolder(View itemView, int pos) {
            super(itemView);
            itemsLayout = (LinearLayout) itemView;
            itemsLayout.setPadding(padding10, 0, padding10, padding10 * 2);
            LinearLayout.LayoutParams llpitem = new LinearLayout.LayoutParams(itemWidth, -2);
            LinearLayout linelayout = new LinearLayout(act);
            linelayout.setOrientation(LinearLayout.HORIZONTAL);
            List<String> imgs=teacherBean.getPhoto_album();
            for (int i = 0; i < imgs.size(); i++) {
                linelayout.addView(getSubjectItemView(imgs.get(i), i), llpitem);
                if (i % 4 == 3 || i == imgs.size()-1) {
                    itemsLayout.addView(linelayout);
                    linelayout = new LinearLayout(act);

                }
            }

            List<String> videos=teacherBean.getVideo();
            if(videos.size()>0){
                if(imgs.size()>0) {
                    View v=new View(act);
                    LinearLayout.LayoutParams lineLP = new LinearLayout.LayoutParams(-1, 1);
                    lineLP.topMargin=padding10;
                    v.setBackgroundResource(R.color.light_gray);
                    itemsLayout.addView(v, lineLP);
                }
                for (int i = 0; i < videos.size(); i++) {
                    linelayout.addView(getSubjectVideoView(videos.get(i), i), llpitem);
                    if (i % 4 == 3 || i == videos.size()-1) {
                        itemsLayout.addView(linelayout);
                        linelayout = new LinearLayout(act);

                    }
                }
            }
        }

        private RelativeLayout getSubjectVideoView(String videoUrl, int pos) {

            RelativeLayout.LayoutParams llpimg = new RelativeLayout.LayoutParams(imgHeight, imgHeight);
            llpimg.bottomMargin = 5;
            RelativeLayout layout = new RelativeLayout(act);
            layout.setPadding(padding10, padding10 * 2, padding10, 0);
            ImageView img = new ImageView(act);
            img.setBackgroundResource(R.color.whitesmoke);
            img.setScaleType(ImageView.ScaleType.CENTER_CROP);
            img.setOnClickListener(videolistener);
            img.setTag(pos);
            VideoThumbnailLoader.getIns().display(act, ImageUtil.getImageUrl(videoUrl),img,
                    100, 100, new VideoThumbnailLoader.ThumbnailListener(){
                        @Override
                        public void onThumbnailLoadCompleted(String url, ImageView iv, Bitmap bitmap) {
                            if(bitmap!=null)
                                iv.setImageBitmap(bitmap);
                        }
                    });
            layout.addView(img, llpimg);

            int padding=padding10*4;
            ImageView imgMask = new ImageView(act);
            imgMask.setBackgroundResource(R.color.light_trans_black);
            imgMask.setImageResource(R.mipmap.icon_video_play);
            imgMask.setPadding(padding, padding, padding, padding);
            layout.addView(imgMask, llpimg);
            return layout;
        }

        private LinearLayout getSubjectItemView(String imgUrl, int pos) {
            LinearLayout.LayoutParams llpimg = new LinearLayout.LayoutParams(imgHeight, imgHeight);
            llpimg.bottomMargin = padding10;
            LinearLayout layout = new LinearLayout(act);
            layout.setOrientation(LinearLayout.VERTICAL);
            layout.setPadding(padding10, padding10 * 2, padding10, 0);
            ImageView img = new ImageView(act);
            img.setBackgroundResource(R.color.whitesmoke);
            img.setScaleType(ImageView.ScaleType.CENTER_CROP);
            img.setOnClickListener(listener);
            img.setTag(pos);
            imageLoader.displayImage(ImageUtil.getImageUrl(imgUrl), img);
            layout.addView(img, llpimg);
            return layout;
        }

        View.OnClickListener listener=new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(act, ViewerActivity.class);
                ArrayList<String> images=new ArrayList<>();
                images.addAll(teacherBean.getPhoto_album());
                i.putStringArrayListExtra("Images", images);
                i.putExtra("pos", (int)view.getTag());
                ActUtil.startAnimActivity(act, i, view, "imgbig");
            }
        };

        View.OnClickListener videolistener=new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(act, VideoPlay.class);
                intent.putExtra("Url", ImageUtil.getImageUrl(teacherBean.getVideo().get((int)view.getTag())));
                act.startActivity(intent);

//                Intent intent = new Intent(Intent.ACTION_VIEW);
//                intent.setDataAndType(Uri.parse(ImageUtil.getImageUrl(teacherBean.getVideo().get((int)view.getTag()))), "video/mp4");
//                act.startActivity(intent);
            }
        };
    }


    ///////////////////////////////////////////////////////////////////////////////////page4holders


    public class TotalCommentsHolder extends RecyclerView.ViewHolder {

        RatingBar ratingbar;
        TextView totalcomments, averageScore;

        public TotalCommentsHolder(View v, int pos) {
            super(v);
            ratingbar= (RatingBar) v.findViewById(R.id.ratingbar);
            totalcomments = (TextView) v.findViewById(R.id.totalcomments);
            averageScore = (TextView) v.findViewById(R.id.averageScore);
        }
    }

    public class CommentsHolder extends RecyclerView.ViewHolder {
        ImageView potrait;
        TextView userName, userComments, commentDate, commentTime;
        RatingBar ratingbar;

        public CommentsHolder(View v, int pos) {
            super(v);
            potrait = (ImageView) v.findViewById(R.id.potrait);
            ratingbar= (RatingBar) v.findViewById(R.id.ratingbar);
            userName = (TextView) v.findViewById(R.id.userName);
            userComments = (TextView) v.findViewById(R.id.userComments);
            commentDate = (TextView) v.findViewById(R.id.commentDate);
            commentTime = (TextView) v.findViewById(R.id.commentTime);
        }
    }

    public class FooterViewHolder extends RecyclerView.ViewHolder {
        public FooterViewHolder(View v) {
            super(v);
        }
    }


    public interface EvaluateCallback{
        void requestData();
        void loadNext();
    }

}




