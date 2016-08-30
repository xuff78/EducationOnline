package com.education.online.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.education.online.R;
import com.education.online.util.ImageUtil;
import com.education.online.util.ScreenUtil;

/**
 * Created by Administrator on 2016/8/29.
 */
public class TeacherOrderAdapter extends RecyclerView.Adapter <RecyclerView.ViewHolder>{
    private Activity activity;
    private LayoutInflater inflater;
    private int imgLength=0;
    private LinearLayout.LayoutParams llp;

    public TeacherOrderAdapter(Activity activity){
        this.activity = activity;
        inflater = LayoutInflater.from(activity);
        imgLength= (ScreenUtil.getWidth(activity)-ImageUtil.dip2px(activity, 70))/4; //左右边距40， 中间3*10
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
        View view=inflater.inflate(R.layout.teacher_order_item, null);
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
        ImageView headImg, statusImg;
        TextView teacherName, typeName, priceTxt, subjectName;
        LinearLayout imgsLayout;
        public CourseItemHolder(View v, int position)
        {
            super(v);
            headImg = (ImageView) v.findViewById(R.id.headImg);
            statusImg = (ImageView) v.findViewById(R.id.statusImg);
            teacherName = (TextView) v.findViewById(R.id.teacherName);
            typeName = (TextView) v.findViewById(R.id.typeName);
            priceTxt = (TextView) v.findViewById(R.id.priceTxt);
            subjectName = (TextView) v.findViewById(R.id.subjectName);
            imgsLayout= (LinearLayout) v.findViewById(R.id.imgsLayout);
            imgsLayout.setVisibility(View.VISIBLE);

            int size=4;
            for(int i=0;i<size;i++){
                ImageView img=new ImageView(activity);
                img.setImageResource(R.color.whitesmoke);
                imgsLayout.addView(img, llp);
            }
        }


    }
}
