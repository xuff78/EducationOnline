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
 * Created by 可爱的蘑菇 on 2016/9/16.
 */
public class WalletHistoryAdapter extends RecyclerView.Adapter <RecyclerView.ViewHolder>{
    private Activity activity;
    private LayoutInflater inflater;


    public WalletHistoryAdapter(Activity activity){
        this.activity = activity;
        inflater = LayoutInflater.from(activity);


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
        View view=inflater.inflate(R.layout.wallet_history_item, null);
        vh = new CourseItemHolder(view, pos);
        return vh;
    }

    @Override
    //绑定数据源
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 6;
    }

    public class CourseItemHolder extends RecyclerView.ViewHolder
    {
        TextView courseName, balanceTxt, dateTxt, costTxt;
        public CourseItemHolder(View v, int position)
        {
            super(v);
            courseName = (TextView) v.findViewById(R.id.CourseName);
            balanceTxt = (TextView) v.findViewById(R.id.courseNumTxt);
            dateTxt = (TextView) v.findViewById(R.id.CourseTime);
            costTxt = (TextView) v.findViewById(R.id.CoursePrice);

        }


    }
}
