package com.education.online.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.education.online.R;
import com.education.online.bean.CourseDetailBean;
import com.education.online.bean.EvaluateBean;
import com.education.online.bean.EvaluateListBean;
import com.education.online.util.ActUtil;
import com.education.online.util.ImageUtil;
import com.education.online.view.RatingBar;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Gaozhiqun on 2016/8/25.
 */

public class CommentsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Activity act;
    private LayoutInflater listInflater;
    private CourseDetailBean courseDetailBean;
    private List<EvaluateBean> evaluateList = new ArrayList<>();
    private ImageLoader imageLoader;
    private String loadingHint = "";


    public CommentsAdapter(Activity act, CourseDetailBean courseDetailBean, List<EvaluateBean> evaluatelList) {
        this.act = act;
        listInflater = LayoutInflater.from(act);
        this.courseDetailBean = courseDetailBean;
        this.evaluateList = evaluatelList;
        imageLoader=imageLoader.getInstance();
    }

    public void setLoadingHint(String hint) {
        loadingHint = hint;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        // TODO Auto-generated method stub
        return getItemCount()-1>position?position:-1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup arg0, int pos) {
        RecyclerView.ViewHolder vh = null;
    if (pos == 0) {
            View view = listInflater.inflate(R.layout.totalcomments_layout, null);
            view.setLayoutParams(new RecyclerView.LayoutParams(-1, -2));
            vh = new TotalCommentsHolder(view, pos);
        } else if(pos>0){
        if(pos!=-1) {
            View view = listInflater.inflate(R.layout.comments_fragment, null);
            view.setLayoutParams(new RecyclerView.LayoutParams(-1, -2));
            vh = new CommentsHolder(view, pos);
        }
        } else if(pos==-1){
        View view = listInflater.inflate(R.layout.footer_layout, null);
        view.setLayoutParams(new RecyclerView.LayoutParams(-1, ImageUtil.dip2px(act, 45)));
        vh = new FooterViewHolder(view);
    }
        return vh;
    }

    @Override
    //视图与数据的绑定，留待以后实现
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int pos) {
        if (holder instanceof FooterViewHolder) {
            FooterViewHolder fvh = (FooterViewHolder) holder;
            fvh.footerHint.setText(loadingHint);
            if(evaluateList.size()==0)
                fvh.footerHint.setText("暂无评价");
        } else
        if (pos == 0) {
            TotalCommentsHolder vh = (TotalCommentsHolder) holder;
            vh.ratingBar.setStar(Float.parseFloat(courseDetailBean.getAverage()));
            vh.averageScore.setText(courseDetailBean.getAverage() + "分");
            if(courseDetailBean.getCourse_evaluate().getTotal().length()==0)
                courseDetailBean.getCourse_evaluate().setTotal("0");
            vh.totalcomments.setText("共" + courseDetailBean.getCourse_evaluate().getTotal() + "条评论");
        }
        else if(pos >0){
            CommentsHolder vh = (CommentsHolder) holder;
            if(evaluateList.size()>0){
            EvaluateBean evaluateBean = evaluateList.get(pos - 1);
            vh.userName.setText(evaluateBean.getUser_name());
            vh.userComments.setText(evaluateBean.getInfo());
            vh.commentDate.setText(ActUtil.getDataTime(evaluateBean.getEvaluate_date()));
            vh.ratingBar.setStar(Float.parseFloat(evaluateBean.getStar()));
            imageLoader.displayImage(ImageUtil.getImageUrl(evaluateBean.getAvatar()), vh.potrait);}
        }

    }

    @Override
    public int getItemCount() {
       return evaluateList.size() + 2;


    }


    public class TotalCommentsHolder extends RecyclerView.ViewHolder {
        RatingBar ratingBar;
        TextView totalcomments, averageScore;


        public TotalCommentsHolder(View v, int pos) {
            super(v);
            ratingBar = (RatingBar) v.findViewById(R.id.ratingbar);
            averageScore = (TextView) v.findViewById(R.id.averageScore);
            totalcomments = (TextView) v.findViewById(R.id.totalcomments);
            ratingBar.setmClickable(false);


        }
    }

    public class CommentsHolder extends RecyclerView.ViewHolder {
        ImageView potrait;
        TextView userName, userComments, commentDate, commentTime;
        RatingBar ratingBar;

        public CommentsHolder(View v, int pos) {
            super(v);
            potrait = (ImageView) v.findViewById(R.id.potrait);
            ratingBar = (RatingBar) v.findViewById(R.id.ratingbar);
            userName = (TextView) v.findViewById(R.id.userName);
            userComments = (TextView) v.findViewById(R.id.userComments);
            commentDate = (TextView) v.findViewById(R.id.commentDate);
            commentTime = (TextView) v.findViewById(R.id.commentTime);
            ratingBar.setmClickable(false);
        }
    }


}




