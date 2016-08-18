package com.education.online.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.education.online.R;
import com.education.online.util.ImageUtil;
import com.education.online.util.ScreenUtil;
import com.education.online.view.ExtendedViewPager;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

/**
 * Created by 可爱的蘑菇 on 2016/8/18.
 */
public class VideoMainAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Activity act;
    private LayoutInflater listInflater;
    private ImageLoader imageLoader;
    private int itemWidth=0, itemHeight=0, imgHeight=0;
    private int padding10=0;

    public VideoMainAdapter(Activity act, String json)
    {
        this.act=act;
        imageLoader=ImageLoader.getInstance();
        listInflater= LayoutInflater.from(act);
        padding10= ImageUtil.dip2px(act, 10);
        itemWidth= ScreenUtil.getWidth(act)/6; //小图片和文字的形成点击区域的边长
        imgHeight=itemWidth-3*padding10; //小图片的边长
    }

    @Override
    public int getItemCount() {
        // TODO Auto-generated method stub
        return 6;
    }

    @Override
    public int getItemViewType(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder vh, int pos) {
        if(pos==0) {
            SubjectHolder ivh = (SubjectHolder) vh;
        }else if(pos==1) {
            HoriListHolder ivh=(HoriListHolder) vh;
            ivh.horilist.setAdapter(new HoriListAdapter(act));
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup arg0, int pos) {
        RecyclerView.ViewHolder vh=null;
        if(pos==0) {
            HorizontalScrollView scrollView=new HorizontalScrollView(act);
            scrollView.setHorizontalScrollBarEnabled(false);
            scrollView.setLayoutParams(new RecyclerView.LayoutParams(-1, -2));
            vh = new SubjectHolder(scrollView);
        }else if(pos==1) {
            View convertView = listInflater.inflate(R.layout.hori_video_bar, null);
            vh = new HoriListHolder(convertView);
        }else if(pos==2) {
            View convertView = listInflater.inflate(R.layout.home_title_bar, null);
            convertView.setLayoutParams(new RecyclerView.LayoutParams(-1, -2));
            vh = new TitleHolder(convertView);
        }else if(pos>3) {
            View convertView = listInflater.inflate(R.layout.course_item_all, null);
            convertView.setLayoutParams(new RecyclerView.LayoutParams(-1, -2));
            vh = new CourseItemHolder(convertView);
        }
        return vh;
    }

    public class SubjectHolder extends RecyclerView.ViewHolder
    {

        public SubjectHolder(View v)
        {
            super(v);
            LinearLayout itemsLayout = new LinearLayout(act);
            itemsLayout.setOrientation(LinearLayout.HORIZONTAL);
            ((HorizontalScrollView)v).addView(itemsLayout);
            LinearLayout.LayoutParams llpitem=new LinearLayout.LayoutParams(itemWidth, -2);
            int itemsize=7;
            for(int i=0;i<itemsize+1;i++){
                itemsLayout.addView(getSubjectItemView("科目",""), llpitem);
            }
        }

        private LinearLayout getSubjectItemView(String name, String imgUrl){
            LinearLayout.LayoutParams llpimg=new LinearLayout.LayoutParams(imgHeight, imgHeight);
            llpimg.bottomMargin=5;
            LinearLayout layout=new LinearLayout(act);
            layout.setOrientation(LinearLayout.VERTICAL);
            layout.setPadding(padding10,padding10*2,padding10,padding10*2);
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

    public class HoriListHolder extends RecyclerView.ViewHolder
    {
        RecyclerView horilist;
        TextView subjectName;
        public HoriListHolder(View convertView) {
            super(convertView);
            subjectName= (TextView) convertView.findViewById(R.id.subjectName);
            horilist=(RecyclerView)convertView.findViewById(R.id.horiRecyclerView);
            horilist.setHasFixedSize(true);
            LinearLayoutManager mLayoutManager = new LinearLayoutManager(act);
            mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            horilist.setLayoutManager(mLayoutManager);
        }
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
