package com.education.online.act;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.callback.AVIMClientCallback;
import com.avos.avoscloud.im.v2.callback.AVIMConversationCallback;
import com.avos.avoscloud.im.v2.callback.AVIMConversationCreatedCallback;
import com.avoscloud.leanchatlib.controller.ChatManager;
import com.avoscloud.leanchatlib.model.LeanchatUser;
import com.avoscloud.leanchatlib.utils.AVUserCacheUtils;
import com.education.online.R;
import com.education.online.act.order.SubmitOrder;
import com.education.online.act.pushlive.LiveCameraPage;
import com.education.online.act.teacher.CourseBaseInfoModify;
import com.education.online.adapter.CourseAdapter;
import com.education.online.bean.CourseDetailBean;
import com.education.online.bean.CourseExtm;
import com.education.online.bean.EvaluateBean;
import com.education.online.bean.EvaluateListBean;
import com.education.online.bean.JsonMessage;
import com.education.online.http.CallBack;
import com.education.online.http.HttpHandler;
import com.education.online.http.Method;
import com.education.online.util.ActUtil;
import com.education.online.util.Constant;
import com.education.online.util.DialogUtil;
import com.education.online.util.ImageUtil;
import com.education.online.util.JsonUtil;
import com.education.online.util.LogUtil;
import com.education.online.util.SharedPreferencesUtil;
import com.education.online.util.ToastUtils;
import com.education.online.view.ZoomRecyclerView;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CourseMainPage extends AppCompatActivity implements View.OnClickListener{

    private ZoomRecyclerView recyclerList;
    private LinearLayoutManager layoutManager;
    private CourseAdapter adapter;
    private List<EvaluateBean> evaluateList=new ArrayList<>();
    private LinearLayout addfavorite_layout, share_layout, editlayout;
    private TextView textaddfavorite, textshare, textaddorbuy, header_title_tv;
    private ImageView addfavorite, share, roundBackBtn;
    private CourseDetailBean courseDetailBean=new CourseDetailBean();
    private String course_id;
    private int page=1;
    private String pageSize="20";
    private boolean flag=false;
    private boolean onloading=false;
    private RelativeLayout header;
    private EvaluateListBean evaluateListBean=new EvaluateListBean();
    Intent intent;
    HttpHandler httpHandler;
    private String ConversationId="";

    private String my_usercode="";
    public void initiHandler(){
        httpHandler = new HttpHandler(this, new CallBack(this)
        {
            @Override
            public void doSuccess(String method, String jsonData) throws JSONException {
                super.doSuccess(method, jsonData);
                if(method.equals(Method.getCourseDtail)) {
                    JsonUtil.getCourseDetail(jsonData, courseDetailBean);
                    List<CourseExtm> course_extm=courseDetailBean.getCourse_extm();
                    if(course_extm.size()>0) {
                        ConversationId = course_extm.get(0).getGroup_number();
                    }
                    boolean isValid=false;
                    String startDateTime="";
                    for(CourseExtm courseExtm:courseDetailBean.getCourse_extm()){
                        if(!courseExtm.getState().equals("3")){
                            isValid=true;
                            startDateTime=courseExtm.getCourseware_date();
                            break;
                        }
                    }
                    if(!isValid){
                        textaddorbuy.setText("已结束");
                    }else if(intent.hasExtra("Edit")||my_usercode.equals(courseDetailBean.getUsercode())) {
                        editlayout.setVisibility(View.VISIBLE);
                        addfavorite_layout.setVisibility(View.GONE);
                        share_layout.setVisibility(View.GONE);
                        if(!intent.hasExtra("status")||intent.getStringExtra("status").equals("1")) {
                            if(ActUtil.canStartLive(startDateTime)) {
                                textaddorbuy.setText("开始直播");
                                textaddorbuy.setOnClickListener(CourseMainPage.this);
                            }else{
                                textaddorbuy.setOnLongClickListener(new View.OnLongClickListener() {
                                    @Override
                                    public boolean onLongClick(View view) {
                                        ChatManager.getInstance().joinCoversation(ConversationId, new AVIMConversationCallback(){

                                            @Override
                                            public void done(AVIMException e) {
                                                if(e==null) {
                                                    Intent i = new Intent(CourseMainPage.this, LiveCameraPage.class);
                                                    i.putExtra(CourseDetailBean.Name, courseDetailBean);
                                                    i.putExtra(com.avoscloud.leanchatlib.utils.Constants.CONVERSATION_ID, ConversationId);
                                                    startActivity(i);
                                                }else {
                                                    ToastUtils.displayTextShort(CourseMainPage.this, "加入失败请稍后重试");
                                                    e.printStackTrace();
                                                }
                                            }
                                        });
                                        return true;
                                    }
                                });
                                textaddorbuy.setText("尚未开始");
                            }
                        }else if(intent.getStringExtra("status").equals("0")) {
                            textaddorbuy.setText("待审核");
                        }else if(intent.getStringExtra("status").equals("2")) {
                            textaddorbuy.setText("已拒绝");
                        }
                    }else if(courseDetailBean.getIs_buy().equals("1")) {
                        if(ActUtil.canStartLive(startDateTime)) {
                            textaddorbuy.setText("进入课程");
                            textaddorbuy.setOnClickListener(CourseMainPage.this);
                        }else {
                            textaddorbuy.setText("尚未开始");
                        }
                    }else {
                        textaddorbuy.setText("立即报名");
                        textaddorbuy.setOnClickListener(CourseMainPage.this);
                    }
                    header_title_tv.setText(courseDetailBean.getCourse_name());
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
                }else if(method.equals(Method.startCourse)){
                    ChatManager.getInstance().joinCoversation(ConversationId, new AVIMConversationCallback(){

                        @Override
                        public void done(AVIMException e) {
                            if(e==null) {
                                Intent i = new Intent(CourseMainPage.this, LiveCameraPage.class);
                                i.putExtra(CourseDetailBean.Name, courseDetailBean);
                                i.putExtra(com.avoscloud.leanchatlib.utils.Constants.CONVERSATION_ID, ConversationId);
                                startActivity(i);
                            }else {
                                ToastUtils.displayTextShort(CourseMainPage.this, "加入失败请稍后重试");
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }

            @Override
            public void onFailure(String method, JsonMessage jsonMessage, String jsonData) {
                super.onFailure(method, jsonMessage, jsonData);
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

        initiHandler();
        initView();
        httpHandler.getCourseDetail(course_id);
    }

    private void initView() {
        header_title_tv= (TextView) findViewById(R.id.header_title_tv);
        header= (RelativeLayout) findViewById(R.id.app_header_layout_value);
        header.setAlpha(0);
        findViewById(R.id.back_imagebtn).setOnClickListener(this);
        my_usercode = SharedPreferencesUtil.getUsercode(this);
        intent = getIntent();
        course_id = intent.getStringExtra("course_id");
        addfavorite = (ImageView) findViewById(R.id.addfavorite);
        share = (ImageView) findViewById(R.id.share);
        roundBackBtn = (ImageView) findViewById(R.id.roundBackBtn);
        textaddfavorite = (TextView) findViewById(R.id.textAddFavorite);
        textshare = (TextView) findViewById(R.id.textShare);
        textaddorbuy = (TextView) findViewById(R.id.addorbuy);
        addfavorite_layout = (LinearLayout) findViewById(R.id.addfavoritelayout);
        addfavorite_layout.setOnClickListener(this);
        share_layout = (LinearLayout) findViewById(R.id.sharelayout);
        share_layout.setOnClickListener(this);
        editlayout = (LinearLayout) findViewById(R.id.editlayout);
        editlayout.setOnClickListener(this);

        share.setImageResource(R.mipmap.icon_telphone);
        textshare.setText("咨询");

        recyclerList=(ZoomRecyclerView)findViewById(R.id.recyclerList);
        recyclerList.setHeadView(header, roundBackBtn);
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
    protected void onResume() {
        super.onResume();
        ImageUtil.initImageLoader(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_imagebtn:
                onBackPressed();
                break;
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
                if(courseDetailBean.getUsercode().length()>0) {
                    LeanchatUser user = AVUser.newAVUser(LeanchatUser.class, null);
                    user.put("avatar", ImageUtil.getImageUrl(courseDetailBean.getUser_info().getAvatar()));
                    user.put("username", courseDetailBean.getUser_info().getUser_name());
                    user.put("user_type ", "2");
                    user.setObjectId(courseDetailBean.getUsercode());
                    AVUserCacheUtils.cacheUser(user.getObjectId(), user);
                    ActUtil.goChat(courseDetailBean.getUsercode(), this, courseDetailBean.getUser_info().getUser_name());
                }
                break;
            case R.id.downloadlayout:
                //do sth;
                break;
            case R.id.addorbuy:
                if(intent.hasExtra("Edit")||my_usercode.equals(courseDetailBean.getUsercode())){
                    if(ConversationId.length()>0){
                        httpHandler.startCourse(courseDetailBean.getCourse_id());
                    }else {
                        ToastUtils.displayTextShort(CourseMainPage.this, "未找到直播室");
                    }
                } else if(courseDetailBean.getIs_buy().equals("1")){
                    if(SharedPreferencesUtil.getValue(CourseMainPage.this, Constant.WIFIWarning, true)){
                        if (ActUtil.isWifi(CourseMainPage.this)) {
                            studyLiveCourse();
                        }else{
                            DialogUtil.showConfirmDialog(CourseMainPage.this, "提示", "正在使用流量，是否继续？", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                    studyLiveCourse();
                                }
                            });
                        }
                    }else
                        studyLiveCourse();
                }else {
                    Intent i = new Intent(CourseMainPage.this, SubmitOrder.class);
                    i.putExtra(CourseDetailBean.Name, courseDetailBean);
                    startActivity(i);
                }
                break;

            case R.id.editlayout:
                Intent intent=new Intent(CourseMainPage.this, CourseBaseInfoModify.class);
                intent.putExtra(CourseDetailBean.Name, courseDetailBean);
                startActivityForResult(intent, 0x12);
                break;
        }
    }

    private void studyLiveCourse(){
        if (ConversationId.length() > 0) {
            ChatManager.getInstance().joinCoversation(ConversationId, new AVIMConversationCallback() {

                @Override
                public void done(AVIMException e) {
                    if (e == null) {
                        Intent intent = new Intent(CourseMainPage.this, LiveCourseDetail.class);
                        intent.putExtra(CourseDetailBean.Name, courseDetailBean);
                        intent.putExtra("Name", courseDetailBean.getCourse_name());
                        intent.putExtra(com.avoscloud.leanchatlib.utils.Constants.CONVERSATION_ID, ConversationId);
                        startActivity(intent);
                    } else
                        ToastUtils.displayTextShort(CourseMainPage.this, "加入失败请稍后重试");
                }
            });
        } else {
            ToastUtils.displayTextShort(CourseMainPage.this, "未找到直播室");
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
                        adapter.setLoadingHint("共"+evaluateList.size()+"条评价");
                }
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            lastVisibleItem = layoutManager.findLastVisibleItemPosition();
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==0x22){
            httpHandler.getCourseDetail(course_id);
        }
    }
}
