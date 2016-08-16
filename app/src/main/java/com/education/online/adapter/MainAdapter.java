package com.education.online.adapter;

import android.app.Activity;
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
import com.education.online.bean.CategoryBean;
import com.education.online.util.ImageUtil;
import com.education.online.util.ScreenUtil;
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
    private int padding=0;

    public MainAdapter(Activity act, String json)
    {
        this.act=act;
        imageLoader=ImageLoader.getInstance();
        listInflater= LayoutInflater.from(act);
        itemWidth= (ScreenUtil.getWidth(act))/5;
        imgHeight=ImageUtil.dip2px(act, 40);
        padding=ImageUtil.dip2px(act, 10);
    }

    @Override
    public int getItemCount() {
        // TODO Auto-generated method stub
        return 1;
    }

    @Override
    public int getItemViewType(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder vh, int pos) {
        SubjectHolder ivh=(SubjectHolder) vh;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup arg0, int pos) {
        RecyclerView.ViewHolder vh=null;
        LinearLayout view=new LinearLayout(act);
        view.setOrientation(LinearLayout.VERTICAL);
        view.setLayoutParams(new RecyclerView.LayoutParams(-1, -2));
        vh=new SubjectHolder(view, pos);
        return vh;
    }

    public class SubjectHolder extends RecyclerView.ViewHolder
    {
        LinearLayout itemsLayout;

        public SubjectHolder(View v, int position)
        {
            super(v);
            itemsLayout= (LinearLayout) v;
            LinearLayout.LayoutParams llpitem=new LinearLayout.LayoutParams(itemWidth, itemWidth);
            LinearLayout.LayoutParams llpimg=new LinearLayout.LayoutParams(itemWidth, itemWidth);
            llpimg.bottomMargin=5;
            int size=9;
            LinearLayout linelayout=new LinearLayout(act);
            linelayout.setOrientation(LinearLayout.HORIZONTAL);
            for(int i=0;i<size;i++){
                LinearLayout layout=new LinearLayout(act);
                layout.setOrientation(LinearLayout.VERTICAL);
                layout.setPadding(padding,padding*2,padding,0);
                layout.setGravity(Gravity.CENTER);
                ImageView img=new ImageView(act);
                img.setBackgroundResource(R.color.whitesmoke);
                layout.addView(img, llpimg);
                TextView txt=new TextView(act);
                txt.setTextSize(12);
                txt.setTextColor(Color.GRAY);
                txt.setText("科目");
                layout.addView(txt);
                linelayout.addView(layout, llpitem);
                if(i%5==4||i==size-1){
                    itemsLayout.addView(linelayout);
                    linelayout=new LinearLayout(act);
                }
            }
        }
    }

}
