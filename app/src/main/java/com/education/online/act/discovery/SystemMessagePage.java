package com.education.online.act.discovery;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.education.online.R;
import com.education.online.act.BaseFrameAct;
import com.education.online.act.CourseMainPage;
import com.education.online.act.Mine.MyCourseMuti;
import com.education.online.act.VideoMainPage;
import com.education.online.adapter.SystemMessageAdapter;
import com.education.online.bean.CourseBean;
import com.education.online.bean.JsonMessage;
import com.education.online.bean.SystemMessage;
import com.education.online.http.CallBack;
import com.education.online.http.HttpHandler;
import com.education.online.http.Method;
import com.education.online.util.JsonUtil;
import com.education.online.util.LogUtil;

import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by 可爱的蘑菇 on 2016/10/4.
 */
public class SystemMessagePage extends BaseFrameAct implements View.OnClickListener{

    private RecyclerView recyclerList;
    private int page=1;
    private HttpHandler handler;
    private boolean onloading=false, complete=false;
    private LinearLayoutManager layoutManager;
    private ArrayList<SystemMessage> datalist=new ArrayList<>();
    private SystemMessageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        AVAnalytics.trackAppOpened(getIntent());
        int type=0;
        Intent noticeIntent=getIntent();
        if(noticeIntent.hasExtra("com.avos.avoscloud.Data")) {
            String typeData = noticeIntent.getStringExtra("com.avos.avoscloud.Data");
            type = JsonUtil.getJsonInt(typeData, "custom-key");
        }
        switch (type){
            case 1:
                Intent i=noticeIntent;
                i.setClass(this, MyCourseMuti.class);
                i.putExtra("Type", 0);
                startActivity(i);
                finish();
                break;
            case 2:
            case 3:
            default:
                setContentView(R.layout.home_page);
                _setHeaderTitle("系统通知");
                initHandler();
                initView();
                handler.getSystemMessage(page);
                break;
        }
    }

    private void initHandler() {
        handler = new HttpHandler(this, new CallBack(this) {
            @Override
            public void doSuccess(String method, String jsonData) throws JSONException {
                if (method.equals(Method.getSystemMessage)) {
                    onloading=false;
                    int totalpage= JsonUtil.getJsonInt(jsonData, "page_total");
                    if(totalpage==page){
                        adapter.setLoadingHint("");
                        complete=true;
                    }else
                        page++;
                    ArrayList<SystemMessage> temps=JSON.parseObject(JsonUtil.getString(jsonData, "message_info"),
                            new TypeReference<ArrayList<SystemMessage>>(){});
                    datalist.addAll(temps);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(String method, JsonMessage jsonMessage, String json) {
                onloading=false;
            }

            @Override
            public void onHTTPException(String method, String jsonMessage) {
                onloading=false;
            }
        });
    }

    private void initView() {
        recyclerList=(RecyclerView)findViewById(R.id.recyclerList);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerList.setLayoutManager(layoutManager);
        adapter=new SystemMessageAdapter(this, datalist,this);
        recyclerList.setAdapter(adapter);
        recyclerList.addOnScrollListener(recyclerListener);
    }

    RecyclerView.OnScrollListener recyclerListener=new RecyclerView.OnScrollListener() {

        int lastVisibleItem=0;

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            lastVisibleItem = layoutManager.findLastVisibleItemPosition();
//            LogUtil.i("test", "listScrollY: "+listScrollY);
        }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if(adapter!=null)
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastVisibleItem + 1 == adapter.getItemCount()) {
                    if(!onloading){
                        if(!complete){
                            onloading = true;
                            handler.getSystemMessage(page);
                            adapter.setLoadingHint("正在加载");
                        }else
                            adapter.setLoadingHint("");
                    }
                }
        }
    };

    @Override
    public void onClick(View view) {
        SystemMessage sm=datalist.get((int) view.getTag());
        if(sm.getMessage_type()!=3) {
            Intent intent = new Intent();
            if(sm.getCourse_type().equals("3"))
                intent.setClass(this, CourseMainPage.class);
            else
                intent.setClass(this, VideoMainPage.class);
            intent.putExtra("course_id", sm.getRelated_id());
            startActivity(intent);
        }
    }
}
