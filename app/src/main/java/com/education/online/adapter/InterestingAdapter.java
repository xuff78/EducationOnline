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
import com.education.online.bean.SubjectBean;
import com.education.online.util.ImageUtil;
import com.education.online.util.ScreenUtil;
import com.education.online.view.AutoFitLinearLayout;
import com.education.online.view.RatingBar;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * Created by Administrator on 2016/9/18.
 */
public class InterestingAdapter extends RecyclerView.Adapter <RecyclerView.ViewHolder>{

    private Activity activity;
    private LayoutInflater inflater;
    private int listType=0;
    private LinkedHashMap<String, SubjectBean> interest=new LinkedHashMap<>();
    private ArrayList<SubjectBean> cates=new ArrayList<>();
    private int itemWidth=0, itemHeight=0;
    private LinearLayout.LayoutParams llp;

    public LinkedHashMap<String, SubjectBean> getInterest() {
        return interest;
    }

    public InterestingAdapter(Activity activity, ArrayList<SubjectBean> cates, ArrayList<SubjectBean> interestList){
        this.activity = activity;
        inflater = LayoutInflater.from(activity);
        this.cates=cates;
        for (SubjectBean item: interestList){
            interest.put(item.getSubject_id(), item);
        }
        for(int x=0;x<cates.size();x++) {
            SubjectBean subject=cates.get(x);
            for (int i = 0; i < subject.getChild_subject().size(); i++) {
                SubjectBean sublist = subject.getChild_subject().get(i);
                for (int j = 0; j < sublist.getChild_subject_details().size(); j++) {
                    SubjectBean detail=sublist.getChild_subject_details().get(j);
                    if(interest.containsKey(detail.getSubject_id())) {
                        detail.setSelected(true);
                        interest.put(detail.getSubject_id(), detail);
                    }
                }
            }
        }
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
            int itemNum=0;
            for(String key:interest.keySet()){
                SubjectBean bean=interest.get(key);
                TextView txt=new TextView(activity);
                txt.setTextSize(13);
                txt.setGravity(Gravity.CENTER);
                txt.setTextColor(activity.getResources().getColor(R.color.normal_blue));
                txt.setBackgroundResource(R.drawable.shape_blueline_with_corner);
                txt.setText(bean.getSubject_name());
                txt.setTag(bean);
                txt.setOnClickListener(listener);
                linelayout.addView(txt, llp);
                if(itemNum%4==3||itemNum==interest.size()-1){
                    vh.interestingLayout.addView(linelayout);
                    linelayout=new LinearLayout(activity);
                    linelayout.setPadding(0, llp.rightMargin,0,0);
                }
                itemNum++;
            }
        }else if(pos>0){
            SubjectBean subject=cates.get(pos-1);
            SelectorHolder vh = (SelectorHolder) holder;
            vh.interestingLayout.removeAllViews();
            vh.titleName.setText(subject.getSubject_name());
            LinearLayout linelayout=new LinearLayout(activity);
            linelayout.setOrientation(LinearLayout.HORIZONTAL);
            int itemNum=0;
            for(int i=0;i<subject.getChild_subject().size();i++){
                SubjectBean sublist=subject.getChild_subject().get(i);
                for (int j=0;j<sublist.getChild_subject_details().size();j++) {
                    SubjectBean detail=sublist.getChild_subject_details().get(j);
                    TextView txt = new TextView(activity);
                    txt.setTextSize(13);
                    txt.setGravity(Gravity.CENTER);
                    if(detail.isSelected()) {
                        txt.setTextColor(activity.getResources().getColor(R.color.normal_blue));
                        txt.setBackgroundResource(R.drawable.shape_blueline_with_corner);
                    }else{
                        txt.setTextColor(activity.getResources().getColor(R.color.normal_gray));
                        txt.setBackgroundResource(R.drawable.shape_corner_blackline);
                    }
                    txt.setText(detail.getSubject_name());
                    txt.setTag(detail);
                    txt.setOnClickListener(listener);
                    linelayout.addView(txt, llp);
                    if (itemNum % 4 == 3){
                        vh.interestingLayout.addView(linelayout);
                        linelayout = new LinearLayout(activity);
                        linelayout.setPadding(0, llp.rightMargin, 0, 0);
                    }
                    itemNum++;
                }
            }
            if (itemNum % 4 != 3)
                vh.interestingLayout.addView(linelayout);
        }
    }

    View.OnClickListener listener=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            TextView txt= (TextView) view;
            SubjectBean bean= (SubjectBean) view.getTag();
            if(bean.isSelected()){
                bean.setSelected(false);
                interest.remove(bean.getSubject_id());
                notifyDataSetChanged();
            }else {
                bean.setSelected(true);
                interest.put(bean.getSubject_id(), bean);
                notifyDataSetChanged();
            }
        }
    };

    @Override
    public int getItemCount() {
        return cates.size()+1;
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
