package com.education.online.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;
import com.education.online.R;
import com.education.online.download.DownloadCourseInfo;
import com.education.online.util.ImageUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by Administrator on 2016/12/7.
 */
public class DownloadedAdapter extends RecyclerView.Adapter <RecyclerView.ViewHolder>{

    private Activity act;
    private LayoutInflater listInflater;
    private List<DownloadCourseInfo> infos;
    private View.OnClickListener listener;
    private ImageLoader imageLoader=ImageLoader.getInstance();
    private int height=0;

    public DownloadedAdapter(Activity act, List<DownloadCourseInfo> infos, View.OnClickListener listener) {
        this.act=act;
        listInflater= LayoutInflater.from(act);
        this.infos = infos;
        this.listener = listener;
        height=ImageUtil.dip2px(act, 90);
    }

    @Override
    public int getItemViewType(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup arg0, int pos) {
        RecyclerView.ViewHolder vh=null;
        View view=listInflater.inflate(R.layout.downloaded_item_layout, null);
        view.setLayoutParams(new RecyclerView.LayoutParams(-1, height));
        vh = new DirectoryHolder(view, pos);
        // view.setTag(pos);

        return vh;
    }

    @Override
    //视图与数据的绑定，留待以后实现
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int pos) {
        DirectoryHolder  vh = ( DirectoryHolder ) holder;
        DownloadCourseInfo info=infos.get(pos);
        imageLoader.displayImage(ImageUtil.getImageUrl(info.getCourseImg()), vh.courseImg);
        vh.courseName.setText(info.getCourseName());
        vh.courseSubName.setText("");
        vh.courseInfo.setText("共"+info.getFileCount()+"节，下载了"+info.getCourses().size()+"节");
    }

    @Override
    public int getItemCount() {
        return infos.size();
    }

    public class DirectoryHolder extends RecyclerView.ViewHolder{

        TextView courseName, courseSubName, courseInfo, delBtn;
        ImageView courseImg;
        SwipeLayout swipeLayout;
        LinearLayout itemLayout;
        public DirectoryHolder(View v, int pos) {
            super(v);
            swipeLayout = (SwipeLayout) v.findViewById(R.id.swipeLayout);
            swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);
            courseName = (TextView) v.findViewById(R.id.courseName);
            courseSubName = (TextView) v.findViewById(R.id.courseSubName);
            courseInfo = (TextView) v.findViewById(R.id.courseInfo);
            delBtn = (TextView) v.findViewById(R.id.delBtn);
            delBtn.setTag(pos);
            courseImg = (ImageView) v.findViewById(R.id.courseImg);
            delBtn.setOnClickListener(listener);
            itemLayout=(LinearLayout) v.findViewById(R.id.itemLayout);
            itemLayout.setTag(pos);
            itemLayout.setOnClickListener(listener);
        }
    }
}
