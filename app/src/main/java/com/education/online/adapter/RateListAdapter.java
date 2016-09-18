package com.education.online.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.education.online.R;
import com.education.online.act.Mine.EvaluationEdit;
import com.education.online.view.RatingBar;

import java.util.ArrayList;

/**
 * Created by 可爱的蘑菇 on 2016/9/16.
 */
public class RateListAdapter extends RecyclerView.Adapter <RecyclerView.ViewHolder>{

    private Activity activity;
    private LayoutInflater inflater;
    private int listType=0;

    public RateListAdapter(Activity activity){
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
        View view = inflater.inflate(R.layout.comments_fragment, null);
        vh = new CommentsHolder(view, pos);
        return vh;
    }

    @Override
    //绑定数据源
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int pos) {

        CommentsHolder vh = (CommentsHolder) holder;
    }

    @Override
    public int getItemCount() {
        return 6;
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
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    activity.startActivity(new Intent(activity, EvaluationEdit.class));
                }
            });
        }
    }
}
