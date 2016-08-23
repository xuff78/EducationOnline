package com.education.online.act.order;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.education.online.R;
import com.education.online.act.BaseFrameAct;

/**
 * Created by 可爱的蘑菇 on 2016/8/22.
 */
public class OrderPay extends BaseFrameAct implements View.OnClickListener {

    TextView walletAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_pay);

        _setHeaderTitle("支付订单");

        initView();
    }

    private void initView() {
        TextView orderId= (TextView) findViewById(R.id.orderId);
        TextView courseName= (TextView) findViewById(R.id.courseName);
        TextView coursePrice= (TextView) findViewById(R.id.coursePrice);
        walletAmount= (TextView) findViewById(R.id.walletAmount);
        TextView orderPrice= (TextView) findViewById(R.id.orderPrice);
        findViewById(R.id.rechargeBtn).setOnClickListener(this);
        findViewById(R.id.rechargeBtn).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.rechargeBtn:
                break;
            case R.id.payBtn:
                break;
        }
    }
}
