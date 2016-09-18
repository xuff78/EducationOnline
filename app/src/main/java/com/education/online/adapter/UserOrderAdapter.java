package com.education.online.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.education.online.R;
import com.education.online.act.Mine.UserOrderDetail;
import com.education.online.util.ImageUtil;
import com.education.online.util.ScreenUtil;

/**
 * Created by 可爱的蘑菇 on 2016/9/17.
 */
public class UserOrderAdapter extends RecyclerView.Adapter <RecyclerView.ViewHolder>{
    private Activity activity;
    private LayoutInflater inflater;
    private int imgLength=0;
    private LinearLayout.LayoutParams llp;

    public UserOrderAdapter(Activity activity){
        this.activity = activity;
        inflater = LayoutInflater.from(activity);
        imgLength= (ScreenUtil.getWidth(activity)- ImageUtil.dip2px(activity, 70))/4; //左右边距40， 中间3*10
        llp=new LinearLayout.LayoutParams(imgLength, imgLength);
        llp.rightMargin=ImageUtil.dip2px(activity, 10);
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
        View view=inflater.inflate(R.layout.user_order_item, null);
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
        ImageView CourseImage, statusImg;
        TextView teacherName, typeName, CoursePrice, CourseName;
        LinearLayout imgsLayout;
        public CourseItemHolder(View v, int position)
        {
            super(v);
            CourseImage = (ImageView) v.findViewById(R.id.CourseImage);
            statusImg = (ImageView) v.findViewById(R.id.statusImg);
            teacherName = (TextView) v.findViewById(R.id.teacherName);
            typeName = (TextView) v.findViewById(R.id.typeName);
            CoursePrice = (TextView) v.findViewById(R.id.CoursePrice);
            CourseName = (TextView) v.findViewById(R.id.CourseName);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    activity.startActivity(new Intent(activity, UserOrderDetail.class));
                }
            });
        }

    }
}
