package com.education.online.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.education.online.R;
import com.education.online.act.CourseMainPage;
import com.education.online.act.VideoMainPage;
import com.education.online.act.video.LiveTelecast;
import com.education.online.act.video.VideoMain;
import com.education.online.util.ImageUtil;
import com.education.online.util.ScreenUtil;
import com.education.online.view.ExtendedViewPager;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

/**
 * Created by 可爱的蘑菇 on 2016/8/15.
 */
public class MainAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Activity act;
    private LayoutInflater listInflater;
    private ImageLoader imageLoader;
    private int itemWidth=0, itemHeight=0, imgHeight=0;
    private int padding10=0;

    public MainAdapter(Activity act, String json)
    {
        this.act=act;
        imageLoader=ImageLoader.getInstance();
        listInflater= LayoutInflater.from(act);
        padding10=ImageUtil.dip2px(act, 10);
        itemWidth= (ScreenUtil.getWidth(act)-2*padding10)/5; //小图片和文字的形成点击区域的边长
        imgHeight=itemWidth-2*padding10; //小图片的边长
    }

    @Override
    public int getItemCount() {
        // TODO Auto-generated method stub
        return 9;
    }

    @Override
    public int getItemViewType(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder vh, int pos) {
        if(pos==0) {
            PagerHolder ivh = (PagerHolder) vh;
        }else if(pos==1) {
            SubjectHolder ivh = (SubjectHolder) vh;
        }else if(pos==2) {
            CourseHolder ivh = (CourseHolder) vh;
            ivh.subjectName.setText("直播");
        }else if(pos==3) {
            CourseHolder ivh = (CourseHolder) vh;
            ivh.subjectName.setText("视频");
        }else if(pos==4) {
            CourseHolder ivh = (CourseHolder) vh;
            ivh.subjectName.setText("课件");
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup arg0, int pos) {
        RecyclerView.ViewHolder vh=null;
        if(pos==0) {
            View convertView = listInflater.inflate(R.layout.viewpager_layout, null);
            int height = (int) ((float) ScreenUtil.getWidth(act) / 640 * 240);
            RecyclerView.LayoutParams alp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
            convertView.setLayoutParams(alp);
            vh = new PagerHolder(convertView);
        }else if(pos==1) {
            LinearLayout view = new LinearLayout(act);
            view.setOrientation(LinearLayout.VERTICAL);
            view.setLayoutParams(new RecyclerView.LayoutParams(-1, -2));
            vh = new SubjectHolder(view, pos);
        }else if(pos==2||pos==3||pos==4) {
            View convertView = listInflater.inflate(R.layout.home_course_layout, null);
            vh = new CourseHolder(convertView, pos);
        }else if(pos==5) {
            View convertView = listInflater.inflate(R.layout.home_title_bar, null);
            convertView.setLayoutParams(new RecyclerView.LayoutParams(-1, -2));
            vh = new TitleHolder(convertView);
        }else if(pos>5) {
            View convertView = listInflater.inflate(R.layout.course_item_all, null);
            convertView.setLayoutParams(new RecyclerView.LayoutParams(-1, -2));
            vh = new CourseItemHolder(convertView);
        }
        return vh;
    }

    public class PagerHolder extends RecyclerView.ViewHolder
    {
        ExtendedViewPager mViewPager;

        public PagerHolder(View convertView) {
            super(convertView);
            mViewPager = (ExtendedViewPager) convertView.findViewById(R.id.galleryImgs);
            ArrayList<String> imgs=new ArrayList<>();
            imgs.add("");
            imgs.add("");
            imgs.add("");
            mViewPager.setAdapter(new ActivityTopGalleryAdapter(act, imgs));
            mViewPager.startAutoScroll();
        }
    }

    public class SubjectHolder extends RecyclerView.ViewHolder
    {
        LinearLayout itemsLayout;

        public SubjectHolder(View v, int position)
        {
            super(v);
            itemsLayout= (LinearLayout) v;
            itemsLayout.setPadding(padding10, 0, padding10, padding10*2);
            LinearLayout.LayoutParams llpitem=new LinearLayout.LayoutParams(itemWidth, -2);
            int itemsize=7;
            LinearLayout linelayout=new LinearLayout(act);
            linelayout.setOrientation(LinearLayout.HORIZONTAL);
            for(int i=0;i<itemsize+1;i++){
                if(i==itemsize)
                    linelayout.addView(getSubjectItemView("更多",""), llpitem);
                else
                    linelayout.addView(getSubjectItemView("科目",""), llpitem);
                if(i%5==4||i==itemsize){
                    itemsLayout.addView(linelayout);
                    linelayout=new LinearLayout(act);
                }
            }
        }

        private LinearLayout getSubjectItemView(String name, String imgUrl){
            LinearLayout.LayoutParams llpimg=new LinearLayout.LayoutParams(imgHeight, imgHeight);
            llpimg.bottomMargin=5;
            LinearLayout layout=new LinearLayout(act);
            layout.setOrientation(LinearLayout.VERTICAL);
            layout.setPadding(padding10,padding10*2,padding10,0);
            ImageView img=new ImageView(act);
            img.setBackgroundResource(R.color.whitesmoke);
            layout.addView(img, llpimg);
            TextView txt=new TextView(act);
            txt.setTextSize(12);
            txt.setTextColor(Color.GRAY);
            txt.setText(name);
            txt.setGravity(Gravity.CENTER_HORIZONTAL);
            layout.addView(txt);
            return layout;
        }
    }

    public class CourseHolder extends RecyclerView.ViewHolder
    {
        TextView subjectName, titleTxt, timeTxt, priceTxt, statusTxt, titleTxt2, timeTxt2, priceTxt2, statusTxt2;
        ImageView courseImg1, courseImg2;
        View item1,item2;
        View moreBtn;
        int pos=0;

        public CourseHolder(View convertView, int pos) {
            super(convertView);
            this.pos=pos;
            subjectName = (TextView) convertView.findViewById(R.id.subjectName);
            titleTxt = (TextView) convertView.findViewById(R.id.titleTxt);
            timeTxt = (TextView) convertView.findViewById(R.id.timeTxt);
            priceTxt = (TextView) convertView.findViewById(R.id.priceTxt);
            priceTxt = (TextView) convertView.findViewById(R.id.priceTxt);
            statusTxt = (TextView) convertView.findViewById(R.id.statusTxt);
            titleTxt2 = (TextView) convertView.findViewById(R.id.titleTxt2);
            timeTxt2 = (TextView) convertView.findViewById(R.id.timeTxt2);
            priceTxt2 = (TextView) convertView.findViewById(R.id.priceTxt2);
            statusTxt2 = (TextView) convertView.findViewById(R.id.statusTxt2);

            courseImg1 = (ImageView) convertView.findViewById(R.id.courseImg1);
            courseImg2 = (ImageView) convertView.findViewById(R.id.courseImg2);

            item1 = convertView.findViewById(R.id.item1);
            item2 = convertView.findViewById(R.id.item2);

            item1.setOnClickListener(listener);
            item2.setOnClickListener(listener);
            moreBtn = convertView.findViewById(R.id.mornBtn);
            moreBtn.setOnClickListener(listener);
        }

        View.OnClickListener listener=new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.mornBtn:
                        if(pos==2)
                            act.startActivity(new Intent(act, LiveTelecast.class));
                        else if(pos==3)
                            act.startActivity(new Intent(act, VideoMain.class));
                        else if(pos==4)
                            act.startActivity(new Intent(act, VideoMain.class));
                        break;
                    case R.id.item1:
                    case R.id.item2:
                        if(pos==2)
                            act.startActivity(new Intent(act, CourseMainPage.class));
                        else if(pos==3)
                            act.startActivity(new Intent(act, VideoMainPage.class));
                        else if(pos==4)
                            act.startActivity(new Intent(act, VideoMainPage.class));
                        break;
                }

            }
        };
    }

    public class TitleHolder extends RecyclerView.ViewHolder
    {
        TextView forYouTitle;

        public TitleHolder(View convertView) {
            super(convertView);
            forYouTitle = (TextView) convertView.findViewById(R.id.forYouTitle);
        }
    }

    public class CourseItemHolder extends RecyclerView.ViewHolder
    {
        TextView titleTxt,  statusTxt, typeTxt;
        ImageView courseImg;

        public CourseItemHolder(View convertView) {
            super(convertView);
            courseImg = (ImageView) convertView.findViewById(R.id.courseImg);
            titleTxt = (TextView) convertView.findViewById(R.id.titleTxt);
            statusTxt = (TextView) convertView.findViewById(R.id.statusTxt);
            typeTxt = (TextView) convertView.findViewById(R.id.typeTxt);
        }
    }
}
