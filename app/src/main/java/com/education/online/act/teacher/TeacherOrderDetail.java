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
import com.education.online.adapter.UserOrderAdapter;
import com.education.online.bean.OrderDetailBean;
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

public class TeacherOrderDetail extends BaseFrameAct implements View.OnClickListener{

    private RecyclerView recyclerList;
    private TextView selectTypeView, selectStatus, statusAll, statusFinish, statusToPay, statusToComment;
    HttpHandler handler;
    private String status = null;
    private String course_type = null;
    private int page = 1;
    private UserOrderAdapter adapter;
    private List<OrderDetailBean> orders = new ArrayList<>();

    private void initHandler() {
        handler = new HttpHandler(this, new CallBack(this) {
            @Override
            public void doSuccess(String method, String jsonData) {
                if (method.equals(Method.getOrderList)) {

                } else if (method.equals(Method.getOrderDetail)) {

                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_orders);

        _setHeaderTitle("我的订单");
        initHandler();
        initView();
        handler.getOrderList(course_type, page, status);
    }

    private void initView() {
        findViewById(R.id.courseTypeLayout).setVisibility(View.GONE);
        statusAll = (TextView) findViewById(R.id.statusAll);
        statusAll.setOnClickListener(this);
        selectStatus = statusAll;
        statusFinish = (TextView) findViewById(R.id.statusFinish);
        statusFinish.setOnClickListener(this);
        statusToPay = (TextView) findViewById(R.id.statusToPay);
        statusToPay.setOnClickListener(this);
        statusToComment = (TextView) findViewById(R.id.statusToComment);
        statusToComment.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        TextView txt = (TextView) view;
        if (txt != selectStatus) {
            selectStatus.setTextColor(getResources().getColor(R.color.normal_gray));
            txt.setTextColor(getResources().getColor(R.color.normal_blue));
            selectStatus = txt;
            page = 1;
            orders.clear();
            switch (view.getId()) {
                case R.id.statusAll:
                    status = null;
                    break;
                case R.id.statusFinish:
                    status = "complete";
                    break;
                case R.id.statusToPay:
                    status = "no_pay";
                    break;
                case R.id.statusToComment:
                    status = "no_evaluate";
                    break;
            }
            handler.getOrderList(course_type, page, status);
        }
    }
}
