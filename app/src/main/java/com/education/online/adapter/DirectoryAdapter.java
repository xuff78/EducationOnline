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
 * Created by Administrator on 2016/8/25.
 */

public class DirectoryAdapter extends RecyclerView.Adapter <RecyclerView.ViewHolder>{

    private Activity act;
    private LayoutInflater listInflater;


    public DirectoryAdapter(Activity act, String jason) {
        this.act=act;
        listInflater= LayoutInflater.from(act);
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
    }

    @Override
    public int getItemCount() {
        return 5;
    }



    public class DirectoryHolder extends RecyclerView.ViewHolder{

        TextView directorytext;
        public DirectoryHolder(View v, int pos) {
            super(v);
            directorytext = (TextView) v.findViewById(R.id.textdirectory);
            String information = directorytext .getText().toString();
            information = (pos+1) + ". " +information;
            directorytext .setText(information);
        }
    }
}



