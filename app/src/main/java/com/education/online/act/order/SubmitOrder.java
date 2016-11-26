package com.education.online.act.order;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.education.online.R;
import com.education.online.act.BaseFrameAct;
import com.education.online.adapter.InterestingAdapter;
import com.education.online.bean.CourseDetailBean;
import com.education.online.bean.OrderDetailBean;
import com.education.online.bean.SubjectBean;
import com.education.online.http.CallBack;
import com.education.online.http.HttpHandler;
import com.education.online.http.Method;
import com.education.online.util.ActUtil;
import com.education.online.util.ImageUtil;
import com.education.online.util.JsonUtil;
import com.education.online.util.ToastUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

/**
 * Created by 可爱的蘑菇 on 2016/8/23.
 */
public class SubmitOrder extends BaseFrameAct implements View.OnClickListener {

    CourseDetailBean bean=new CourseDetailBean();
    HttpHandler handler;
    private String order_number;

    private void initHandler() {
        handler = new HttpHandler(this, new CallBack(this) {
            @Override
            public void doSuccess(String method, String jsonData){
                if(method.equals(Method.submitOrder)){
                    order_number= JsonUtil.getString(jsonData, "order_number");
                    handler.getOrderDetail(order_number);
                }else if(method.equals(Method.getOrderDetail)){

                    OrderDetailBean orderDetailBean= JSON.parseObject(jsonData, OrderDetailBean.class);
                    if(orderDetailBean.getPrice().length()>0) {
                        double price=Double.valueOf(orderDetailBean.getPrice());
                        if(price!=0) {
                            Intent i = new Intent(SubmitOrder.this, OrderPay.class);
                            i.putExtra("jsonData", jsonData);
                            startActivity(i);
                        }else {
                            Intent i = new Intent(SubmitOrder.this, PaymentCompletePage.class);
                            i.putExtra("jsonData", jsonData);
                            startActivity(i);
                        }
                    }

                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_submit);

        _setHeaderTitle("提交订单");
        bean= (CourseDetailBean) getIntent().getSerializableExtra(CourseDetailBean.Name);
        initHandler();
        initView();
    }

    private void initView() {
        ImageLoader imageLoader=ImageLoader.getInstance();
        ImageView courseImg= (ImageView) findViewById(R.id.courseImg);
        imageLoader.displayImage(ImageUtil.getImageUrl(bean.getImg()), courseImg);
        TextView courseType= (TextView) findViewById(R.id.courseType);
        ActUtil.getCourseTypeTxt(bean.getCourse_type(), courseType);
        TextView coursePrice= (TextView) findViewById(R.id.coursePrice);
        coursePrice.setText("￥"+bean.getPrice());
        TextView teacherName= (TextView) findViewById(R.id.teacherName);
        teacherName.setText(bean.getIntroduction());
        TextView courseName= (TextView) findViewById(R.id.courseName);
        courseName.setText(bean.getCourse_name());
        TextView courseTotalPrice= (TextView) findViewById(R.id.courseTotalPrice);
        courseTotalPrice.setText("￥"+bean.getPrice());
        TextView orderPrice= (TextView) findViewById(R.id.orderPrice);
        orderPrice.setText("￥"+bean.getPrice());
        findViewById(R.id.submitBtn).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.submitBtn:
                if(order_number==null)
                    handler.submitOrder(bean.getCourse_id(), null);
                else
                    handler.getOrderDetail(order_number);
                break;
        }
    }
}
