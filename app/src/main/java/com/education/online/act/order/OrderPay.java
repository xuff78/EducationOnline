package com.education.online.act.order;

import android.os.Bundle;

import com.education.online.R;
import com.education.online.act.BaseFrameAct;

/**
 * Created by 可爱的蘑菇 on 2016/8/22.
 */
public class OrderPay extends BaseFrameAct {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_pay);

        _setHeaderTitle("支付订单");

        initView();
    }

    private void initView() {

    }
}
