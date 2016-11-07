package com.education.online.act.teacher;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.education.online.R;
import com.education.online.act.BaseFrameAct;
import com.education.online.bean.TeacherBean;
import com.education.online.fragment.teacher.HomepageCourse;
import com.education.online.fragment.teacher.HomepageImg;
import com.education.online.fragment.teacher.HomepageVideo;
import com.education.online.http.CallBack;
import com.education.online.http.HttpHandler;
import com.education.online.http.Method;
import com.education.online.util.SharedPreferencesUtil;

import org.json.JSONException;

/**
 * Created by Administrator on 2016/8/30.
 */
public class TeacherHomePage extends BaseFrameAct implements View.OnClickListener{

    private LinearLayout details, directory, comments;
    private TextView textdetails, textdirectory, textcomments;
    private View viewdetails, viewdirectory, viewcomments;
    private View lastSelectedview, professionLayout;
    private int lastSelectedPosition=0;
    HomepageImg homepageImg=new HomepageImg();
    HomepageVideo homepageVideo=new HomepageVideo();
    HomepageCourse homepageCourse=new HomepageCourse();
    ImageButton homeBtn;
    private HttpHandler mHandler;
    private TeacherBean teacherBean;

    private void initHandler() {
        mHandler = new HttpHandler(this, new CallBack(this) {
            @Override
            public void doSuccess(String method, String jsonData) throws JSONException {
                super.doSuccess(method, jsonData);
                if(method.equals(Method.getUserInfo)){
                    teacherBean= JSON.parseObject(jsonData, TeacherBean.class);
                    homepageImg.setData(teacherBean.getPhoto_album());
                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_homepage);

        _setHeaderTitle("主页编辑");
        _setRightHome(R.mipmap.teacher_home_edit, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                homeBtn.setImageResource(R.mipmap.teacher_home_done);
                switch (lastSelectedPosition){
                    case 0:
                        homepageImg.setEdit();
                        break;
                    case 1:
                        homepageVideo.setEdit();
                        break;
                    case 2:
                        homepageCourse.setEdit();
                        break;
                }
            }
        });

        initHandler();
        initView();
        openFragment(R.id.frgframe, homepageImg);

        mHandler.getUserInfo(SharedPreferencesUtil.getUsercode(this));
    }

    private void initView() {
        homeBtn = (ImageButton) findViewById(R.id.back_home_imagebtn);
        details = (LinearLayout) findViewById(R.id.details);
        details.setOnClickListener(this);
        directory = (LinearLayout) findViewById(R.id.directory);
        directory.setOnClickListener(this);
        comments = (LinearLayout) findViewById(R.id.comments);
        comments.setOnClickListener(this);

        textdetails = (TextView) findViewById(R.id.textdetails);
        textdirectory = (TextView)  findViewById(R.id.textdirectory);
        textcomments = (TextView) findViewById(R.id.textcomments);

        viewdetails = findViewById(R.id.viewdetails);
        viewdirectory =  findViewById(R.id.viewdirectory);
        viewcomments = findViewById(R.id.viewcomments);
        findViewById(R.id.homePageBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TeacherHomePage.this, TeacherInformationPage.class));
            }
        });
        professionLayout=findViewById(R.id.professionLayout);
        professionLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TeacherHomePage.this, TeacherInfoEdit.class));
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view != lastSelectedview) {
            professionLayout.setVisibility(View.VISIBLE);
            setStatusFalse(lastSelectedPosition);
            lastSelectedview= view;
            switch (view.getId()) {
                case R.id.details:
                    lastSelectedPosition=0;
                    textdetails.setTextColor(getResources().getColor(R.color.dark_orange));
                    viewdetails.setVisibility(View.VISIBLE);
                    openFragment(R.id.frgframe, homepageImg);
                    break;
                case R.id.directory:
                    lastSelectedPosition=1;
                    textdirectory.setTextColor(getResources().getColor(R.color.dark_orange));
                    viewdirectory.setVisibility(View.VISIBLE);
                    openFragment(R.id.frgframe, homepageVideo);
                    break;
                case R.id.comments:
                    professionLayout.setVisibility(View.GONE);
                    lastSelectedPosition=2;
                    textcomments.setTextColor(getResources().getColor(R.color.dark_orange));
                    viewcomments.setVisibility(View.VISIBLE);
                    openFragment(R.id.frgframe, homepageCourse);
                    break;
            }
        }
    }

    public void setStatusFalse(int pos){
        switch (pos)
        {
            case 0:
                textdetails.setTextColor(getResources().getColor(R.color.light_gray));
                viewdetails.setVisibility(View.INVISIBLE);
                break;
            case 1:
                textdirectory.setTextColor(getResources().getColor(R.color.light_gray));
                viewdirectory.setVisibility(View.INVISIBLE);
                break;
            case 2:
                textcomments.setTextColor(getResources().getColor(R.color.light_gray));
                viewcomments.setVisibility(View.INVISIBLE);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        currentFrg.onActivityResult(requestCode, resultCode, data);
    }
}
