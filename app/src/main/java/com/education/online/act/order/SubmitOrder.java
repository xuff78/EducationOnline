package com.education.online.act.order;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.education.online.R;
import com.education.online.act.BaseFrameAct;

/**
 * Created by 可爱的蘑菇 on 2016/8/23.
 */
public class SubmitOrder extends BaseFrameAct implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_submit);

        _setHeaderTitle("我的订单");

        initView();
    }

    private void initView() {
        ImageView courseImg= (ImageView) findViewById(R.id.courseImg);
        TextView courseType= (TextView) findViewById(R.id.courseType);
        TextView coursePrice= (TextView) findViewById(R.id.coursePrice);
        TextView teacherName= (TextView) findViewById(R.id.teacherName);
        TextView courseName= (TextView) findViewById(R.id.courseName);
        TextView courseTotalPrice= (TextView) findViewById(R.id.courseTotalPrice);
        TextView orderPrice= (TextView) findViewById(R.id.orderPrice);
        findViewById(R.id.submitBtn).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.submitBtn:
                Intent i=new Intent(SubmitOrder.this, OrderPay.class);
                startActivity(i);
                break;
        }
    }
}
