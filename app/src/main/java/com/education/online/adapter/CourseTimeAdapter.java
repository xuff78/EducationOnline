package com.education.online.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.education.online.R;
import com.education.online.bean.CourseTimeBean;
import com.education.online.util.ImageUtil;
import com.education.online.util.ScreenUtil;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/9/7.
 */
public class CourseTimeAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private LinearLayout.LayoutParams llp;
    private ArrayList<CourseTimeBean> courses;
    private View.OnClickListener listener;

    public CourseTimeAdapter(Activity activity, ArrayList<CourseTimeBean> courses, View.OnClickListener listener){
        this.courses=courses;
        this.listener=listener;
        inflater = LayoutInflater.from(activity);
    }

    @Override
    public int getCount() {
        return courses.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View v = inflater.inflate(R.layout.course_edit_item, null);
        CourseTimeBean course=courses.get(position);

        TextView txt= (TextView) v.findViewById(R.id.courseInfo);
        txt.setText((position+1)+". "+course.getDisplayTxt());
        View delBtn=v.findViewById(R.id.delBtn);
        delBtn.setOnClickListener(listener);
        delBtn.setTag(position);
        View modifyBtn=v.findViewById(R.id.modifyBtn);
        modifyBtn.setTag(position);
        modifyBtn.setOnClickListener(listener);

        return v;
    }
}
