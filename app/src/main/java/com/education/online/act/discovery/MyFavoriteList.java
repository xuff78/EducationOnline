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
import com.education.online.act.teacher.TeacherInformationPage;
import com.education.online.adapter.MyFavorityAdapter;
import com.education.online.bean.PageInfo;
import com.education.online.bean.UserInfo;
import com.education.online.http.CallBack;
import com.education.online.http.HttpHandler;
import com.education.online.http.Method;
import com.education.online.inter.AdapterCallback;
import com.education.online.util.ActUtil;
import com.education.online.util.Constant;
import com.education.online.util.JsonUtil;
import com.education.online.util.ToastUtils;

import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by 可爱的蘑菇 on 2016/10/4.
 */
public class MyFavoriteList extends BaseFrameAct implements AdapterCallback {

    private RecyclerView recyclerList;
    private HttpHandler mHandler;
    private int page=1;
    private ArrayList<UserInfo> users=new ArrayList<>();
    private boolean loadComplete=true;
    private MyFavorityAdapter adapter;
    private UserInfo selectedUser;
    private View selectedView;

    private void initHandler() {
        mHandler = new HttpHandler(this, new CallBack(this) {
            @Override
            public void doSuccess(String method, String jsonData) throws JSONException {
                if(method.equals(Method.attentionList)){
                    PageInfo pageInfo = JSON.parseObject(jsonData, PageInfo.class);
                    ArrayList<UserInfo> addUsers=JSON.parseObject(JsonUtil.getString(jsonData, "attention_info"),
                            new TypeReference<ArrayList<UserInfo>>() {});
                    users.addAll(addUsers);
                    adapter.notifyDataSetChanged();
                    if(pageInfo.getCurrent_page()==pageInfo.getPage_total()){
                        loadComplete=true;
                    }
                    page++;
                }else if(method.equals(Method.addAttention)){
                    selectedUser.setSelected(!selectedUser.getSelected());
                    adapter.notifyDataSetChanged();
                }else if(method.equals(Method.getUserInfo)){
                    Intent intent=new Intent(MyFavoriteList.this, Studentintroduction.class);
                    intent.putExtra("jsonData", jsonData);
                    ActUtil.startAnimActivity(MyFavoriteList.this, intent, selectedView, "headIcon");
                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);

        initHandler();
        _setHeaderTitle("我的关注");
        initView();
        mHandler.getAttentionList(page);
    }

    private void initView() {
        recyclerList=(RecyclerView)findViewById(R.id.recyclerList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerList.setLayoutManager(layoutManager);
        adapter=new MyFavorityAdapter(this, users, this);
        recyclerList.setAdapter(adapter);
    }

    @Override
    public void onClick(View v, int i) {
        selectedUser=users.get(i);
        if(selectedUser.getUser_identity().equals("1")){
            selectedView=v;
            mHandler.getUserInfo(selectedUser.getUsercode());
        }else if(selectedUser.getUser_identity().equals("2")){
            Intent intent=new Intent(this, TeacherInformationPage.class);
            intent.putExtra(Constant.UserCode, selectedUser.getUsercode());
            intent.putExtra("Avatar", selectedUser.getAvatar());
            ActUtil.startAnimActivity(this, intent, v, "headIcon");
        }
    }

    @Override
    public void additem() {

    }

    @Override
    public void delitem(View v, int i) {
        selectedUser=users.get(i);
        mHandler.addAttention(selectedUser.getUsercode());
    }
}
