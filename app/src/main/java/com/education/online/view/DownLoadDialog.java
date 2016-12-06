package com.education.online.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.education.online.R;
import com.education.online.adapter.DownloadItemAdapter;
import com.education.online.download.DownloadService;
import com.education.online.download.FileInfo;
import com.education.online.inter.weekdayDialogCallback;
import com.education.online.util.ScreenUtil;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/12/5.
 */
public class DownLoadDialog extends Dialog implements View.OnClickListener{
    private ArrayList<FileInfo> files = new ArrayList<>();
    private boolean flag =false;
    private Activity act;
    private int height=0;
    private LinearLayoutManager layoutManager;
    private DownloadItemAdapter adapter;
    private int countNum=0;
    private TextView btnDownload, hintDownload;
    private ImageView selectAllIcon;
    private boolean selectAll=false;

    public DownLoadDialog(Activity act, DownloadCallback callback, ArrayList<FileInfo> files, int height) {
        super(act, R.style.view_dialog);
        this.act = act;
        this.callback=callback;
        this.files=files;
        this.height=height;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.download_dialog);

        initView();

        Window window = getWindow();
        window.setGravity(Gravity.BOTTOM);  //此处可以设置dialog显示的位置
        window.setWindowAnimations(R.style.dialog_popfrombottom);  //添加动画
        window.setLayout(ScreenUtil.getWidth(act), height);

    }

    private void initView() {
        RecyclerView recyclerList = (RecyclerView) findViewById(R.id.courseRecycleView);
        layoutManager = new LinearLayoutManager(act);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerList.setLayoutManager(layoutManager);
        adapter=new DownloadItemAdapter(act, files, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos= (int) view.getTag();
                FileInfo file=files.get(pos);
                if(file.getStatus()==0)
                    file.setStatus(1);
                else if(files.get(pos).getStatus()==1)
                    file.setStatus(0);
                adapter.notifyDataSetChanged();
                refreshCountNum();
            }
        });
        recyclerList.setAdapter(adapter);
        findViewById(R.id.cancelBtn).setOnClickListener(this);
        btnDownload= (TextView) findViewById(R.id.btnDownload);
        findViewById(R.id.btnDownload).setOnClickListener(this);
        hintDownload= (TextView) findViewById(R.id.hintDownload);
        selectAllIcon= (ImageView) findViewById(R.id.selectAllIcon);
        findViewById(R.id.selectedAllBtn).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.cancelBtn:
                dismiss();
                break;
            case R.id.selectedAllBtn:
                if(selectAll) {
                    selectAllIcon.setImageResource(R.mipmap.icon_round);
                    for (FileInfo info : files) {
                        if (info.getStatus() == 1)
                            info.setStatus(0);
                    }
                }else{
                    selectAllIcon.setImageResource(R.mipmap.icon_round_right);
                    for (FileInfo info : files) {
                        if (info.getStatus() == 0)
                            info.setStatus(1);
                    }
                }
                selectAll=!selectAll;
                adapter.notifyDataSetChanged();
                refreshCountNum();
                break;
            case R.id.btnDownload:
                if(countNum>0) {
                    if (callback != null)
                        callback.startDownload(files);
                    dismiss();
                }
                break;
        }
    }

    private void refreshCountNum(){
        countNum=0;
        for(FileInfo info:files){
            if(info.getStatus()==1)
                countNum++;
        }
        if(countNum>0){
            btnDownload.setBackgroundResource(R.color.normal_red);
        }else{
            btnDownload.setBackgroundResource(R.color.light_gray);
        }
        hintDownload.setText("已选"+countNum+"个");
    }

    DownloadCallback callback;
    public interface DownloadCallback{
        void startDownload(ArrayList<FileInfo> fileInfos);
    }
}
