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
import com.education.online.bean.EvaluateBean;
import com.education.online.util.ImageUtil;
import com.education.online.view.RatingBar;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 可爱的蘑菇 on 2016/9/16.
 */
public class RateListAdapter extends RecyclerView.Adapter <RecyclerView.ViewHolder>{

    private Activity activity;
    private LayoutInflater inflater;
    private int listType=0;
    private List<EvaluateBean> evaluations=new ArrayList<>();
    private ImageLoader imageLoader=ImageLoader.getInstance();

    public RateListAdapter(Activity activity, List<EvaluateBean> evaluations){
        this.activity = activity;
        inflater = LayoutInflater.from(activity);
        this.evaluations=evaluations;
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
        EvaluateBean evaluateBean = evaluations.get(pos);
        vh.ratingbar.setStar(Float.valueOf(evaluateBean.getStar()));
        vh.commentDate.setText(evaluateBean.getEvaluate_date());
        imageLoader.displayImage(ImageUtil.getImageUrl(evaluateBean.getAvatar()), vh.potrait);
        vh.userName.setText(evaluateBean.getUser_name());
        vh.userComments.setText(evaluateBean.getInfo());
    }

    @Override
    public int getItemCount() {
        return evaluations.size();
    }

    public class CommentsHolder extends RecyclerView.ViewHolder {
        ImageView potrait;
        RatingBar ratingbar;
        TextView userName, userComments, commentDate, commentTime, replyTxt;

        public CommentsHolder(View v, int pos) {
            super(v);
            ratingbar= (RatingBar) v.findViewById(R.id.ratingbar);
            potrait = (ImageView) v.findViewById(R.id.potrait);
            userName = (TextView) v.findViewById(R.id.userName);
            userComments = (TextView) v.findViewById(R.id.userComments);
            commentDate = (TextView) v.findViewById(R.id.commentDate);
            commentTime = (TextView) v.findViewById(R.id.commentTime);
            replyTxt = (TextView) v.findViewById(R.id.replyTxt);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    activity.startActivity(new Intent(activity, EvaluationEdit.class));
                }
            });
        }
    }
}
