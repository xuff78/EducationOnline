package com.education.online.act.discovery;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.education.online.R;

/**
 * Created by Administrator on 2016/9/28.
 */
public class MyAnswersAdapter extends RecyclerView.Adapter {


    private Activity act;
    private LayoutInflater listInflater;

    public MyAnswersAdapter(Activity act, String jason){
        this.act = act;
        this.listInflater = LayoutInflater.from(act);

    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh =null;
        View view = listInflater.inflate(R.layout.myanswer_item,null);
        vh = new MyAnswersAdapter.AnswerHolder(view,viewType);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        AnswerHolder vh = (AnswerHolder) holder;

    }

    @Override
    public int getItemCount() {
        return 2;
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
