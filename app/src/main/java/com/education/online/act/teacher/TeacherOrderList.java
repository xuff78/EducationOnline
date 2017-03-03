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
import com.education.online.act.Mine.UserOrderDetail;
import com.education.online.adapter.TeacherOrderListAdapter;
import com.education.online.adapter.UserOrderAdapter;
import com.education.online.bean.OrderDetailBean;
import com.education.online.bean.TeacherOrderBean;
import com.education.online.http.CallBack;
import com.education.online.http.HttpHandler;
import com.education.online.http.Method;
import com.education.online.inter.SimpleAdapterCallback;
import com.education.online.util.JsonUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/3.
 */

public class TeacherOrderList extends BaseFrameAct implements View.OnClickListener {

    private RecyclerView recyclerList;
    private TextView selectTypeView, selectStatus, statusAll, statusFinish, statusToPay, statusToComment;
    HttpHandler handler;
    private String status = null;
    private String course_type = null;
    private int page = 1;
    private TeacherOrderListAdapter adapter;
    private List<TeacherOrderBean> orders = new ArrayList<>();

    private void initHandler() {
        handler = new HttpHandler(this, new CallBack(this) {
            @Override
            public void doSuccess(String method, String jsonData) {
                if (method.equals(Method.getTeacherOrderList)) {
                    String orderInfo = JsonUtil.getString(jsonData, "◦message_info");
                    List<TeacherOrderBean> addOrders = JSON.parseObject(orderInfo, new TypeReference<List<TeacherOrderBean>>(){});
                    orders.addAll(addOrders);
                    if (page == 1) {
                        recyclerList.setAdapter(adapter);
                    } else {
                        adapter.notifyDataSetChanged();
                    }
                    page++;
                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);

        _setHeaderTitle("我的订单");
        initHandler();
        initView();
        handler.getTeacherOrderList(page);
    }

    private void initView() {

        recyclerList = (RecyclerView) findViewById(R.id.recyclerList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerList.setLayoutManager(layoutManager);
        adapter = new TeacherOrderListAdapter(this, orders);
        recyclerList.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {

    }
}
