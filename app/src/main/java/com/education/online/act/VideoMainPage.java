package com.education.online.act;

import android.content.Intent;
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
import android.widget.Toast;

import com.education.online.R;
import com.education.online.act.order.SubmitOrder;
import com.education.online.bean.CourseDetailBean;
import com.education.online.bean.EvaluateListBean;
import com.education.online.bean.JsonMessage;
import com.education.online.bean.VideoImgItem;
import com.education.online.fragment.CoursePage;
import com.education.online.fragment.VideoPage;
import com.education.online.http.CallBack;
import com.education.online.http.HttpHandler;
import com.education.online.http.Method;
import com.education.online.util.JsonUtil;

import org.json.JSONException;

public class VideoMainPage extends BaseFrameAct implements View.OnClickListener{

    int currentPos = 0;
    private LinearLayout addfavorite_layout, share_layout, download_layout;
    private TextView textaddfavorite, textshare, textdownload, textaddorbuy;
    private ImageView addfavorite, share, download;
    private View lastSelectedView=null;
    private VideoPage videopage = new VideoPage();

    private CourseDetailBean courseDetailBean;
    private EvaluateListBean evaluateListBean;
    private String course_id;
    private boolean flag=false;
    Intent intent;
    HttpHandler httpHandler;
    public void initiHandler(){
        httpHandler = new HttpHandler(this, new CallBack(this)
        {
            @Override
            public void doSuccess(String method, String jsonData) throws JSONException {
                super.doSuccess(method, jsonData);
                if(method.equals(Method.getCourseDtail)) {
                    courseDetailBean = JsonUtil.getCourseDetail(jsonData);
                    _setHeaderTitle(courseDetailBean.getCourse_name());
                    if (courseDetailBean.getIs_collection().equals("0"))
                    {
                        flag=false;
                        addfavorite.setSelected(false);

                    }else{
                        flag=true;
                        addfavorite.setSelected(true);
                    }
                    httpHandler.getEvaluateList(course_id,"1","10","1");

                }else if(method.equals(Method.getEvaluateList)){
                    evaluateListBean = JsonUtil.getEvaluateList(jsonData);
                    //   Toast.makeText(CourseMainPage.this,"success",Toast.LENGTH_SHORT).show();

                    videopage = new VideoPage();
                    videopage.setCourseDetailBean(courseDetailBean);
                    videopage.setEvaluateListBean(evaluateListBean);
                    changePage(videopage);
                }
                else if (method.equals(Method.addCollection)){
                    if(flag)
                        Toast.makeText(VideoMainPage.this,"收藏成功！",Toast.LENGTH_SHORT).show();

                    else
                        Toast.makeText(VideoMainPage.this,"取消收藏成功！",Toast.LENGTH_SHORT).show();
                }

                //  DialogUtil.showInfoDailog(CourseMainPage.this, "提示", "发布课程成功!");
            }

            @Override
            public void onFailure(String method, JsonMessage jsonMessage) {
                super.onFailure(method, jsonMessage);
                //
            }
        });
    }


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
        intent = getIntent();
        course_id = intent.getStringExtra("course_id");
        initiHandler();
        httpHandler.getCourseDetail(course_id);



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

                    if (!flag) {
                        addfavorite.setSelected(true);
                        flag=true;
                    }
                    else {
                        addfavorite.setSelected(false);
                        flag=false;
                    }
                    httpHandler.addCollection(course_id);
                    break;


                case R.id.sharelayout:
                    //do sth;

                    break;
                case R.id.downloadlayout:
                    //do sth;

                    break;
                case R.id.addorbuy:
                    //do sth;
                    startActivity(new Intent(VideoMainPage.this, SubmitOrder.class));
                    break;
            }

    }
}
