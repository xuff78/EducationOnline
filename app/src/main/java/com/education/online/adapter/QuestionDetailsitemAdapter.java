package com.education.online.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.education.online.R;
import com.education.online.bean.AnswerInfoBean;
import com.education.online.bean.AnswerListHolder;
import com.education.online.bean.QuestionInfoBean;
import com.education.online.util.ImageUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/29.
 */
public class QuestionDetailsitemAdapter extends RecyclerView.Adapter {
    private Activity act;
    private ImageLoader imageLoader;
    private LayoutInflater listInflater;
    private String loadingHint = "";
    QuestionInfoBean questionInfoBean = new QuestionInfoBean();
    private AnswerListHolder answerListHolder = new AnswerListHolder();
    private List<AnswerInfoBean> answerInfoBeen = new ArrayList<>();

    public QuestionDetailsitemAdapter(Activity act, QuestionInfoBean questionInfoBean, AnswerListHolder answerListHolder, List<AnswerInfoBean> answerInfoBeen) {
        this.act = act;
        this.listInflater = LayoutInflater.from(act);
        imageLoader = ImageLoader.getInstance();
        this.questionInfoBean = questionInfoBean;
        this.answerInfoBeen = answerInfoBeen;
        this.answerListHolder = answerListHolder;
    }

    public void setLoadingHint(String hint) {
        loadingHint = hint;
        notifyDataSetChanged();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int pos) {
        RecyclerView.ViewHolder vh = null;
        if (pos == 0){
            View view = listInflater.inflate(R.layout.myquestiondetailsfirstitem, null);
            vh = new QuestionDetailsFirstitemHolder(view);
        } else if (pos > 0){
            View view = listInflater.inflate(R.layout.myquestiondetailsitems, null);
            vh = new QuestionAnswerHolder(view);
        } else {
            View view = listInflater.inflate(R.layout.footer_layout, null);
            view.setLayoutParams(new RecyclerView.LayoutParams(-1, ImageUtil.dip2px(act, 45)));
            vh = new FooterViewHolder(view);
        }
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof FooterViewHolder) {
            FooterViewHolder fvh = (FooterViewHolder) holder;
            fvh.footerHint.setText(loadingHint);
            if (getItemCount() < 3)
                fvh.footerHint.setText("暂无回答");
        } else if (position == 0) {

            QuestionDetailsFirstitemHolder vh = (QuestionDetailsFirstitemHolder) holder;
            vh.questionIntroduction.setText(questionInfoBean.getIntroduction());
            vh.design.setText(questionInfoBean.getSubject_name());
            vh.time.setText(questionInfoBean.getCreated_at());
            if (questionInfoBean.getImg().length() > 0)
                imageLoader.displayImage(ImageUtil.getImageUrl(questionInfoBean.getImg()), vh.coursepicture);
            if (questionInfoBean.getAvatar().length() > 0)
                imageLoader.displayImage(ImageUtil.getImageUrl(questionInfoBean.getAvatar()), vh.headIcon);
        } else {
            QuestionAnswerHolder vh = (QuestionAnswerHolder) holder;
            AnswerInfoBean answerInfoBean = answerInfoBeen.get(position-1);
            vh.answer.setText(answerInfoBean.getIntroduction());
            vh.answerwhatquesttion.setText(answerInfoBean.getIntroduction());
            if(answerInfoBean.getIs_correct()=="1")
                vh.isadopted.setVisibility(View.VISIBLE);
            String temp;
            if(answerInfoBean.getUser_identity()=="2") {
                temp = "老师";
            } else {
                temp = "学生";
            }
            vh.name.setText(answerInfoBean.getUser_name()+"("+temp+")");
            if(answerInfoBean.getAvatar().length()>0)
                imageLoader.displayImage(ImageUtil.getImageUrl(answerInfoBean.getAvatar()), vh.headIcon);
            vh.time.setText(answerInfoBean.getCreated_at());
        }

    }

    @Override
    public int getItemCount() {
        return answerInfoBeen.size() + 2;
    }


    @Override
    public int getItemViewType(int position) {
        return getItemCount() - 1 > position ? position : -1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class QuestionDetailsFirstitemHolder extends RecyclerView.ViewHolder {
        ImageView headIcon, coursepicture;
        TextView time, design, questionIntroduction;

        public QuestionDetailsFirstitemHolder(View itemView) {
            super(itemView);
            headIcon = (ImageView) itemView.findViewById(R.id.headIcon);
            coursepicture = (ImageView) itemView.findViewById(R.id.coursepicture);
            time = (TextView) itemView.findViewById(R.id.time);
            design = (TextView) itemView.findViewById(R.id.design);
            questionIntroduction = (TextView) itemView.findViewById(R.id.questionIntroduction);
        }
    }

    public class QuestionAnswerHolder extends RecyclerView.ViewHolder {

        ImageView headIcon;
        TextView name, answerwhatquesttion, answer, time,isadopted;

        public QuestionAnswerHolder(View itemView) {
            super(itemView);

            headIcon = (ImageView) itemView.findViewById(R.id.headIcon);
            isadopted = (TextView) itemView.findViewById(R.id.isadopted);
            name = (TextView) itemView.findViewById(R.id.name);
            answerwhatquesttion = (TextView) itemView.findViewById(R.id.answerwhatquesttion);
            answer = (TextView) itemView.findViewById(R.id.answer);
            time = (TextView) itemView.findViewById(R.id.time);
        }
    }
}
