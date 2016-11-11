package com.education.online.act.Mine;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.education.online.R;
import com.education.online.act.BaseFrameAct;
import com.education.online.bean.OrderDetailBean;
import com.education.online.view.SelectPicDialog;

/**
 * Created by 可爱的蘑菇 on 2016/9/16.
 */
public class UserOrderDetail extends BaseFrameAct {


    private ImageView teacherImg;
    private TextView payMoney, orderId, createTime, teacherName, courseTitle, courseNum, priceTxt, payTxt, labelTxt;
    private LinearLayout courseLayout;
    private OrderDetailBean orderDetailBean;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_order_detail);

        orderDetailBean= JSON.parseObject(getIntent().getStringExtra("jsonData"), OrderDetailBean.class);
        _setHeaderTitle("订单详情");
        initView();
    }

    private void initView() {
        teacherImg= (ImageView) findViewById(R.id.teacherImg);
        payMoney= (TextView) findViewById(R.id.payMoney);
        orderId= (TextView) findViewById(R.id.orderId);
        payMoney= (TextView) findViewById(R.id.payMoney);
        createTime= (TextView) findViewById(R.id.createTime);
        teacherName= (TextView) findViewById(R.id.teacherName);
        courseTitle= (TextView) findViewById(R.id.courseTitle);
        courseNum= (TextView) findViewById(R.id.courseNum);
        priceTxt= (TextView) findViewById(R.id.priceTxt);
        payTxt= (TextView) findViewById(R.id.payTxt);
        labelTxt= (TextView) findViewById(R.id.labelTxt);

        courseLayout= (LinearLayout) findViewById(R.id.courseLayout);
        LayoutInflater inflater=LayoutInflater.from(this);
        for(int i=0;i<4;i++){
            View v=inflater.inflate(R.layout.item_course_layout, null);
            TextView courseNum= (TextView) v.findViewById(R.id.courseNum);
            courseLayout.addView(v);
        }

    }

    View.OnClickListener listener=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.headIcon:
                    new SelectPicDialog(UserOrderDetail.this).show();

                    break;
                case R.id.birthdayLayout:
                    break;

            }
        }
    };
}
