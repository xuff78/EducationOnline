package com.education.online.act;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.education.online.R;
import com.education.online.act.order.SubmitOrder;
import com.education.online.act.pushlive.LiveCameraPage;
import com.education.online.adapter.CourseAdapter;
import com.education.online.bean.CourseDetailBean;
import com.education.online.bean.EvaluateBean;
import com.education.online.bean.EvaluateListBean;
import com.education.online.bean.JsonMessage;
import com.education.online.http.CallBack;
import com.education.online.http.HttpHandler;
import com.education.online.http.Method;
import com.education.online.util.JsonUtil;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class CourseMainPage extends BaseFrameAct implements View.OnClickListener{

    private RecyclerView recyclerList;
    private LinearLayoutManager layoutManager;
    private CourseAdapter adapter;
    private List<EvaluateBean> evaluateList=new ArrayList<>();
    private LinearLayout addfavorite_layout, share_layout, download_layout;
    private TextView textaddfavorite, textshare, textdownload, textaddorbuy;
    private ImageView addfavorite, share, download;
    private CourseDetailBean courseDetailBean=new CourseDetailBean();
    private String course_id;
    private int page=1;
    private String pageSize="20";
    private boolean flag=false;
    private boolean onloading=false;
    private EvaluateListBean evaluateListBean=new EvaluateListBean();
    Intent intent;
    HttpHandler httpHandler;
    public void initiHandler(){
        httpHandler = new HttpHandler(this, new CallBack(this)
        {
            @Override
            public void doSuccess(String method, String jsonData) throws JSONException {
                super.doSuccess(method, jsonData);
                if(method.equals(Method.getCourseDtail)) {
                    JsonUtil.getCourseDetail(jsonData, courseDetailBean);
                    if(intent.hasExtra("Edit"))
                        textaddorbuy.setText("开始直播");
                    else if(courseDetailBean.getIs_buy().equals("1"))
                        textaddorbuy.setText("进入课程");
                    else
                        textaddorbuy.setText("立即报名");
                    _setHeaderTitle(courseDetailBean.getCourse_name());
                    adapter.notifyDataSetChanged();
                    if (courseDetailBean.getIs_collection().equals("0"))
                    {
                        flag=false;
                        addfavorite.setSelected(false);
                    }else{
                        flag=true;
                        addfavorite.setSelected(true);
                    }
                    httpHandler.getEvaluateList(course_id,null,pageSize,"1");
                }else if(method.equals(Method.getEvaluateList)){
                    onloading=false;
                    evaluateListBean = JsonUtil.getEvaluateList(jsonData);
                    evaluateList.addAll(evaluateListBean.getEvaluateList());
                    adapter.notifyDataSetChanged();
                    page++;
                }else if (method.equals(Method.addCollection)){
                    if(flag)
                    Toast.makeText(CourseMainPage.this,"收藏成功！",Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(CourseMainPage.this,"取消收藏成功！",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(String method, JsonMessage jsonMessage) {
                super.onFailure(method, jsonMessage);
                if(method.equals(Method.getEvaluateList)){
                    onloading=false;
                    adapter.setLoadingHint("加载失败");
                }
            }

            @Override
            public void onHTTPException(String method, String jsonMessage) {
                super.onHTTPException(method, jsonMessage);
                if(method.equals(Method.getEvaluateList)){
                    onloading=false;
                    adapter.setLoadingHint("加载失败");
                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.live_course_layout);

        _setRightHomeListener(this);
        initiHandler();
        initView();
        httpHandler.getCourseDetail(course_id);
    }

    private void initView() {
        intent = getIntent();
        course_id = intent.getStringExtra("course_id");
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

        share.setImageResource(R.mipmap.icon_telphone);
        textshare.setText("咨询");
        textdownload.setVisibility(View.INVISIBLE);
        download.setVisibility(View.INVISIBLE);

        recyclerList=(RecyclerView)findViewById(R.id.recyclerList);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerList.setLayoutManager(layoutManager);
        if(intent.hasExtra("course_name"))
            courseDetailBean.setCourse_name(intent.getStringExtra("course_name"));
        if(intent.hasExtra("course_img"))
            courseDetailBean.setImg(intent.getStringExtra("course_img"));
        adapter=new CourseAdapter(this, courseDetailBean, evaluateList);
        recyclerList.setAdapter(adapter);
        recyclerList.addOnScrollListener(srcollListener);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.addfavoritelayout:
                if (!flag) {
                    addfavorite.setSelected(true);
                    flag=true;
                } else {
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
                if(intent.hasExtra("Edit")){
                    Intent i = new Intent(CourseMainPage.this, LiveCameraPage.class);
                    i.putExtra(CourseDetailBean.Name, courseDetailBean);
                    startActivity(i);
                } else if(courseDetailBean.getIs_buy().equals("1")){

                }else {
                    Intent i = new Intent(CourseMainPage.this, SubmitOrder.class);
                    i.putExtra(CourseDetailBean.Name, courseDetailBean);
                    startActivity(i);
                }
                break;
        }
    }

    RecyclerView.OnScrollListener srcollListener=new RecyclerView.OnScrollListener() {

        int lastVisibleItem=0;

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (newState == RecyclerView.SCROLL_STATE_IDLE
                    && lastVisibleItem + 1 == adapter.getItemCount()) {
                if(!onloading){
                    if(evaluateListBean.getCurrent_page()<evaluateListBean.getPagetotal()){
                        onloading = true;
                        httpHandler.getEvaluateList(course_id, null, pageSize, String.valueOf(page));
                        adapter.setLoadingHint("正在加载");
                    }else
                        adapter.setLoadingHint("加载完成");
                }
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            lastVisibleItem = layoutManager.findLastVisibleItemPosition();
        }
    };
}
