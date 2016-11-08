package com.education.online.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.education.online.R;
import com.education.online.bean.EvaluateBean;
import com.education.online.bean.EvaluateListBean;
import com.education.online.util.ImageUtil;
import com.education.online.view.RatingBar;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Gaozhiqun on 2016/8/25.
 */

public class CommentsAdapter extends RecyclerView.Adapter <RecyclerView.ViewHolder>{

    private Activity act;
    private LayoutInflater listInflater;
    private EvaluateListBean evaluateListBean;
    private ImageLoader imageLoader;;


    public CommentsAdapter(Activity act, EvaluateListBean evaluateListBean) {
        this.act=act;
        listInflater= LayoutInflater.from(act);
        this.evaluateListBean = evaluateListBean;
    }

    @Override
    public int getItemViewType(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup arg0, int pos) {
        RecyclerView.ViewHolder vh=null;
        if(pos==0){
            View view=listInflater.inflate(R.layout.totalcomments_layout, null);
            view.setLayoutParams(new RecyclerView.LayoutParams(-1, -2));
            vh = new TotalCommentsHolder(view, pos);
        }else{
            View view=listInflater.inflate(R.layout.comments_fragment, null);
            view.setLayoutParams(new RecyclerView.LayoutParams(-1, -2));
            vh = new CommentsHolder(view, pos);
        }


        return vh;
    }

    @Override
    //视图与数据的绑定，留待以后实现
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int pos) {
        if (pos==0) {
            TotalCommentsHolder vh = (TotalCommentsHolder) holder;
            vh.ratingBar.setStar(Float.parseFloat(evaluateListBean.getAverage()));
            vh.averageScore.setText(evaluateListBean.getAverage()+"分");
            vh.totalcomments.setText("共"+evaluateListBean.getTotal()+"条评论");
        }
        else{
            CommentsHolder vh = (CommentsHolder) holder;
            for (int i = 0; i < evaluateListBean.getEvaluateList().size(); i++) {
                vh.userName.setText(evaluateListBean.getEvaluateList().get(i).getUser_name());
                DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat formatter1 = new SimpleDateFormat("HH:mm");
                Date date = new Date();
                try {
                    date = format1.parse(evaluateListBean.getEvaluateList().get((i)).getEvaluate_date());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                vh.commentTime.setText(formatter1.format(date));
                vh.commentDate.setText(formatter.format(date));
                vh.ratingBar.setStar(Float.parseFloat(evaluateListBean.getEvaluateList().get(i).getStar()));
                imageLoader.displayImage(ImageUtil.getImageUrl(evaluateListBean.getEvaluateList().get(i).getAvatar()), vh.potrait);
            }
        }
    }

    @Override
    public int getItemCount() {
        return evaluateListBean.getEvaluateList().size()+1;
    }


    public class TotalCommentsHolder extends RecyclerView.ViewHolder{
        RatingBar ratingBar;
        TextView totalcomments,averageScore;




        public TotalCommentsHolder(View v, int pos) {
            super(v);
            ratingBar = (RatingBar) v.findViewById(R.id.ratingbar);
            averageScore = (TextView) v.findViewById(R.id.averageScore);
            totalcomments = (TextView) v.findViewById(R.id.totalcomments);
            ratingBar.setmClickable(false);


        }
    }

    public class CommentsHolder extends RecyclerView.ViewHolder{
        ImageView potrait;
        TextView userName, userComments,commentDate,commentTime;
        RatingBar ratingBar;

        public CommentsHolder(View v, int pos) {
            super(v);
            potrait = (ImageView) v. findViewById(R.id.potrait);
            ratingBar= (RatingBar) v.findViewById(R.id.ratingbar);
            userName= (TextView) v.findViewById(R.id.userName);
            userComments= (TextView) v.findViewById(R.id.userComments);
            commentDate= (TextView) v.findViewById(R.id.commentDate);
            commentTime= (TextView) v.findViewById(R.id.commentTime);
            ratingBar.setmClickable(false);
        }
    }


}




