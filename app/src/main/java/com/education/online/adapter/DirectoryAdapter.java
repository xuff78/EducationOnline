package com.education.online.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.education.online.R;
import com.education.online.bean.CourseDetailBean;
import com.education.online.bean.CourseExtm;

import java.util.List;

/**
 * Created by Administrator on 2016/8/25.
 */

public class DirectoryAdapter extends RecyclerView.Adapter <RecyclerView.ViewHolder>{

    private Activity act;
    private LayoutInflater listInflater;
    private CourseDetailBean courseDetailBean;


    public DirectoryAdapter(Activity act, CourseDetailBean courseDetailBean) {
        this.act=act;
        listInflater= LayoutInflater.from(act);
        this.courseDetailBean = courseDetailBean;
    }

    @Override
    public int getItemViewType(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup arg0, int pos) {
        RecyclerView.ViewHolder vh=null;
            View view=listInflater.inflate(R.layout.directorylayout, null);
            vh = new DirectoryAdapter. DirectoryHolder(view, pos);
        return vh;
    }

    @Override
    //视图与数据的绑定，留待以后实现
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int pos) {
            DirectoryHolder  vh = ( DirectoryHolder ) holder;
        List<CourseExtm> list = courseDetailBean.getCourse_extm();
        vh.directorytext.setText((pos+1)+"."+list.get(pos).getName());
    }

    @Override
    public int getItemCount() {
      List<CourseExtm> list = courseDetailBean.getCourse_extm();


        return list.size();
    }


    public String [] splitResource(String url){
        String str[]=url.split(",");
        String temp1 ="";
        for(int i=0;i<str.length;i++){
            String str2[]=str[i].split("_");
            temp1 = temp1+str2[0]+',';

        }
        return temp1.split(",");
    }

    public class DirectoryHolder extends RecyclerView.ViewHolder{

        TextView directorytext;
        public DirectoryHolder(View v, int pos) {
            super(v);
            directorytext = (TextView) v.findViewById(R.id.textdirectory);
            String information = directorytext .getText().toString();
        }
    }
}



