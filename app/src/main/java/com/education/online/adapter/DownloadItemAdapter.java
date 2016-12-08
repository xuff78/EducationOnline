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
import com.education.online.download.ThreadInfo;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/12/5.
 */
public class DownloadItemAdapter  extends RecyclerView.Adapter <RecyclerView.ViewHolder>{

    private Activity act;
    private LayoutInflater listInflater;
    private View.OnClickListener listener;
    private ArrayList<ThreadInfo> files;

    public DownloadItemAdapter(Activity act, ArrayList<ThreadInfo> files, View.OnClickListener listener) {
        this.act=act;
        listInflater= LayoutInflater.from(act);
        this.files = files;
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
        View view=listInflater.inflate(R.layout.download_listitem, null);
        view.setLayoutParams(new RecyclerView.LayoutParams(-1,-2));
        vh = new DirectoryHolder(view, pos);
        // view.setTag(pos);

        return vh;
    }

    @Override
    //视图与数据的绑定，留待以后实现
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int pos) {
        DirectoryHolder  vh = ( DirectoryHolder ) holder;
        ThreadInfo fileInfo=files.get(pos);
        vh.directorytext.setText((pos+1)+"."+fileInfo.getSubname());
        switch (fileInfo.getStatus()){
            case 0:
                vh.selectedIcon.setImageResource(R.mipmap.icon_round);
                break;
            case 1:
                vh.selectedIcon.setImageResource(R.mipmap.icon_round_right);
                break;
            case 2:
                vh.selectedIcon.setImageResource(R.mipmap.icon_download);
                vh.directorytext.setTextColor(act.getResources().getColor(R.color.light_gray));
                break;
            case 3:
                vh.selectedIcon.setImageResource(R.mipmap.right_blue);
                vh.directorytext.setTextColor(act.getResources().getColor(R.color.light_gray));
                break;
        }
    }

    @Override
    public int getItemCount() {
        return files.size();
    }

    public class DirectoryHolder extends RecyclerView.ViewHolder{

        TextView directorytext;
        ImageView selectedIcon;
        LinearLayout textholder;
        public DirectoryHolder(View v, int pos) {
            super(v);
            selectedIcon = (ImageView) v.findViewById(R.id.selectedIcon);
            directorytext = (TextView) v.findViewById(R.id.textdirectory);
            textholder = (LinearLayout) v.findViewById(R.id.textholder);
            v.setTag(pos);
            v.setOnClickListener(listener);
            ThreadInfo fileInfo=files.get(pos);
            if(fileInfo.getStatus()==1)
                fileInfo.setStatus(0);
        }
    }
}
