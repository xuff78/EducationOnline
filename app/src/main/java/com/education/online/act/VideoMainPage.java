package com.education.online.act;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.education.online.R;
import com.education.online.fragment.CoursePage;
import com.education.online.fragment.VideoPage;

public class VideoMainPage extends BaseFrameAct implements View.OnClickListener{

    int currentPos = 0;
    private LinearLayout addfavorite_layout, share_layout, download_layout;
    private TextView textaddfavorite, textshare, textdownload, textaddorbuy;
    private ImageView addfavorite, share, download;
    private View lastSelectedView=null;
    private VideoPage videopage = new VideoPage();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.basicframe_video);
        _setRightHomeGone();
        _setHeaderGone();
        _setLeftBackGone();
        initView();
    }

    private void initView() {
        addfavorite = (ImageView) findViewById(R.id.addfavorite);
        share = (ImageView) findViewById(R.id.share);
        download = (ImageView) findViewById(R.id.download);
        textaddfavorite = (TextView) findViewById(R.id.textAddFavorite);
        textshare = (TextView) findViewById(R.id.textShare);
        textdownload = (TextView) findViewById(R.id.textDownload);
        textaddorbuy = (TextView) findViewById(R.id.addorbuy);
        textaddorbuy.setOnClickListener(this);
        addfavorite_layout = (LinearLayout) findViewById(R.id.addfavoritelayout);
        addfavorite_layout.setOnClickListener(this);
        share_layout = (LinearLayout) findViewById(R.id.sharelayout);
        share_layout.setOnClickListener(this);
        download_layout = (LinearLayout) findViewById(R.id.downloadlayout);
        download_layout.setOnClickListener(this);


        videopage.setPaidStatus(true);
        changePage(videopage);


    }

    private void changePage(Fragment frg){
        FragmentManager fm=getSupportFragmentManager();
        FragmentTransaction ft=fm.beginTransaction();
        ft.replace(R.id.fragment_frame, frg);
        ft.commit();
    }


    @Override
    public void onClick(View view) {
            switch (view.getId()) {
                case R.id.addfavoritelayout:

                    addfavorite.setImageResource(R.mipmap.icon_star_red);

                    break;
                case R.id.sharelayout:
                    //do sth;

                    break;
                case R.id.downloadlayout:
                    //do sth;

                    break;
                case R.id.addorbuy:
                    //do sth;

                    break;
            }

    }
}
