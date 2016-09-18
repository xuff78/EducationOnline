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
import android.widget.ProgressBar;
import android.widget.TextView;

import com.education.online.R;
import com.education.online.bean.InterestingBean;
import com.education.online.util.ImageUtil;
import com.education.online.util.ScreenUtil;
import com.education.online.view.AutoFitLinearLayout;
import com.education.online.view.RatingBar;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/9/18.
 */
public class InterestingAdapter extends RecyclerView.Adapter <RecyclerView.ViewHolder>{

    private Activity activity;
    private LayoutInflater inflater;
    private int listType=0;
    private ArrayList<InterestingBean> interest=new ArrayList<>();
    private ArrayList<InterestingBean> tobeAdd=new ArrayList<>();
    private int itemWidth=0, itemHeight=0;
    private LinearLayout.LayoutParams llp;


    public InterestingAdapter(Activity activity){
        this.activity = activity;
        inflater = LayoutInflater.from(activity);
        int itemWidth = (ScreenUtil.getWidth(activity)- ImageUtil.dip2px(activity,90))/4;
        int itemHeight = ImageUtil.dip2px(activity,30);
        llp=new LinearLayout.LayoutParams(itemWidth, itemHeight);
        llp.rightMargin=ImageUtil.dip2px(activity, 15);

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
        View view = inflater.inflate(R.layout.interesting_item, null);
        vh = new SelectorHolder(view);
        return vh;
    }

    @Override
    //绑定数据源
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int pos) {
        if(pos==0){
            SelectorHolder vh = (SelectorHolder) holder;
            vh.interestingLayout.removeAllViews();
            vh.titleName.setText("我的兴趣");
            LinearLayout linelayout=new LinearLayout(activity);
            linelayout.setOrientation(LinearLayout.HORIZONTAL);
            for(int i=0;i<interest.size();i++){
                InterestingBean bean=interest.get(i);
                TextView txt=new TextView(activity);
                txt.setTextSize(13);
                txt.setGravity(Gravity.CENTER);
                txt.setTextColor(activity.getResources().getColor(R.color.normal_blue));
                txt.setBackgroundResource(R.drawable.shape_blueline_with_corner);
                txt.setText(bean.getName());
                txt.setTag(bean);
                txt.setOnClickListener(listener);
                linelayout.addView(txt, llp);
                if(i%4==3||i==interest.size()-1){
                    vh.interestingLayout.addView(linelayout);
                    linelayout=new LinearLayout(activity);
                    linelayout.setPadding(0, llp.rightMargin,0,0);
                }
            }
        }else if(pos>0){
            SelectorHolder vh = (SelectorHolder) holder;
            vh.interestingLayout.removeAllViews();
            vh.titleName.setText("科目");
            int size=6;
            LinearLayout linelayout=new LinearLayout(activity);
            linelayout.setOrientation(LinearLayout.HORIZONTAL);
            for(int i=0;i<size;i++){
                InterestingBean bean=new InterestingBean();
                bean.setName(pos+"测试"+i);
                TextView txt=new TextView(activity);
                txt.setTextSize(13);
                txt.setGravity(Gravity.CENTER);
                txt.setBackgroundResource(R.drawable.shape_corner_blackline);
                txt.setTextColor(Color.GRAY);
                txt.setText(bean.getName());
                txt.setTag(bean);
                txt.setOnClickListener(listener);
                linelayout.addView(txt, llp);
                if(i%4==3||i==size-1){
                    vh.interestingLayout.addView(linelayout);
                    linelayout=new LinearLayout(activity);
                    linelayout.setPadding(0, llp.rightMargin,0,0);
                }
            }
        }
    }

    View.OnClickListener listener=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            TextView txt= (TextView) view;
            InterestingBean bean= (InterestingBean) view.getTag();
            if(bean.isSelected()){
                bean.setSelected(false);
                tobeAdd.remove(bean);
                txt.setTextColor(activity.getResources().getColor(R.color.normal_gray));
                txt.setBackgroundResource(R.drawable.shape_corner_blackline);
            }else {
                bean.setSelected(true);
                tobeAdd.add(bean);
                txt.setTextColor(activity.getResources().getColor(R.color.normal_blue));
                txt.setBackgroundResource(R.drawable.shape_blueline_with_corner);
            }
        }
    };

    @Override
    public int getItemCount() {
        return 3;
    }

    public void addInterest() {
        if(tobeAdd.size()>0){
            interest.addAll(tobeAdd);
            for (InterestingBean bean:tobeAdd){
                bean.setSelected(false);
            }
            tobeAdd=new ArrayList<>();
            notifyDataSetChanged();
        }
    }


    public class SelectorHolder extends RecyclerView.ViewHolder {
        TextView titleName;
        LinearLayout interestingLayout;

        public SelectorHolder(View v) {
            super(v);
            interestingLayout= (LinearLayout) v.findViewById(R.id.interestingLayout);
            titleName = (TextView) v.findViewById(R.id.titleName);
        }
    }
}
