package com.education.online.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.education.online.R;
import com.education.online.act.CourseMainPage;
import com.education.online.act.SearchResultAct;
import com.education.online.act.VideoMainPage;
import com.education.online.act.video.LiveTelecast;
import com.education.online.act.video.VideoMain;
import com.education.online.bean.AdvertsBean;
import com.education.online.bean.CourseBean;
import com.education.online.bean.HomePageInfo;
import com.education.online.bean.LiveCourse;
import com.education.online.bean.SubjectBean;
import com.education.online.bean.TeacherBean;
import com.education.online.bean.VideoCourse;
import com.education.online.bean.WareCourse;
import com.education.online.util.ActUtil;
import com.education.online.util.Constant;
import com.education.online.util.ImageUtil;
import com.education.online.util.ScreenUtil;
import com.education.online.view.ExtendedViewPager;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 可爱的蘑菇 on 2016/8/15.
 */
public class AdvertAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Activity act;
    private LayoutInflater listInflater;
    private ImageLoader imageLoader;
    private int itemWidth = 0, itemHeight = 0, imgHeight = 0;
    private int padding10 = 0;
    private AdvertsBean info;
    private String loadingHint = "";
    private List<CourseBean> courseBeen;
    private List<TeacherBean> teacherBeen;
    private int num1, num2;
    private int currentpos = 0;

    public void setLoadingHint(String hint) {
        loadingHint = hint;
        notifyDataSetChanged();
    }

    public AdvertAdapter(Activity act, AdvertsBean info) {
        this.act = act;
        this.info = info;
        imageLoader = ImageLoader.getInstance();
        listInflater = LayoutInflater.from(act);
        padding10 = ImageUtil.dip2px(act, 10);
        itemWidth = (ScreenUtil.getWidth(act) - 2 * padding10) / 5; //小图片和文字的形成点击区域的边长
        imgHeight = itemWidth - 2 * padding10; //cour
        courseBeen = info.getCourseInfos();
        teacherBeen = info.getTeachetInfos();
        num1 = (courseBeen.size() % 2 == 0) ? (courseBeen.size() / 2) : (courseBeen.size() / 2 + 1);
        //  num2 = (teacherBeen.size() % 2 == 0) ? (teacherBeen.size() / 2) : (teacherBeen.size() / 2 + 1);

    }

    @Override
    public int getItemCount() {
        // TODO Auto-generated method stub
        return 3 + num1;
    }

    @Override
    public int getItemViewType(int position) {
        // TODO Auto-generated method stub
        return getItemCount() - 1 > position ? position : -1;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder vh, int pos) {
        if (vh instanceof FooterViewHolder) {
            FooterViewHolder fvh = (FooterViewHolder) vh;
            fvh.footerHint.setText(loadingHint);
            if ((num1 + num2) == 0) {
                fvh.footerHint.setText("暂时没有更多推荐");
            }
        } else if (pos == 0) {
            View convertView = listInflater.inflate(R.layout.viewpager_layout, null);
            int height = (int) ((float) ScreenUtil.getWidth(act) / 640 * 300);  //240
            RecyclerView.LayoutParams alp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
            convertView.setLayoutParams(alp);
            PagerHolder pagerHolder = (PagerHolder) vh;
            imageLoader.displayImage(ImageUtil.getImageUrl(info.getImg()), ((PagerHolder) vh).imageView);
        } else if (pos == 1) {
            TitleHolder titleHolder = (TitleHolder) vh;
        } else if (pos > 1&& pos < (3 + num1)) {
            CourseHolder ivh = (CourseHolder) vh;
            ivh.item2.setTag(pos);
            ivh.item1.setTag(pos);
            if (currentpos < courseBeen.size()) {
                CourseBean courseBean = courseBeen.get(currentpos);
                if (courseBean.getCourse_type().equals("3")) {
                    ivh.courseType1.setText("直播课");
                } else if (courseBean.getCourse_type().equals("2")) {
                    ivh.courseType1.setText("视频课");
                    ivh.imgMask1.setVisibility(View.VISIBLE);
                } else if (courseBean.getCourse_type().equals("1")) {
                    ivh.courseType1.setText("课件课");
                }
                if (courseBean.getImg().length() > 0)
                    imageLoader.displayImage(ImageUtil.getImageUrl(courseBean.getImg()), ivh.courseImg1);
                ivh.NumTxt1.setText(courseBean.getFollow() + "人报名");
                if (courseBean.getPrice().equals("0.00"))
                    ivh.priceTxt1.setText("免费");
                else ivh.priceTxt1.setText(ActUtil.getPrice(courseBean.getPrice()));
                ivh.timeTxt1.setText(courseBean.getPlan());
                ivh.titleTxt1.setText(courseBean.getCourse_name());
                ivh.timeTxt1.setText(courseBean.getCourseware_date());
                ivh.item1.setTag(currentpos);
                currentpos++;

            }

            if (currentpos < courseBeen.size()) {
                CourseBean courseBean = courseBeen.get(currentpos);
                if (courseBean.getCourse_type().equals("3")) {
                    ivh.courseType2.setText("直播课");
                } else if (courseBean.getCourse_type().equals("2")) {
                    ivh.courseType2.setText("视频课");
                    ivh.imgMask2.setVisibility(View.VISIBLE);
                } else if (courseBean.getCourse_type().equals("1")) {
                    ivh.courseType2.setText("课件课");
                }
                if (courseBean.getImg().length() > 0)
                    imageLoader.displayImage(ImageUtil.getImageUrl(courseBean.getImg()), ivh.courseImg2);
                ivh.NumTxt2.setText(courseBean.getFollow() + "人报名");
                if (courseBean.getPrice().equals("0.00"))
                    ivh.priceTxt2.setText("免费");
                else ivh.priceTxt2.setText(ActUtil.getPrice( courseBean.getPrice()));
                ivh.timeTxt2.setText(courseBean.getPlan());
                ivh.titleTxt2.setText(courseBean.getCourse_name());
                ivh.timeTxt2.setText(courseBean.getCourseware_date());
                ivh.item2.setTag(currentpos);
                currentpos++;
            } else {
                ivh.item2.setVisibility(View.INVISIBLE);
            }


        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup arg0, int pos) {
        RecyclerView.ViewHolder vh = null;
        if (pos == 0) {
            View convertView = listInflater.inflate(R.layout.imageview_layout, null);
            int height = (int) ((float) ScreenUtil.getWidth(act) / 640 * 300);  //240
            RecyclerView.LayoutParams alp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
            convertView.setLayoutParams(alp);
            vh = new PagerHolder(convertView);
        } else if (pos == 1) {
            View convertView = listInflater.inflate(R.layout.home_title_bar, null);
            convertView.setLayoutParams(new RecyclerView.LayoutParams(-1, -2));
            vh = new TitleHolder(convertView);
        } else if (pos > 1 && pos < (4 + num1)) {
            View convertView = listInflater.inflate(R.layout.advert_courseitem, null);
            vh = new CourseHolder(convertView, pos);
        } else if (pos == -1) {
            View view = listInflater.inflate(R.layout.footer_layout, null);
            view.setLayoutParams(new RecyclerView.LayoutParams(-1, ImageUtil.dip2px(act, 45)));
            vh = new FooterViewHolder(view);
        }
        return vh;
    }

    /***
     * public class PagerHolder extends RecyclerView.ViewHolder {
     * ExtendedViewPager mViewPager;
     * <p/>
     * public PagerHolder(View convertView) {
     * super(convertView);
     * mViewPager = (ExtendedViewPager) convertView.findViewById(R.id.galleryImgs);
     * if(teacherBeen.size()>0){
     * mViewPager.setAdapter(new TeacherTopGalleryAdapter(act, teacherBeen));
     * mViewPager.startAutoScroll();}else
     * mViewPager.setVisibility(View.GONE);
     * }
     * }
     */
    public class PagerHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public PagerHolder(View convertView) {
            super(convertView);
            imageView = (ImageView) convertView.findViewById(R.id.image);
        }
    }

    public class CourseHolder extends RecyclerView.ViewHolder {
        TextView courseType1, courseType2, titleTxt1, titleTxt2, timeTxt1, timeTxt2, NumTxt1, NumTxt2, priceTxt1, priceTxt2;
        ImageView courseImg1, courseImg2, imgMask1, imgMask2;
        View item1, item2;
        View moreBtn;
        int pos = 0;

        public CourseHolder(View convertView, int pos) {
            super(convertView);
            this.pos = pos;

            courseType1 = (TextView) convertView.findViewById(R.id.courseType1);
            courseType2 = (TextView) convertView.findViewById(R.id.courseType2);
            titleTxt1 = (TextView) convertView.findViewById(R.id.titleTxt1);
            titleTxt2 = (TextView) convertView.findViewById(R.id.titleTxt2);
            timeTxt1 = (TextView) convertView.findViewById(R.id.timeTxt1);
            timeTxt2 = (TextView) convertView.findViewById(R.id.timeTxt2);
            NumTxt1 = (TextView) convertView.findViewById(R.id.NumTxt1);
            NumTxt2 = (TextView) convertView.findViewById(R.id.NumTxt2);
            priceTxt1 = (TextView) convertView.findViewById(R.id.priceTxt1);
            priceTxt2 = (TextView) convertView.findViewById(R.id.priceTxt2);
            courseImg1 = (ImageView) convertView.findViewById(R.id.courseImg1);
            courseImg2 = (ImageView) convertView.findViewById(R.id.courseImg2);
            imgMask1 = (ImageView) convertView.findViewById(R.id.imgMask1);
            imgMask2 = (ImageView) convertView.findViewById(R.id.imgMask2);
            item1 = convertView.findViewById(R.id.item1);
            item2 = convertView.findViewById(R.id.item2);
            item1.setOnClickListener(listener);
            item2.setOnClickListener(listener);
        }

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int i = (int) view.getTag();
                Intent intent= new Intent();
                switch (view.getId()) {
                    case R.id.item1:
                    case R.id.item2:
                        CourseBean courseBean = courseBeen.get(i);
                        intent.putExtra("course_id",courseBean.getCourse_id());
                        if(courseBean.getCourse_type().equals("3"))
                            intent.setClass(act,CourseMainPage.class);
                        else intent.setClass(act,VideoMainPage.class);
                        act.startActivity(intent);
                        break;
                }
            }
        };
    }

    ;

    public class TitleHolder extends RecyclerView.ViewHolder {
        TextView forYouTitle;
        View hintview;

        public TitleHolder(View convertView) {
            super(convertView);
            hintview = convertView.findViewById(R.id.hintview);
            hintview.setVisibility(View.GONE);
            forYouTitle = (TextView) convertView.findViewById(R.id.forYouTitle);
            forYouTitle.setText("你要的好课，都在这里");
            forYouTitle.setTextSize(14);
           // forYouTitle.setPadding(0,10,0,10);
            forYouTitle.setTextColor(act.getResources().getColor(R.color.hard_gray));
        }
    }

}
