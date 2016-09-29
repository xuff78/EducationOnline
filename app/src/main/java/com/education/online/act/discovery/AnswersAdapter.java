package com.education.online.act.discovery;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.education.online.R;
import com.tencent.mm.sdk.diffdev.OAuthListener;

/**
 * Created by Administrator on 2016/9/28.
 */
public class AnswersAdapter extends RecyclerView.Adapter implements View.OnClickListener{


    private Activity act;
    private LayoutInflater listInflater;

    @Override
    public void onClick(View view) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取数据
            mOnItemClickListener.onItemClick(view);
        }
    }

    ////定义个接口
    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(View view);
    }

    private OnRecyclerViewItemClickListener mOnItemClickListener = null;
    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }




    public  AnswersAdapter(Activity act, String jason){
        this.act = act;
        this.listInflater = LayoutInflater.from(act);

    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh =null;
        View view = listInflater.inflate(R.layout.myquestionsandanswer_item,null);
        vh = new AnswersAdapter.AnswerHolder(view,viewType);
        view.setOnClickListener(this);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        AnswerHolder vh = (AnswerHolder) holder;

    }

    @Override
    public int getItemCount() {
        return 3;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class AnswerHolder extends RecyclerView.ViewHolder{

        ImageView headIcon, questionpicture;
        TextView name, time,answerNum,design, questiondetail;
        public AnswerHolder(View itemView, int viewType) {
            super(itemView);
            headIcon = (ImageView) itemView.findViewById(R.id.headIcon);
            questionpicture = (ImageView) itemView.findViewById(R.id.questionpicture);
            name= (TextView) itemView.findViewById(R.id.name);
            time = (TextView) itemView.findViewById(R.id.time);
            answerNum = (TextView) itemView.findViewById(R.id.answerNum);
            design= (TextView) itemView.findViewById(R.id.design);
            questiondetail = (TextView) itemView.findViewById(R.id.questiondetail);
        }
    }
}
