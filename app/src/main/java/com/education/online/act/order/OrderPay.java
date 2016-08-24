package com.education.online.act.order;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.education.online.R;
import com.education.online.act.BaseFrameAct;
import com.education.online.view.PayTypeDialog;

/**
 * Created by 可爱的蘑菇 on 2016/8/22.
 */
public class OrderPay extends BaseFrameAct implements View.OnClickListener, PayTypeDialog.PayDialogCallBack {

    TextView walletAmount;
    PayTypeDialog dialog;

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
        findViewById(R.id.payBtn).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.rechargeBtn:
                Intent i=new Intent(OrderPay.this, WalletRecharge.class);
                startActivity(i);
                break;
            case R.id.payBtn:
                dialog=new PayTypeDialog(OrderPay.this, true, OrderPay.this);
                dialog.show();
                break;
        }
    }

    @Override
    public void onSelected(int payType) {
        switch (payType){
            case PayTypeDialog.WalletPay:
                break;
            case PayTypeDialog.AliPay:
                break;
            case PayTypeDialog.WechatPay:
                break;
            case PayTypeDialog.UnionPay:
                break;
        }
    }
}
