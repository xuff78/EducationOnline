package com.education.online.adapter;

import android.support.v7.widget.RecyclerView;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.education.online.R;
/**
 * Created by Administrator on 2016/9/17.
 */

public class MyquestionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Activity act;
    private LayoutInflater listInflater;

    public MyquestionAdapter(Activity act, String jason) {
        this.act = act;
        this.listInflater = LayoutInflater.from(act);

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh = null;
        View view = listInflater.inflate(R.layout.myqurestion_items, null);
        vh = new MyquestionAdapter.MyquestionitemHolder(view, viewType);
        return vh;

        ////////////

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyquestionitemHolder vh = (MyquestionitemHolder) holder;

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

    public class MyquestionitemHolder extends RecyclerView.ViewHolder {

        TextView questiondate, questiontime, bonus, CourseName, answerNum;

        MyquestionitemHolder(View v, int pos) {
            super(v);
            questiondate = (TextView) v.findViewById(R.id.questiondate);
            questiontime = (TextView) v.findViewById(R.id.questiontime);
            bonus = (TextView) v.findViewById(R.id.bonus);
            CourseName = (TextView) v.findViewById(R.id.CourseName);
            answerNum = (TextView) v.findViewById(R.id.answerNum);
        }

    }

}
