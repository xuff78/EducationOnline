package com.education.online.act.order;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.education.online.R;
import com.education.online.act.BaseFrameAct;
import com.education.online.act.MainPage;
import com.education.online.act.Mine.UserOrderDetail;
import com.education.online.bean.OrderDetailBean;
import com.education.online.http.CallBack;
import com.education.online.http.HttpHandler;
import com.education.online.http.Method;
import com.education.online.util.JsonUtil;

import java.util.List;

/**
 * Created by 可爱的蘑菇 on 2016/11/26.
 */
public class PaymentCompletePage extends BaseFrameAct implements View.OnClickListener {

    private String initPassword;
    private String confirmPassword;
    private boolean isConfirmEnter = false;
    private TextView textView1;
    private LinearLayout passwordlayout;
    private View view1;
    private HttpHandler handler;
    private OrderDetailBean orderDetailBean;

    private void initHandler() {
        handler = new HttpHandler(this, new CallBack(this) {
            @Override
            public void doSuccess(String method, String jsonData){
                if(method.equals(Method.getOrderDetail)){
                    Intent i=new Intent(PaymentCompletePage.this, UserOrderDetail.class);
                    i.putExtra("jsonData", jsonData);
                    startActivity(i);
                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_pay_complete);
        _setHeaderTitle("订单支付");
        _setLeftBackGone();
        initHandler();
        init();
    }

    @Override
    public void onBackPressed() {

    }

    public void init() {
        orderDetailBean= JSON.parseObject(getIntent().getStringExtra("jsonData"), OrderDetailBean.class);
        TextView orderId= (TextView) findViewById(R.id.orderId);
        orderId.setText("订单号： "+orderDetailBean.getOrder_number());
        TextView orderAmount= (TextView) findViewById(R.id.orderAmount);
        orderAmount.setText("课程名： "+orderDetailBean.getCourse_name()+"   ¥"+orderDetailBean.getPrice());
        findViewById(R.id.toOrder).setOnClickListener(this);
        findViewById(R.id.toHome).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.toOrder:
                handler.getOrderDetail(orderDetailBean.getOrder_number());
                break;
            case R.id.toHome:
                Intent i=new Intent();
                i.setClass(PaymentCompletePage.this, MainPage.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                break;
        }
    }
}
