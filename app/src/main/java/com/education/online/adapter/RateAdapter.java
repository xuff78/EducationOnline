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
import com.education.online.bean.EvaluateBean;
import com.education.online.inter.AdapterCallback;
import com.education.online.util.ImageUtil;
import com.education.online.view.RatingBar;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/2.
 */
public class RateAdapter extends RecyclerView.Adapter <RecyclerView.ViewHolder>{

    private Activity activity;
    private LayoutInflater inflater;
    private int listType=0;
    private String average="0", total="0";
    private List<EvaluateBean> evaluations=new ArrayList<>();
    private ImageLoader imageLoader=ImageLoader.getInstance();
    private AdapterCallback cb;
    private int e1=0,e2=0,e3=0,e4=0,e5=0;

    public RateAdapter(Activity activity, List<EvaluateBean> evaluations, AdapterCallback cb){
        this.activity = activity;
        inflater = LayoutInflater.from(activity);
        this.evaluations=evaluations;
        this.cb=cb;
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
            vh.rateTxt.setText(average);
            vh.numTxt.setText("共"+total+"人评价");
            vh.ratingBar.setStar(Float.valueOf(average));
            vh.progress1.setProgress(e1);
            vh.rate5Txt.setText(e1+"%");
            vh.progress2.setProgress(e2);
            vh.rate4Txt.setText(e2+"%");
            vh.progress3.setProgress(e3);
            vh.rate3Txt.setText(e3+"%");
            vh.progress4.setProgress(e4);
            vh.rate2Txt.setText(e4+"%");
            vh.progress5.setProgress(e5);
            vh.rate1Txt.setText(e5+"%");
        }else if(pos==1){
            MenuHolder vh = (MenuHolder) holder;
            for (int i=0;i<vh.txts.size();i++){
                if(i==listType)
                    vh.txts.get(i).setTextColor(activity.getResources().getColor(R.color.normal_blue));
                else
                    vh.txts.get(i).setTextColor(activity.getResources().getColor(R.color.normal_gray));
            }
        }else {
            CommentsHolder vh = (CommentsHolder) holder;
            EvaluateBean evaluateBean = evaluations.get(pos-2);
            vh.ratingbar.setStar(Float.valueOf(evaluateBean.getStar()));
            vh.commentDate.setText(evaluateBean.getEvaluate_date());
            imageLoader.displayImage(ImageUtil.getImageUrl(evaluateBean.getAvatar()), vh.potrait);
            vh.userName.setText(evaluateBean.getUser_name());
            vh.userComments.setText(evaluateBean.getInfo());
        }
    }

    @Override
    public int getItemCount() {
        return 2+evaluations.size();
    }

    public void setOtherInfo(String average, int total, int e1, int e2, int e3, int e4, int e5) {
        this.average=average;
        this.total=total+"";
        this.e1=e1;
        this.e2=e2;
        this.e3=e3;
        this.e4=e4;
        this.e5=e5;
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
                    listType=tag;
                    if(cb!=null)
                        cb.onClick(view, tag);
                }
            }
        };
    }

    public class CommentsHolder extends RecyclerView.ViewHolder {
        ImageView potrait;
        RatingBar ratingbar;
        TextView userName, userComments, commentDate, commentTime, startfeedBack, replyTxt;

        public CommentsHolder(View v, final int pos) {
            super(v);
            ratingbar= (RatingBar) v.findViewById(R.id.ratingbar);
            potrait = (ImageView) v.findViewById(R.id.potrait);
            userName = (TextView) v.findViewById(R.id.userName);
            userComments = (TextView) v.findViewById(R.id.userComments);
            commentDate = (TextView) v.findViewById(R.id.commentDate);
            commentTime = (TextView) v.findViewById(R.id.commentTime);
            startfeedBack = (TextView) v.findViewById(R.id.startfeedBack);
            replyTxt = (TextView) v.findViewById(R.id.replyTxt);
            startfeedBack.setVisibility(View.VISIBLE);
            startfeedBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    cb.delitem(view, pos);
                }
            });
        }
    }
}
