package com.education.online.adapter;

import android.support.v7.widget.RecyclerView;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.education.online.R;
import com.education.online.bean.AnswerInfoBean;
import com.education.online.bean.AnswerListHolder;
import com.education.online.bean.QuestionInfoBean;
import com.education.online.bean.QuestionListHolder;
import com.education.online.util.ImageUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/17.
 */

public class MyquestionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener{

    private Activity act;
    private ImageLoader imageLoader;
    private LayoutInflater listInflater;
    private String loadingHint = "";
    private QuestionListHolder questionListHolder = new QuestionListHolder();
    private List<QuestionInfoBean> questionInfoBeens  = new ArrayList<>();


    @Override
    public void onClick(View view) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取数据
            mOnItemClickListener.onItemClick(view);
        }
    }


    public void setLoadingHint(String hint) {
        loadingHint = hint;
        notifyDataSetChanged();
    }

    ////定义个接口
    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(View view);
    }

    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }




    public  MyquestionAdapter(Activity act, QuestionListHolder questionListHolder,List<QuestionInfoBean>questionInfoBeens) {
        this.act = act;
        this.listInflater = LayoutInflater.from(act);
        this.questionInfoBeens = questionInfoBeens;
        this.questionListHolder = questionListHolder;
        this.imageLoader = ImageLoader.getInstance();

    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int pos) {
        RecyclerView.ViewHolder vh =null;
        if(pos==-1){
            View view = listInflater.inflate(R.layout.footer_layout, null);
            view.setLayoutParams(new RecyclerView.LayoutParams(-1, ImageUtil.dip2px(act, 45)));
            vh = new FooterViewHolder(view);
        }else {
            View view = listInflater.inflate(R.layout.myqurestion_items, null);
            vh = new MyquestionAdapter.MyquestionitemHolder(view, pos);
            view.setTag(pos);
            view.setOnClickListener(this);
        }
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof FooterViewHolder) {
            FooterViewHolder fvh = (FooterViewHolder) holder;
            fvh.footerHint.setText(loadingHint);
            if(getItemCount()==1)
                fvh.footerHint.setText("暂无数据");
        } else {
            MyquestionitemHolder vh = (MyquestionitemHolder) holder;
            QuestionInfoBean questionInfoBean = questionInfoBeens.get(position);
            vh.answerNum.setText( "已有"+questionInfoBean.getAnswer_count()+"人回答");
            vh.bonus.setText(questionInfoBean.getIntegral()+"积分");
           if(questionInfoBean.getImg().length()>0){
               imageLoader.displayImage(ImageUtil.getImageUrl(questionInfoBean.getImg()),vh.CourseImage);
           }else{
               vh.CourseImage.setVisibility(View.GONE);
          }
            vh.questiondate.setText(questionInfoBean.getCreated_at());
            vh.design.setText(questionInfoBean.getSubject_name());
            vh.CourseName.setText(questionInfoBean.getIntroduction());
        if(questionInfoBean.getIs_finished()=="1")
        {
            vh.isfinished.setText("已完结");
        }else{
            vh.isfinished.setText("未完结");}
        }



    }

    @Override
    public int getItemCount() {
        return questionInfoBeens.size()+1;
    }

    @Override
    public int getItemViewType(int position) {
        return getItemCount()-1>position?position:-1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class MyquestionitemHolder extends RecyclerView.ViewHolder {

        TextView questiondate, bonus, CourseName, answerNum,isfinished,design;

        ImageView CourseImage;

        MyquestionitemHolder(View v, int pos) {
            super(v);
            design= (TextView) v.findViewById(R.id.design);
            questiondate = (TextView) v.findViewById(R.id.questiondate);
            bonus = (TextView) v.findViewById(R.id.bonus);
            CourseName = (TextView) v.findViewById(R.id.CourseName);
            CourseImage = (ImageView) v.findViewById(R.id.CourseImage);
            answerNum = (TextView) v.findViewById(R.id.answerNum);
            isfinished = (TextView) v.findViewById(R.id.isfinished);
        }

    }

}
