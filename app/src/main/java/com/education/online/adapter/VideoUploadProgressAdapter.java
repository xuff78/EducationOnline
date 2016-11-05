package com.education.online.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.education.online.R;
import com.education.online.bean.UploadVideoProgress;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

/**
 * Created by Great Gao on 2016/11/2.
 */
public class VideoUploadProgressAdapter extends BaseAdapter {
    private Activity act;
    private LayoutInflater inflater;
    private View.OnClickListener listener;
    private ArrayList<UploadVideoProgress>  uploadVideoProgresses;
   /*传入参数:视频的uri在本Adapter中获取缩略图；
   进度条回调函数；
    */



    public VideoUploadProgressAdapter(Activity act,View.OnClickListener listener,ArrayList<UploadVideoProgress> uploadVideoProgress) {
        this.act = act;
        inflater = LayoutInflater.from(act);
        this.listener=listener;
        this.uploadVideoProgresses = uploadVideoProgress;
    }

    @Override
    public int getCount() {
        return uploadVideoProgresses.size();
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
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = inflater.inflate(R.layout.video_upload_progress, null);
        TextView delete = (TextView) convertView.findViewById(R.id.delete);
        TextView open = (TextView) convertView.findViewById(R.id.open);
        ImageView videoImage = (ImageView) convertView.findViewById(R.id.videoImage);
        ProgressBar progress = (ProgressBar) convertView.findViewById(R.id.progress);
        EditText resourseName = (EditText) convertView.findViewById(R.id.resourseName);
            delete.setTag(position);
            open.setTag(position);
        open.setOnClickListener(listener);
        delete.setOnClickListener(listener);
        return convertView;
    }

}
