package com.education.online.adapter;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.education.online.R;
import com.education.online.util.ImageUtil;
import com.education.online.util.ScreenUtil;
import com.education.online.view.RatingBar;

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


    /*
    * itemType 1: MainHolder
    *
    *
    *
    * */

    public void setItemType(int itemType) {
        ItemType = itemType;
        notifyDataSetChanged();
    }

    public int getItemType() {
        return ItemType;
    }

    public TeacherMainAdapter(Activity act, String jason) {
        this.act = act;
        inflater = LayoutInflater.from(act);
        padding10 = ImageUtil.dip2px(act, 5);
        itemWidth = (ScreenUtil.getWidth(act) - 2 * padding10) / 4; //小图片和文字的形成点击区域的边长
        imgHeight = itemWidth - 2 * padding10; //小图片的边长
    }

    @Override
    public int getItemViewType(int position) {
        int i = getItemType();
        if (position == 0) {
            return 1;
        }else if(position ==1){
            if (i==1) {
                return 2;
            } else if(i==2) {
                return 3;
            }else if (i==3) {
            return 4;
            }else if (i==4){
                return 5;
            }
        } else if (position>1){
            if (i==2){
                return 3;
            }else if (i==4){
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
            case 1:
                view = inflater.inflate(R.layout.teacher_mainpart, null);//主要部分
                vh = new MainHolder(view);
                break;
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
        }
        return vh;
    }

    @Override
    //视图与数据的绑定，留待以后实现
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int pos) {
        int i = getItemType();
        if (pos == 0) {
            MainHolder vh = (MainHolder) holder;
        } else if (pos == 1 ){
            if(i==1){
                    BriefHolder vh =(BriefHolder) holder;
            }else if(i==2){
                CourseHolder vh = (CourseHolder) holder;
            }else if (i==3){
                AlbumHolder vh = (AlbumHolder) holder;
            }else if (i==4){
                TotalCommentsHolder vh = (TotalCommentsHolder) holder;
            }
        }else if (pos > 1){
            if (i==2) {
                CourseHolder vh = (CourseHolder) holder;
            }else if (i==4){
                CommentsHolder vh = (CommentsHolder ) holder;
            }
        }

    }

    @Override
    public int getItemCount() {
        int i = getItemType();
        switch (i) {
            case 1:
                return 2;
            case 2:
                return 5;
            case 3:
                return 2;
            case 4:
                return 7;
        }
        return 2;
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
        TextView courseName, coursePrice, totallength;


        public CourseHolder(View itemView) {
            super(itemView);
            teacherImage = (ImageView) itemView.findViewById(R.id.teacherImage);
            courseName = (TextView) itemView.findViewById(R.id.courseName);
            coursePrice = (TextView) itemView.findViewById(R.id.coursePrice);
            totallength = (TextView) itemView.findViewById(R.id.totallength);

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
            int itemsize = 15;
            LinearLayout linelayout = new LinearLayout(act);
            linelayout.setOrientation(LinearLayout.HORIZONTAL);
            for (int i = 0; i < itemsize + 1; i++) {
                linelayout.addView(getSubjectItemView(""), llpitem);
                if (i % 4 == 3 || i == itemsize) {
                    itemsLayout.addView(linelayout);
                    linelayout = new LinearLayout(act);

                }
            }
        }

        private LinearLayout getSubjectItemView(String imgUrl) {
            LinearLayout.LayoutParams llpimg = new LinearLayout.LayoutParams(imgHeight, imgHeight);
            llpimg.bottomMargin = 5;
            LinearLayout layout = new LinearLayout(act);
            layout.setOrientation(LinearLayout.VERTICAL);
            layout.setPadding(padding10, padding10 * 2, padding10, 0);
            ImageView img = new ImageView(act);
            img.setBackgroundResource(R.color.whitesmoke);
            layout.addView(img, llpimg);
            return layout;
        }
    }


    ///////////////////////////////////////////////////////////////////////////////////page4holders


    public class TotalCommentsHolder extends RecyclerView.ViewHolder {


        public TotalCommentsHolder(View v, int pos) {
            super(v);
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


}




