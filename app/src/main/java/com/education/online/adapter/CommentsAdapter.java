package com.education.online.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.education.online.R;

/**
 * Created by Gaozhiqun on 2016/8/25.
 */

public class CommentsAdapter extends RecyclerView.Adapter <RecyclerView.ViewHolder>{

    private Activity act;
    private LayoutInflater listInflater;


    public CommentsAdapter(Activity act, String jason) {
        this.act=act;
        listInflater= LayoutInflater.from(act);
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
        }
        else{
            CommentsHolder vh = (CommentsHolder) holder;
        }
    }

    @Override
    public int getItemCount() {
        return 5;
    }


    public class TotalCommentsHolder extends RecyclerView.ViewHolder{


        public TotalCommentsHolder(View v, int pos) {
            super(v);
        }
    }

    public class CommentsHolder extends RecyclerView.ViewHolder{
        ImageView potrait,star1,star2,star3,star4,star5;
        TextView userName, userComments,commentDate,commentTime;

        public CommentsHolder(View v, int pos) {
            super(v);
            potrait = (ImageView) v. findViewById(R.id.potrait);
            star1 = (ImageView) v.findViewById(R.id.star1);
            star2 = (ImageView) v.findViewById(R.id.star2);
            star3 = (ImageView) v.findViewById(R.id.star3);
            star4 = (ImageView) v.findViewById(R.id.star4);
            star5 = (ImageView) v.findViewById(R.id.star5);
            userName= (TextView) v.findViewById(R.id.userName);
            userComments= (TextView) v.findViewById(R.id.userComments);
            commentDate= (TextView) v.findViewById(R.id.commentDate);
            commentTime= (TextView) v.findViewById(R.id.commentTime);
        }
    }


}




