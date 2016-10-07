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
 * Created by Administrator on 2016/9/29.
 */
public class QuestionDetailsitemAdapter extends RecyclerView.Adapter {
    private Activity act;
    private LayoutInflater listInflater;

    public QuestionDetailsitemAdapter(Activity act, String jason) {
        this.act = act;
        this.listInflater = LayoutInflater.from(act);

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh = null;
        if (viewType == 1) {
            View view = listInflater.inflate(R.layout.myquestiondetailsfirstitem, null);
            vh = new QuestionDetailsFirstitemHolder(view);
            return vh;
        } else {
            View view = listInflater.inflate(R.layout.myquestiondetailsitems, null);
            vh = new QuestionAnswerHolder(view);
            return vh;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 5;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return 1;
        } else {
            return 2;
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class QuestionDetailsFirstitemHolder extends RecyclerView.ViewHolder {
        ImageView headIcon, coursepicture;
        TextView time, design, classname;

        public QuestionDetailsFirstitemHolder(View itemView) {
            super(itemView);
            headIcon = (ImageView) itemView.findViewById(R.id.headIcon);
            coursepicture = (ImageView) itemView.findViewById(R.id.coursepicture);
            time = (TextView) itemView.findViewById(R.id.time);
            design = (TextView) itemView.findViewById(R.id.design);
            classname = (TextView) itemView.findViewById(R.id.classname);
        }
    }

    public class QuestionAnswerHolder extends RecyclerView.ViewHolder {

        ImageView headIcon;
        TextView name, answerwhatquesttion, answer, time;

        public QuestionAnswerHolder(View itemView) {
            super(itemView);
            headIcon = (ImageView) itemView.findViewById(R.id.headIcon);
            name = (TextView) itemView.findViewById(R.id.name);
            answerwhatquesttion = (TextView) itemView.findViewById(R.id.answerwhatquesttion);
            answer = (TextView) itemView.findViewById(R.id.answer);
            time = (TextView) itemView.findViewById(R.id.time);
        }
    }
}
