package com.education.online.adapter;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;
import com.education.online.R;
import com.education.online.download.DownloadCourseInfo;
import com.education.online.download.DownloadService;
import com.education.online.download.ThreadInfo;
import com.education.online.util.ActUtil;
import com.education.online.util.Constant;
import com.education.online.util.ImageUtil;
import com.education.online.util.ToastUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by Administrator on 2016/12/8.
 */
public class DownloadingAdapter extends RecyclerView.Adapter <RecyclerView.ViewHolder>{

    private Activity act;
    private LayoutInflater listInflater;
    private List<ThreadInfo> infos;
    private View.OnClickListener listener;
    private ImageLoader imageLoader=ImageLoader.getInstance();
    private int height=0;
    private DownloadService.DownloadBinder binder;

    public DownloadingAdapter(Activity act, List<ThreadInfo> infos, View.OnClickListener listener,
                              DownloadService.DownloadBinder binder) {
        this.act=act;
        listInflater= LayoutInflater.from(act);
        this.infos = infos;
        this.listener = listener;
        height= ImageUtil.dip2px(act, 90);
        this.binder=binder;
    }

    @Override
    public int getItemViewType(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup arg0, int pos) {
        RecyclerView.ViewHolder vh=null;
        View view=listInflater.inflate(R.layout.downloading_list_item, null);
        view.setLayoutParams(new RecyclerView.LayoutParams(-1, height));
        vh = new DirectoryHolder(view, pos);
        // view.setTag(pos);

        return vh;
    }

    @Override
    //视图与数据的绑定，留待以后实现
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int pos) {
        DirectoryHolder  vh = ( DirectoryHolder ) holder;
    }

    @Override
    public int getItemCount() {
        return infos.size();
    }

    public class DirectoryHolder extends RecyclerView.ViewHolder{

        TextView courseName, courseSubName, delBtn, progressInfo;
        ImageView courseImg, downloadIcon;
        SwipeLayout swipeLayout;
        ProgressBar progressBar;
        LinearLayout itemLayout;
        boolean isDownloading=false;
        private ThreadInfo threadInfo=null;

        public DirectoryHolder(View v, int pos) {
            super(v);
            threadInfo=infos.get(pos);
            progressBar = (ProgressBar) v.findViewById(R.id.progressBar);
            swipeLayout = (SwipeLayout) v.findViewById(R.id.swipeLayout);
            swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);
            courseName = (TextView) v.findViewById(R.id.courseName);
            courseSubName = (TextView) v.findViewById(R.id.courseSubName);
            progressInfo = (TextView) v.findViewById(R.id.progressInfo);
            delBtn = (TextView) v.findViewById(R.id.delBtn);
            delBtn.setTag(pos);
            courseImg = (ImageView) v.findViewById(R.id.courseImg);
            downloadIcon = (ImageView) v.findViewById(R.id.downloadIcon);
            delBtn.setOnClickListener(listener);
            imageLoader.displayImage(ImageUtil.getImageUrl(threadInfo.getCourseimg()), courseImg);
            courseName.setText(threadInfo.getSubname());
            progressInfo.setText("共"+threadInfo.getStart()+"/"+threadInfo.getEnd()+"");
            itemLayout=(LinearLayout) v.findViewById(R.id.itemLayout);
            itemLayout.setTag(pos);
            itemLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(isDownloading){
                        binder.pauseDownload(threadInfo);
                    }else{
                        ToastUtils.displayTextLong(act, "继续下载请稍后");
                        binder.startDownload(threadInfo, handler);
                    }
                    isDownloading=!isDownloading;
                }
            });
            setHandler();
        }

        public void setHandler(){
            isDownloading=binder.setCallbackHandker(threadInfo.getUrl(), handler);
            setProgressInfo(threadInfo.getFinished());
            if(isDownloading)
                downloadIcon.setImageResource(R.mipmap.icon_video_stop);
            else
                downloadIcon.setImageResource(R.mipmap.icon_download_white);
        }

        Handler handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case Constant.resumeDownload:
                        downloadIcon.setImageResource(R.mipmap.icon_video_stop);
                        break;
                    case Constant.pouseDownload:
                        downloadIcon.setImageResource(R.mipmap.icon_download_white);
                        break;
                    case Constant.finishDownload:
                        progressBar.setProgress(100);
                        listener.onClick(itemLayout);
                        break;
                    case Constant.errorDownload:
                        downloadIcon.setImageResource(R.mipmap.icon_video_stop);
                        break;
                    case Constant.updateDownload:
                        long finished=msg.getData().getLong("finished");
                        setProgressInfo(finished);
                        break;
                }
            }
        };

        private void setProgressInfo(long finished){
            Double finsihedD=finished/(1024*1024d);
            Double total=threadInfo.getEnd()/(1024*1024d);
            progressInfo.setText(ActUtil.twoDecimal(finsihedD)+"M / "+ActUtil.twoDecimal(total)+"M");
            progressBar.setProgress((int)(finished*100/threadInfo.getEnd()));
        }
    }
}
