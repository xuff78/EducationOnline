package com.education.online.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.education.online.R;
import com.education.online.bean.QuestionInfoBean;
import com.education.online.bean.QuestionListHolder;
import com.education.online.util.ImageUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/28.
 */
public class AnswersAdapter extends RecyclerView.Adapter implements View.OnClickListener{


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




    public  AnswersAdapter(Activity act, QuestionListHolder questionListHolder,List<QuestionInfoBean>questionInfoBeens) {
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
            View view = listInflater.inflate(R.layout.myquestionsandanswer_item, null);
            vh = new AnswersAdapter.AnswerHolder(view, pos);
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
            AnswerHolder vh = (AnswerHolder) holder;
            QuestionInfoBean questionInfoBean = questionInfoBeens.get(position);
            vh.answerNum.setText( questionInfoBean.getUser_name());
            vh.design.setText(questionInfoBean.getSubject_name());
            if(questionInfoBean.getAvatar().length()>0)
            imageLoader.displayImage(ImageUtil.getImageUrl(questionInfoBean.getAvatar()), vh.headIcon);
            if(questionInfoBean.getImg().length()>0)
            imageLoader.displayImage(ImageUtil.getImageUrl(questionInfoBean.getImg()),vh.questionpicture);
            else vh.questionpicture.setVisibility(View.GONE);

            if(questionInfoBean.getIs_finished()=="0")
                vh.answerNum.setText("问题尚未解决");
                else
                vh.answerNum.setText("问题已解决");
            vh.questiondetail.setText(questionInfoBean.getIntroduction());
            vh.time.setText(questionInfoBean.getCreated_at());
            vh.integrayTxt.setText(questionInfoBean.getIntegral()+"学分");


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

    public class AnswerHolder extends RecyclerView.ViewHolder{

        ImageView headIcon, questionpicture;
        TextView name, time,answerNum,design, questiondetail, integrayTxt;
        public AnswerHolder(View itemView, int viewType) {
            super(itemView);
            headIcon = (ImageView) itemView.findViewById(R.id.headIcon);
            questionpicture = (ImageView) itemView.findViewById(R.id.questionpicture);
            name= (TextView) itemView.findViewById(R.id.name);
            time = (TextView) itemView.findViewById(R.id.time);
            answerNum = (TextView) itemView.findViewById(R.id.answerNum);
            design= (TextView) itemView.findViewById(R.id.design);
            questiondetail = (TextView) itemView.findViewById(R.id.questiondetail);
            integrayTxt = (TextView) itemView.findViewById(R.id.integrayTxt);
        }
    }

}
