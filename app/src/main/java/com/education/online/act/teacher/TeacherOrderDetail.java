package com.education.online.act.teacher;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.education.online.R;
import com.education.online.act.BaseFrameAct;
import com.education.online.act.CM_MessageChatAct;
import com.education.online.act.Mine.UserOrderDetail;
import com.education.online.act.discovery.ChatPage;
import com.education.online.act.discovery.Studentintroduction;
import com.education.online.adapter.OrderPayListAdapter;
import com.education.online.adapter.TeacherOrderListAdapter;
import com.education.online.adapter.UserOrderAdapter;
import com.education.online.bean.OrderDetailBean;
import com.education.online.bean.OrderPayInfo;
import com.education.online.bean.TeacherOrderBean;
import com.education.online.http.CallBack;
import com.education.online.http.HttpHandler;
import com.education.online.http.Method;
import com.education.online.inter.SimpleAdapterCallback;
import com.education.online.util.ActUtil;
import com.education.online.util.JsonUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/3.
 */

public class TeacherOrderDetail extends BaseFrameAct implements View.OnClickListener{

    private RecyclerView recyclerList;
    HttpHandler handler;
    private String status = null;
    private String course_type = null;
    private int page = 1;
    private boolean onloading=false, complete=false;
    private OrderPayListAdapter adapter;
    private List<OrderPayInfo> orders = new ArrayList<>();
    private LinearLayoutManager layoutManager;
    private String course_id;

    private void initHandler() {
        handler = new HttpHandler(this, new CallBack(this) {
            @Override
            public void doSuccess(String method, String jsonData) {
                if (method.equals(Method.getOrderList)) {
                    String orderInfo = JsonUtil.getString(jsonData, "order_info");
                    List<OrderPayInfo> addOrders = JSON.parseObject(orderInfo, new TypeReference<List<OrderPayInfo>>(){});
                    orders.addAll(addOrders);
                    int totalpage= JsonUtil.getJsonInt(jsonData, "page_total");
                    if(totalpage==page){
                        adapter.setLoadingHint("");
                        complete=true;
                    }else
                        page++;
                    adapter.notifyDataSetChanged();
                    onloading=false;
                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);

        TeacherOrderBean bean= (TeacherOrderBean) getIntent().getSerializableExtra(TeacherOrderBean.Name);
        course_id=bean.getCourse_id();
        _setHeaderTitle("订单列表");
        initHandler();
        initView();
        handler.getOrderListByTeacher(course_id, course_type, page, status);
    }

    private void initView() {
        recyclerList = (RecyclerView) findViewById(R.id.recyclerList);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerList.setLayoutManager(layoutManager);
        adapter = new OrderPayListAdapter(this, orders, loader, this);
        recyclerList.setAdapter(adapter);
        recyclerList.addOnScrollListener(recyclerListener);
    }

    RecyclerView.OnScrollListener recyclerListener=new RecyclerView.OnScrollListener() {

        int lastVisibleItem=0;

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            lastVisibleItem = layoutManager.findLastVisibleItemPosition();
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
                            handler.getOrderListByTeacher(course_id, course_type, page, status);
                            adapter.setLoadingHint("正在加载");
                        }else
                            adapter.setLoadingHint("");
                    }
                }
        }
    };

    @Override
    public void onClick(View view) {
        OrderPayInfo bean=orders.get((Integer) view.getTag());
        Intent i=new Intent();
        switch (view.getId()){
            case R.id.teacherImg:
                i.setClass(this, Studentintroduction.class);
                i.putExtra("usercode", bean.getUsercode());
                ActUtil.startAnimActivity(this, i, view, "headIcon");
                break;
            case R.id.totalk:
                ActUtil.goChat(bean.getUsercode(),  this, bean.getUser_name());
                break;
        }
    }
}
