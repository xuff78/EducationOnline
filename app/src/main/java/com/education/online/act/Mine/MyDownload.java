package com.education.online.act.Mine;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.education.online.R;
import com.education.online.act.BaseFrameAct;
import com.education.online.bean.CourseBean;
import com.education.online.bean.FilterAll;
import com.education.online.bean.FilterInfo;
import com.education.online.download.DownloadService;
import com.education.online.fragment.CourseVideoList;
import com.education.online.fragment.OnlineCoursePage;
import com.education.online.fragment.mycenter.DownloadedListPage;
import com.education.online.fragment.mycenter.DownloadingListPage;
import com.education.online.http.CallBack;
import com.education.online.http.HttpHandler;
import com.education.online.http.Method;
import com.education.online.inter.CourseUpdate;
import com.education.online.util.JsonUtil;
import com.education.online.view.MenuPopup;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/7.
 */
public class MyDownload extends BaseFrameAct {

    private View selectTypeView;
    private View hintLayout;
    private FrameLayout filterDetailLayout;
    private MenuPopup popup;
    private int type=0; //0:课程  1:收藏  2:下载
    private DownloadedListPage downloadedListPage = new DownloadedListPage();
    private TextView menuTxt1, menuTxt2;
    private View menuLine1, menuLine2;

    private DownloadService.DownloadBinder myBinder;
    private ServiceConnection connection = new ServiceConnection() {

        @Override
        public void onServiceDisconnected(ComponentName name) {
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            myBinder = (DownloadService.DownloadBinder) service;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.download_layout);

        connectService();
        _setHeaderTitle("我的下载");
        initView();
        openFragment(R.id.fragment_layout, downloadedListPage);

    }

    private void connectService() {
        Intent startIntent=new Intent(this, DownloadService.class);
        startService(startIntent);

        Intent bindIntent = new Intent(this, DownloadService.class);
        bindService(bindIntent, connection, BIND_AUTO_CREATE);
    }

    private void initView() {
        menuTxt1= (TextView) findViewById(R.id.menuTxt1);
        menuTxt2= (TextView) findViewById(R.id.menuTxt2);
        menuLine1=findViewById(R.id.menuLine1);
        menuLine2=findViewById(R.id.menuLine2);
        findViewById(R.id.menuLayout1).setOnClickListener(typeListener);
        findViewById(R.id.menuLayout2).setOnClickListener(typeListener);
    }

    View.OnClickListener typeListener=new View.OnClickListener(){

        @Override
        public void onClick(View view) {
            if(view!=selectTypeView) {
                selectTypeView = view;
                switch (view.getId()) {
                    case R.id.menuLayout1:
                        menuTxt1.setTextColor(getResources().getColor(R.color.hard_gray));
                        menuLine1.setVisibility(View.VISIBLE);
                        menuTxt2.setTextColor(getResources().getColor(R.color.normal_gray));
                        menuLine2.setVisibility(View.GONE);
                        openFragment(R.id.fragment_layout, downloadedListPage);
                        break;
                    case R.id.menuLayout2:
                        menuTxt2.setTextColor(getResources().getColor(R.color.hard_gray));
                        menuLine2.setVisibility(View.VISIBLE);
                        menuTxt1.setTextColor(getResources().getColor(R.color.normal_gray));
                        menuLine1.setVisibility(View.GONE);
                        DownloadingListPage downloadingListPage = new DownloadingListPage();
                        downloadingListPage.setBinder(myBinder);
                        openFragment(R.id.fragment_layout, downloadingListPage);
                        break;
                }
            }
        }
    };

    @Override
    protected void onDestroy() {
        unbindService(connection);
        super.onDestroy();
    }
}
