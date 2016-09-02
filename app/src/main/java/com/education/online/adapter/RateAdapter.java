package com.education.online.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.education.online.R;
import com.education.online.bean.OnlineCourseBean;
import com.education.online.view.RatingBar;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/9/2.
 */
public class RateAdapter extends RecyclerView.Adapter <RecyclerView.ViewHolder>{

    private Activity activity;
    private LayoutInflater inflater;
    private int listType=0;

    public RateAdapter(Activity activity){
        this.activity = activity;
        inflater = LayoutInflater.from(activity);


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
        if(pos==0){
            View view = inflater.inflate(R.layout.rate_top_item, null);
            vh = new TopRateHolder(view);
        }else if(pos==1){
            View view = inflater.inflate(R.layout.rate_top_menu, null);
            vh = new MenuHolder(view);
        }else {
            View view = inflater.inflate(R.layout.comments_fragment, null);
            vh = new CommentsHolder(view, pos);
        }
        return vh;
    }

    @Override
    //绑定数据源
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int pos) {
        if(pos==0){
            TopRateHolder vh = (TopRateHolder) holder;
        }else if(pos==1){
            MenuHolder vh = (MenuHolder) holder;
        }else {
            CommentsHolder vh = (CommentsHolder) holder;
        }
    }

    @Override
    public int getItemCount() {
        return 6;
    }

    public class TopRateHolder extends RecyclerView.ViewHolder {

        RatingBar ratingBar;
        TextView rateTxt, numTxt, rate5Txt, rate4Txt, rate3Txt, rate2Txt, rate1Txt;
        ProgressBar progress1, progress2, progress3, progress4, progress5;

        public TopRateHolder(View v) {
            super(v);
            rateTxt = (TextView) v.findViewById(R.id.rateTxt);
            numTxt = (TextView) v.findViewById(R.id.numTxt);
            rate5Txt = (TextView) v.findViewById(R.id.rate5Txt);
            rate4Txt = (TextView) v.findViewById(R.id.rate4Txt);
            rate3Txt = (TextView) v.findViewById(R.id.rate3Txt);
            rate2Txt = (TextView) v.findViewById(R.id.rate2Txt);
            rate1Txt = (TextView) v.findViewById(R.id.rate1Txt);
            ratingBar= (RatingBar) v.findViewById(R.id.ratingbar);
            progress1= (ProgressBar) v.findViewById(R.id.progress1);
            progress2= (ProgressBar) v.findViewById(R.id.progress2);
            progress3= (ProgressBar) v.findViewById(R.id.progress3);
            progress4= (ProgressBar) v.findViewById(R.id.progress4);
            progress5= (ProgressBar) v.findViewById(R.id.progress5);
        }
    }

    public class MenuHolder extends RecyclerView.ViewHolder {

        private ArrayList<TextView> txts=new ArrayList<>();

        public MenuHolder(View v) {
            super(v);
            txts.add((TextView)v.findViewById(R.id.rateMenu1));
            txts.add((TextView) v.findViewById(R.id.rateMenu2));
            txts.add((TextView) v.findViewById(R.id.rateMenu3));
            txts.add((TextView) v.findViewById(R.id.rateMenu4));
            txts.add((TextView) v.findViewById(R.id.rateMenu5));
            txts.add((TextView) v.findViewById(R.id.rateMenu6));
            for (int i=0;i<txts.size();i++){
                txts.get(i).setOnClickListener(listener);
                txts.get(i).setTag(i);
            }
        }

        View.OnClickListener listener=new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                int tag= (int) view.getTag();
                if(tag!=listType){
                    txts.get(tag).setTextColor(activity.getResources().getColor(R.color.normal_blue));
                    txts.get(listType).setTextColor(activity.getResources().getColor(R.color.normal_gray));
                    listType=tag;
                }
            }
        };
    }

    public class CommentsHolder extends RecyclerView.ViewHolder {
        ImageView potrait;
        RatingBar ratingbar;
        TextView userName, userComments, commentDate, commentTime;

        public CommentsHolder(View v, int pos) {
            super(v);
            ratingbar= (RatingBar) v.findViewById(R.id.ratingbar);
            potrait = (ImageView) v.findViewById(R.id.potrait);
            userName = (TextView) v.findViewById(R.id.userName);
            userComments = (TextView) v.findViewById(R.id.userComments);
            commentDate = (TextView) v.findViewById(R.id.commentDate);
            commentTime = (TextView) v.findViewById(R.id.commentTime);
        }
    }
}
