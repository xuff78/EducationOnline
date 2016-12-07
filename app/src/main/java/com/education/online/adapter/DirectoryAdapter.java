package com.education.online.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.education.online.R;
import com.education.online.download.ThreadInfo;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/8/25.
 */

public class DirectoryAdapter extends RecyclerView.Adapter <RecyclerView.ViewHolder>{

    private Activity act;
    private LayoutInflater listInflater;
    private ArrayList<ThreadInfo> infos;
    private View.OnClickListener listener;


    public DirectoryAdapter(Activity act, ArrayList<ThreadInfo> infos, View.OnClickListener listener) {
        this.act=act;
        listInflater= LayoutInflater.from(act);
        this.infos = infos;
        this.listener = listener;
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
       // view.setTag(pos);

        return vh;
    }

    @Override
    //视图与数据的绑定，留待以后实现
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int pos) {
            DirectoryHolder  vh = ( DirectoryHolder ) holder;
        vh.textholder.setTag(pos);
        vh.directorytext.setText((pos+1)+"."+infos.get(pos).getSubname());
        switch (infos.get(pos).getStatus()){
            case 2:
                vh.statusTxt.setTextColor(act.getResources().getColor(R.color.light_gray));
                vh.statusTxt.setText("正在下载");
                break;
            case 3:
                vh.statusTxt.setText("已下载");
                break;
        }

    }

    @Override
    public int getItemCount() {
        return infos.size();
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

        TextView directorytext, statusTxt;
        LinearLayout textholder;
        public DirectoryHolder(View v, int pos) {
            super(v);
            statusTxt = (TextView) v.findViewById(R.id.statusTxt);
            directorytext = (TextView) v.findViewById(R.id.textdirectory);
            textholder = (LinearLayout) v.findViewById(R.id.textholder);
            textholder.setOnClickListener(listener);
            String information = directorytext .getText().toString();
        }
    }
}



