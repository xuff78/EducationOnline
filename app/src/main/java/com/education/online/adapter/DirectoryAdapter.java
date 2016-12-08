package com.education.online.adapter;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.education.online.R;
import com.education.online.act.VideoMainPage;
import com.education.online.download.DownloadService;
import com.education.online.download.ThreadInfo;
import com.education.online.util.Constant;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/8/25.
 */

public class DirectoryAdapter extends RecyclerView.Adapter <RecyclerView.ViewHolder>{

    private Activity act;
    private LayoutInflater listInflater;
    private ArrayList<ThreadInfo> infos;
    private View.OnClickListener listener;
    private DownloadService.DownloadBinder myBinder;

    public DirectoryAdapter(Activity act, ArrayList<ThreadInfo> infos, View.OnClickListener listener,
                            DownloadService.DownloadBinder myBinder) {
        this.act=act;
        listInflater= LayoutInflater.from(act);
        this.infos = infos;
        this.listener = listener;
        this.myBinder=myBinder;
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
        ThreadInfo info;
        int pos=0;

        public DirectoryHolder(View v, int pos) {
            super(v);
            this.pos=pos;
            info=infos.get(pos);
            statusTxt = (TextView) v.findViewById(R.id.statusTxt);
            directorytext = (TextView) v.findViewById(R.id.textdirectory);
            textholder = (LinearLayout) v.findViewById(R.id.textholder);
            textholder.setOnClickListener(listener);
            boolean isDownloading=myBinder.setCallbackHandker(info.getUrl(), handler);
            switch (info.getStatus()){
                case 2:
                    statusTxt.setText(info.getFinished()*100/info.getEnd()+"%");
                    break;
                case 3:
                    statusTxt.setText("已下载");
                    break;
            }
        }

        Handler handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case Constant.updateDownload:
                        long finished=msg.getData().getLong("finished");
                        statusTxt.setText(finished*100/info.getEnd()+"%");
                        break;
                    case Constant.finishDownload:
                        statusTxt.setText("已下载");
                        ((VideoMainPage)act).files.get(pos).setStatus(3);
                        break;
                }
            }
        };
    }
}



