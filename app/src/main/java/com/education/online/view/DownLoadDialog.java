package com.education.online.view;

import android.app.Activity;
import android.app.Dialog;
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
import com.education.online.download.FileInfo;
import com.education.online.inter.weekdayDialogCallback;
import com.education.online.util.ScreenUtil;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/12/5.
 */
public class DownLoadDialog extends Dialog implements View.OnClickListener{
    private View.OnClickListener listener;
    private ArrayList<FileInfo> files = new ArrayList<>();
    private boolean flag =false;
    private Activity act;
    private int height=0;
    private LinearLayoutManager layoutManager;
    private DownloadItemAdapter adapter;

    public DownLoadDialog(Activity act, View.OnClickListener listener, ArrayList<FileInfo> files, int height) {
        super(act, R.style.view_dialog);
        this.act = act;
        this.listener=listener;
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
        adapter=new DownloadItemAdapter(act, files, this);
        recyclerList.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {

    }
}
