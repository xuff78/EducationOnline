package com.education.online.act;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.education.online.R;
import com.education.online.act.order.SubmitOrder;
import com.education.online.bean.CourseDetailBean;
import com.education.online.bean.EvaluateListBean;
import com.education.online.bean.JsonMessage;
import com.education.online.fragment.CoursePage;
import com.education.online.fragment.HomePage;
import com.education.online.fragment.OnlineCoursePage;
import com.education.online.fragment.dialog.SelectorPage;
import com.education.online.http.CallBack;
import com.education.online.http.HttpHandler;
import com.education.online.http.Method;
import com.education.online.util.Constant;
import com.education.online.util.DialogUtil;
import com.education.online.util.JsonUtil;

import org.json.JSONException;

public class CourseMainPage extends BaseFrameAct implements View.OnClickListener{

    int currentPos = 0;
    private LinearLayout addfavorite_layout, share_layout, download_layout;
    private TextView textaddfavorite, textshare, textdownload, textaddorbuy;
    private ImageView addfavorite, share, download;
    private CoursePage coursepage;
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
                        addfavorite.setImageResource(R.mipmap.icon_star);

                    }else{
                        flag=true;
                        addfavorite.setImageResource(R.mipmap.icon_star_red);
                    }
                    httpHandler.getEvaluateList(course_id,"1","10","1");

                }else if(method.equals(Method.getEvaluateList)){
                    evaluateListBean = JsonUtil.getEvaluateList(jsonData);
                //   Toast.makeText(CourseMainPage.this,"success",Toast.LENGTH_SHORT).show();

                    coursepage = new CoursePage();
                    coursepage.setCourseDetailBean(courseDetailBean);
                    coursepage.setEvaluateListBean(evaluateListBean);
                    changePage(coursepage);
                }
                else if (method.equals(Method.addCollection)){
                    if(flag)
                    Toast.makeText(CourseMainPage.this,"收藏成功！",Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(CourseMainPage.this,"取消收藏成功！",Toast.LENGTH_SHORT).show();
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





    private View lastSelectedView=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.basicframe_video);
       // _setHeaderTitle("PS基础课程教学");
        _setRightHomeListener(this);
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

        textaddorbuy.setText("立即报名");
        share.setImageResource(R.mipmap.icon_telphone);
        textshare.setText("咨询");
        textdownload.setVisibility(View.INVISIBLE);
        download.setVisibility(View.INVISIBLE);
    }

    private void changePage(Fragment frg){
        FragmentManager fm=getSupportFragmentManager();
        FragmentTransaction ft=fm.beginTransaction();
        ft.replace(R.id.fragment_frame, frg);
        ft.commit();
    }


    @Override
    public void onClick(View view) {
      //  if(view!=lastSelectedView) {
      //      lastSelectedView.setSelected(false);
       //     view.setSelected(true);
            switch (view.getId()) {
                case R.id.addfavoritelayout:
                    if (!flag) {
                        addfavorite.setImageResource(R.mipmap.icon_star_red);
                        flag=true;
                    }
                    else {
                        addfavorite.setImageResource(R.mipmap.icon_star);
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
                    startActivity(new Intent(CourseMainPage.this, SubmitOrder.class));
                    break;
            }
        }
  //  }
}
