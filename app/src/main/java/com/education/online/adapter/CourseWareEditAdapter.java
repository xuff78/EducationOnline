package com.education.online.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.education.online.R;
import com.education.online.bean.UploadVideoProgress;
import com.education.online.util.ImageUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/3/3.
 */

public class CourseWareEditAdapter extends BaseAdapter {
    private Activity act;
    private LayoutInflater inflater;
    private ArrayList<UploadVideoProgress> uploadVideoProgresses;
    private View.OnClickListener listener;
   /*传入参数:视频的uri在本Adapter中获取缩略图；
   进度条回调函数；
    */

    public CourseWareEditAdapter(Activity act,View.OnClickListener listener,ArrayList<UploadVideoProgress> uploadVideoProgress) {
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

        convertView = inflater.inflate(R.layout.courseware_item_edit, null);
        TextView delete = (TextView) convertView.findViewById(R.id.delete);
        TextView open = (TextView) convertView.findViewById(R.id.open);
        ImageView videoImage = (ImageView) convertView.findViewById(R.id.videoImage);

        ImageLoader.getInstance().displayImage(ImageUtil.getImageUrl(uploadVideoProgresses.get(position).getUrl()), videoImage);

        ProgressBar progress = (ProgressBar) convertView.findViewById(R.id.progress);
        EditText resourseName = (EditText) convertView.findViewById(R.id.resourseName);
        resourseName.setText(uploadVideoProgresses.get(position).getDescription());
        progress.setProgress(uploadVideoProgresses.get(position).getProgress());
        delete.setTag(position);
        open.setTag(position);
        open.setOnClickListener(listener);
        delete.setOnClickListener(listener);
        convertView.setTag(R.id.tag_progress_value1,progress);
        convertView.setTag(R.id.tag_videodescription,resourseName);
        return convertView;
    }

}
