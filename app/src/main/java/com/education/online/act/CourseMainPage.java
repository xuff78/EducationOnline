package com.education.online.act;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.education.online.R;
import com.education.online.fragment.CoursePage;
import com.education.online.fragment.HomePage;
import com.education.online.fragment.OnlineCoursePage;
import com.education.online.fragment.dialog.SelectorPage;

public class CourseMainPage extends BaseFrameAct implements View.OnClickListener{

    int currentPos = 0;
    private LinearLayout addfavorite_layout, share_layout, download_layout;
    private TextView textaddfavorite, textshare, textdownload, textaddorbuy;
    private ImageView addfavorite, share, download;
    private CoursePage coursepage  = new CoursePage();


    private View lastSelectedView=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.basicframe_video);
        _setHeaderTitle("PS基础课程教学");
        _setRightHomeListener(this);
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

        textaddorbuy.setText("立即报名");
        share.setImageResource(R.mipmap.icon_telphone);
        textshare.setText("咨询");
        textdownload.setVisibility(View.INVISIBLE);
        download.setVisibility(View.INVISIBLE);

        changePage(coursepage);



    }

    private void changePage(Fragment frg){
        FragmentManager fm=getSupportFragmentManager();
        FragmentTransaction ft=fm.beginTransaction();
        ft.replace(R.id.fragment_frame, frg);
        ft.commit();
    }


    @Override
    public void onClick(View view) {
        if(view!=lastSelectedView) {
            lastSelectedView.setSelected(false);
            view.setSelected(true);
            switch (view.getId()) {
                case R.id.addfavoritelayout:
                    addfavorite.setBackgroundResource(R.mipmap.icon_star_red);

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
}
